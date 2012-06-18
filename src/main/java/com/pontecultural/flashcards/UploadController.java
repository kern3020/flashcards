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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class uploads a open office spreadsheet file containing
 * the flash cards in the agreed upon format. (can I dig up the 
 * original email correspondence? ) ReadSpreadsheet is used to 
 * parse the data file and populate the database.
 * 
 * @author John Kern
 *
 */

// This code will allow one to upload a deck. It is not 
// password protected at this time. To prevent bad actors
// from uploading stuff. I have commented it out for this 
// release. 


//@Controller
//@RequestMapping(value = "/upload")
//public class UploadController {
//	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
//	private JdbcFlashcardsDao jdbcFlashcardsDao;
//	
//	@Autowired
//	public UploadController(JdbcFlashcardsDao jdbcFlashcardsDao) {
//		super();
//		this.jdbcFlashcardsDao = jdbcFlashcardsDao;
//	}
//	
//	@RequestMapping( method = RequestMethod.GET)
//	public String getUploadForm(Locale locale, Model model) {
//		logger.info("Would you like to load a new spread sheet(ods)?");
//		
//		ReadSpreadsheet odsFile = new ReadSpreadsheet();
//		model.addAttribute("odsFile",odsFile);
//		return "odsSubmitData";
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public String create(HttpServletRequest req, Model model, ReadSpreadsheet odsFile, BindingResult result) {
//		logger.info("upload file. " + odsFile.toString());
//		
//		odsFile.setJdbcFlashcardsDao(jdbcFlashcardsDao);
//		if (result.hasErrors()) {
//			for(ObjectError error : result.getAllErrors()) {
//				logger.error("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
//			}
//			return "odsSubmitData";
//		}
//		logger.info("-------------------------------------------");
//		logger.info("Test upload: " + odsFile.getFileData().getOriginalFilename());
//		logger.info("-------------------------------------------");
//		Thread t = new Thread(odsFile); 
//		t.start();
//		
//		String baseURL = req.getScheme() + "://" +
//				req.getServerName() + ":" +
//				req.getServerPort() + 
//				req.getContextPath();
//		
//		model.addAttribute("baseURL", baseURL );
//		
//		return "populated";
//	}
//
//
//}
