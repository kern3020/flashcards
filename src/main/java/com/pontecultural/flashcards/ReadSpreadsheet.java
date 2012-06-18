package com.pontecultural.flashcards;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * Instructors build flash cards by adding to spreadsheet. This class
 * parses it.
 * 
 * @author John Kern
 */

public class ReadSpreadsheet implements ContentHandler, Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ReadSpreadsheet.class);

	private CommonsMultipartFile fileData;
	private String odsFile; 

	private final static String TAG_FOR_DECK="table:table";
	private final static String ATTR_TABLE_NAME="table:name";
	private final static String TAG_FOR_CARD="table:table-row";
	private final static String TAG_FOR_TEXT="text:p";
	private final static String DESCRIPTION_KEYWORD="description";

	private String deckName;  
	
	private boolean srcColumnSetP; 
	private boolean inDescription; 
	private boolean inSrcLang;
	private boolean inDestLang; 
	private String srcPhrase;
	private String destPhrase;
	private int deckId; 
	
	// keep counts for test.
	private int testCountDecks;
	private int testCountCards;
	
	public int getTestCountDecks() {
		return testCountDecks;
	}
	
	public int getTestCountCards() {
		return testCountCards;
	}

	private JdbcFlashcardsDao jdbcFlashcardsDao;

	public ReadSpreadsheet() {
		super();		
		testCountDecks = 0; 
		testCountCards = 0;
		initializeCardState();
	}

	private void initializeCardState() { 
		deckName=null; 
		srcColumnSetP=false;
		inDescription=false;
		inSrcLang=false;
		inDestLang=false;
		srcPhrase="";
		destPhrase="";
	}

	public JdbcFlashcardsDao getJdbcFlashcardsDao() {
		return jdbcFlashcardsDao;
	}

	public void setJdbcFlashcardsDao(JdbcFlashcardsDao jdbcFlashcardsDao) {
		this.jdbcFlashcardsDao = jdbcFlashcardsDao;
	}
	
	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	
	public void run() {
		try { 
			// This class is used to upload a zip file via 
			// the web (ie,fileData). To test it, the file is 
			// give to it directly via odsFile. One of 
			// which must be defined. 
			assertFalse(odsFile == null && fileData == null);
			XMLReader reader = null;
		    final String ZIP_CONTENT="content.xml"; 
		    // Open office files are zipped. 
		    // Walk through it and find "content.xml"
		    
		    ZipInputStream zin = null;
		    try { 
		    	if (fileData != null) {
		    		zin = new ZipInputStream(
		    			new BufferedInputStream(
		    				fileData.getInputStream()));
		    	} else {
			    	zin = 
			    	    new ZipInputStream(
				    	    new BufferedInputStream(
				    	    	new FileInputStream(odsFile)));
		    	}

		    	ZipEntry entry;
		    	while((entry = zin.getNextEntry()) != null) {
		    		if(entry.getName().equals(ZIP_CONTENT)) {
		    			break; 	    		   
		    		}
		    	}
		    	SAXParserFactory spf = SAXParserFactory.newInstance();
		    	//spf.setValidating(validate);

		    	SAXParser parser = spf.newSAXParser();
		    	reader = parser.getXMLReader();

		    	// reader.setErrorHandler(new MyErrorHandler());
		    	reader.setContentHandler(this);	    
		    	// reader.parse(new InputSource(zf.getInputStream(entry)));
		    	
		    	reader.parse(new InputSource(zin));
		    
		    } catch (ParserConfigurationException pce) {
		    	pce.printStackTrace();
		    } catch (SAXParseException spe) {
		    	spe.printStackTrace();
		    } catch (SAXException se) {
		    	se.printStackTrace();
		    } catch(IOException ioe ) {
		    	ioe.printStackTrace();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    } finally {
		    	if (zin != null) zin.close();
		    }
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	

	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(inSrcLang==true) {
			try { 
				srcPhrase += URLDecoder.decode(
					new String(ch,start,length),"application/x-www-form-urlencoded");
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
			if (srcPhrase.equals(DESCRIPTION_KEYWORD)) {
				inDescription = true;
			}
		} else if (inDestLang==true) {
			try { 
				destPhrase += URLDecoder.decode(
					new String(ch,start,length),"application/x-www-form-urlencoded");
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
		}
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals(TAG_FOR_TEXT)) {
			if (inSrcLang == true) {
				inSrcLang = false;
				srcColumnSetP=true;
			} else if (inDestLang == true) {
				inDestLang=false;
				srcColumnSetP=false;
			}
		} else if (qName.equals(TAG_FOR_CARD)) {
			if (!(srcPhrase.isEmpty() && 
					destPhrase.isEmpty())) {
				if (inDescription) {
					logger.debug("deck name: " + deckName + 
							" - "  + destPhrase+ "\n");
					Deck d = new Deck(deckName, destPhrase);
					try { 
						this.deckId = this.jdbcFlashcardsDao.insert(d);
					} catch (DataAccessException e) {
						SQLException sqle = (SQLException)e.getCause();
						logger.error(e.getCause().getMessage());
						logger.error("Error code: " + sqle.getErrorCode());
						logger.error("SQL state: "+ sqle.getSQLState());
					}
				
					testCountDecks++; 
				} else {
					Card c = new Card(srcPhrase,destPhrase, deckId);
					try { 
						this.jdbcFlashcardsDao.insert(c);
					} catch (DataAccessException e) {
						SQLException sqle = (SQLException)e.getCause();
						logger.error(e.getCause().getMessage());
						logger.error("Error code: " + sqle.getErrorCode());
						logger.error("SQL state: "+ sqle.getSQLState());
					} catch (Exception e ) {
						logger.error("hmm..what happened here: " + e.getMessage());
					}
					logger.debug("card completed");
					logger.debug("\t en: " + srcPhrase);
					logger.debug("\t pt: " + destPhrase); 
					testCountCards++;
				}
				this.initializeCardState();
			}
		} else if(qName.equals(TAG_FOR_DECK)) {
			logger.debug("deck completed.");
		}
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals(TAG_FOR_DECK)) {
			deckName = atts.getValue(ATTR_TABLE_NAME);
		} else if (qName.equals(TAG_FOR_CARD)) {
			inDescription = false;
			logger.debug("Commence card");
		} else if (qName.equals(TAG_FOR_TEXT)) {
			if (srcColumnSetP==false) {
				inSrcLang=true;
			} else {
				inDestLang=true; 
			}
		}
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setOdsFile(String odsFile) {
		this.odsFile = odsFile;
	}
	
	public static void main(String[] args) throws Exception {
		FileSystemXmlApplicationContext dbCtx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/appServlet/flashcards-persistence.xml");
		JdbcFlashcardsDao jDao = (JdbcFlashcardsDao)dbCtx.getBean("jdbcFlashcardsDaoTest");
		ReadSpreadsheet ods = new ReadSpreadsheet();
		ods.setJdbcFlashcardsDao(jDao);
		ods.setOdsFile("/home/jkern/workspace/JavaSpring/test/data/FlashCardsTest.ods");
		ods.run();
	}


}
