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

import com.pontecultural.flashcards.JdbcFlashcardsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebServicesController {
	private static final Logger logger = LoggerFactory.getLogger(WebServicesController.class);
	private JdbcFlashcardsDao jdbcFlashcardsDao;
	
	@Autowired
	public void setJdbcFlashcardsDao(JdbcFlashcardsDao jdbcFlashcardsDao) {
		this.jdbcFlashcardsDao = jdbcFlashcardsDao;
	}

	/**
	 * Fetch cards for deck. 
	 */
	@RequestMapping(value ="/deckId/{deckId}/cards.json", method = RequestMethod.GET)
	public String getCards(HttpServletRequest req,
			@PathVariable Integer deckId,
			Model model) {
		logger.info("get cards for deck: " + deckId);
		try { 
			model.addAttribute("flashcards", jdbcFlashcardsDao.fetchCardsByDeck(deckId));
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException)e.getCause();
			logger.error("Error code: " + sqle.getErrorCode());
			logger.error("SQL state: " + sqle.getSQLState());
			logger.error("Error msg: " + sqle.getMessage());
			model.addAttribute("flashcards", null);
		}
		return "flashcardstemplate"; // was  "observationtemplate" - do I need to configure this someone? 
	}
}
