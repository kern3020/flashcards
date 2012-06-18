//package com.pontecultural.flashcards;

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

//import java.sql.SQLException;
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * Handles requests for the application home page.
// */
//@Controller
//public class HomeController {
//	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	private JdbcFlashcardsDao jdbcFlashcardsDao;
//	
//	@Autowired
//	public HomeController(JdbcFlashcardsDao jdbcFlashcardsDao) {
//		super();
//		this.jdbcFlashcardsDao = jdbcFlashcardsDao;
//	}
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! the client locale is "+ locale.toString());
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
//	
//	@RequestMapping(value = "/deckList", method = RequestMethod.GET)
//	public String deckList(Locale locale, Model model) {
//		logger.info("list the decks.");
//		try { 
//			model.addAttribute("decks", jdbcFlashcardsDao.fetchAllDecks() );
//		} catch (DataAccessException e) {
//			SQLException sqle = (SQLException)e.getCause();
//			logger.error("Error code: " + sqle.getErrorCode());
//			logger.error("SQL state: " + sqle.getSQLState());
//			logger.error("Error msg: " + sqle.getMessage());
//			model.addAttribute("decks", null);
//		}
//		return "deckList";
//	}
//}
