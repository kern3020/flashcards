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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

import static org.junit.Assert.assertTrue;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

public class JdbcFlashcardsDao implements FlashcardsDao {
	
	public enum STATE  {
		PRESENT, MASTERED
	}
	
	private DataSource dataSource;
	
	public JdbcFlashcardsDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public void insert(final Card aCard) {
		String sql = "INSERT INTO cards (enText,ptText,deckId) VALUES (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) 
				throws SQLException {
				int index = 1; 
				ps.setString(index, aCard.getEnText()); index++; 
				ps.setString(index, aCard.getPtText()); index++; 
				ps.setInt(index,aCard.getDeckId());
			}
		});
	}
	
	public int insert(final Deck aDeck) {
		int rc = -1; 
		String sql = "INSERT INTO decks (name, description) VALUES (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) 
				throws SQLException {
				int index = 1; 
				ps.setString(index, aDeck.getName()); index++;
				ps.setString(index, aDeck.getDescription());
			}
		});		
  
		// There are database specific ways to retrieve the id 
		// for the last inserted row(e.g., last_insert_id() in 
		// mysql. Using MAX(id) because I want to be able to use
		// different data sources. See Stackoverflow for limitations
		// on this approach. 
		// http://stackoverflow.com/questions/4734589/retrieve-inserted-row-id-in-sql/4734672
		rc = jdbcTemplate.queryForInt("SELECT MAX(_id) from decks");
		return rc;
	}
	
	public void deleteAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
		jdbcTemplate.execute("delete from cards"); 
		jdbcTemplate.execute("delete from decks");
	}
	
	public List<Card> fetchAllCards() {
		List<Card> rc = null; 
		StringBuilder sql = new StringBuilder("SELECT enText,ptText FROM cards where state = ");
		sql.append(STATE.PRESENT);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql.toString());
		rc = new ArrayList<Card>(rows.size());
		for (Map<String,Object> r : rows) {
			Card c = new Card();
			c.setEnText((String)r.get("enText"));
			c.setPtText((String)r.get("ptText"));
			c.setId((Integer)r.get("deckid"));
			rc.add(c);
		}
		return rc; 
	}
	
	// TODO: when the concept of user is added, update to check for state. 
	public List<Card> fetchCardsByDeck(int aDeckId) {
		List<Card> rc = null; 
		StringBuilder sql = new StringBuilder("SELECT enText,ptText FROM cards where deckId = ");
		sql.append(aDeckId);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql.toString());
		rc = new ArrayList<Card>(rows.size());
		for (Map<String,Object> r : rows) {
			Card c = new Card();
			c.setEnText((String)r.get("enText"));
			c.setPtText((String)r.get("ptText"));
			c.setId((Integer)r.get("deckId"));
			rc.add(c);
		}
		return rc; 
	}
	
	// TODO: when I add the concept of a user, update SQL query to observe state. 
	public List<Card> fetchRandomCards() {
		List<Card> rc = null; 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int tutorialId = -1; 
		
		// find deckId for Tutorial, so that, we can ignore it. 
		StringBuilder sql = new StringBuilder("SELECT _id FROM decks where name = \"Tutorial\"");
		tutorialId = jdbcTemplate.queryForInt(sql.toString());
		
		sql = new StringBuilder("SELECT enText,ptText,deckId FROM cards where deckId != ");
		sql.append(tutorialId);
		sql.append(" order by rand()");
		
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql.toString());
		rc = new ArrayList<Card>(rows.size());
		for (Map<String,Object> r : rows) {
			Card c = new Card();
			c.setEnText((String)r.get("enText"));
			c.setPtText((String)r.get("ptText"));
			c.setId((Integer)r.get("deckId"));
			rc.add(c);
		}
		return rc; 
	}
	
	public List<Deck> fetchAllDecks() {
		List<Deck> rc = null; 
		StringBuilder sql = new StringBuilder("SELECT _id,name,description FROM decks  WHERE name not like \"Tutorial\"");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql.toString());
		rc = new ArrayList<Deck>(rows.size());
		for (Map<String,Object> r : rows) {
			Deck d = new Deck();
			d.setName((String)r.get("name"));
			d.setDescription((String)r.get("description"));
			d.setId((Integer)r.get("_id"));
			rc.add(d);
		}
		return rc; 
	}
	
	public String fetchDeckname(int aDeckId) {
		String  rc = null; 
		StringBuilder sql = new StringBuilder("SELECT name,description FROM decks where _id = ");
		sql.append(aDeckId);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql.toString());
		assertTrue(rows.size() == 1);
		for (Map<String,Object> r : rows) {
			rc = (String)r.get("name");
		}
		return rc; 
	}
	
	public int getNextDeckId(int aDeckId) {
		return -1; 
	}
    
    public void resetCardsInDeck(int aDeckId){

    }

	public void setCardMastered(int aCardId, String aState) {
		
	}
	
	public int remainCards(int aDeckId) {
		return -1 ; 
	}
	
	public String percentComplete(int aDeckId) {
		return ""; 
	}
	
	public boolean hasCards(int aDeckId) {
		return false; 
	}
 
	public int getNumberOfDecks() {
		int rc = -1 ; 
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM decks where _id > 1 ");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		rc = jdbcTemplate.queryForInt(sql.toString());
		return rc; 
	}
}
