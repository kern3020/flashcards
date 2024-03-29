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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This is the common case. Does the class under test process 
 * the spread sheet?
 * 
 * @author john kern
 */


public class ReadInSpreadSheetTest {
	private FileSystemXmlApplicationContext dbCtx;
	private JdbcFlashcardsDao jdbcFlashcardsDao;
	private ReadSpreadsheet ods;
	
	@Before 
	public void setupContext() {
		// project home is assumed - '/home/jkern/workspace/JavaSpring/'
		dbCtx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/appServlet/flashcards-persistence-test.xml");
		jdbcFlashcardsDao = (JdbcFlashcardsDao)dbCtx.getBean("jdbcFlashcardsDaoTest");
		jdbcFlashcardsDao.deleteAll();
		
		// now populate the database 
		ods = new ReadSpreadsheet();
		ods.setJdbcFlashcardsDao(jdbcFlashcardsDao);
		ods.setOdsFile("/home/jkern/workspace/JavaSpring/test/data/FlashCardsTest.ods");
		ods.run();
	}
	
	@Test
	public void testReadSpreadsheet() {
		
		assertEquals(5,ods.getTestCountDecks());
		assertEquals(205, ods.getTestCountCards());
	}
}
