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

public interface FlashcardsDao {
	
	public void insert(Card aCard);
	public int insert(Deck aDeck);
	
	public List<Card> fetchAllCards(); 
	public List<Card> fetchCardsByDeck(int aDeckId);
	public List<Card> fetchRandomCards();
	public List<Deck> fetchAllDecks();
	
	/**
	 * TEST: used by tests to ensure database is in a well-known
	 * state.  
	 */	
	public void deleteAll();
	
	/**
     * Used by home screen to determine the name of the current deck 
     * @param aDeckId
     * @return - index as a string. 
     */
	public String fetchDeckname(int aDeckId);
	
    /**
     * Once the student has completed, provide the index for the next deck. Return -1 if no more 
     * decks are available.  
     * @param aDeckId
     * @return
     */
	public int getNextDeckId(int aDeckId);
    
    /**
     * Given a deckId, reset all cards associated with it.  
     * @param aIndex - deck id 
     */    
    public void resetCardsInDeck(int aDeckId);
    
	/**
     * If the user flicks to the next card, mark the cards as mastered. 
     * @param aCardId - id of card to be updated. 
     */
	public void setCardMastered(int aCardId, String aState);
	
	/**
     * how many cards remain to be reviewed in this deck? 
     * @param aDeckId
     * @return
     */
	
	public int remainCards(int aDeckId);
	
    /**
     * return a string represent the number of completed cards in a deck. 
     *   If all are completed, return completed string.
     *   If the deck id is DB_ALL_CARDS, compute for all cards.
     */
	public String percentComplete(int aDeckId); 
	
	/**
     * does the deck have cards to review?  
     * @param aDeckId
     * @return true if there are cards to review. Else false
     */
	public boolean hasCards(int aDeckId);
 
	/**
     * return number of decks.  
     * Note: the tutorial is a deck. its id is 1. For statistical information do not
     * include it. 
     * @return number of decks minus the tutorial
     */
	public int getNumberOfDecks();
}
