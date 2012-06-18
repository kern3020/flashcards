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

// import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is responsible for getting this list of decks and
 * passing it along to the deck view. 
 * 
 * @author john kern
 *
 */

@Controller
@RequestMapping(value = "/")
public class DeckController {
	private static final Logger logger = LoggerFactory.getLogger(DeckController.class);
	private JdbcFlashcardsDao jdbcFlashcardsDao;
	
	@Autowired
	public DeckController(JdbcFlashcardsDao jdbcFlashcardsDao) {
		super();
		this.jdbcFlashcardsDao = jdbcFlashcardsDao;
	}
	
	@RequestMapping( method = RequestMethod.GET)
	public String getUploadForm(HttpServletRequest req, Locale locale, Model model) {
		logger.info("List of decks");
		
		// compute base URL to allow client to call us back.
		String baseURL = req.getScheme() + "://" +
				req.getServerName() + ":" +
				req.getServerPort() + 
				req.getContextPath();
		
		model.addAttribute("baseURL", baseURL );
		model.addAttribute("decks",jdbcFlashcardsDao.fetchAllDecks() );
		return "deckList";
	}
	
}
