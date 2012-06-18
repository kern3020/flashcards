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


public class Card {
	private Integer id;
	private String enText; 
	private String ptText;
	private Integer deckId;
	
	public Card(String enText, String ptText, Integer deckId) {
		super();
		this.id = 0; 
		this.enText = enText;
		this.ptText = ptText;
		this.deckId = deckId;
	}
	
	public Card() {
		super(); 
	}
	
	public Integer getId() {
		return id;
	}
	public String getEnText() {
		return enText;
	}
	public String getPtText() {
		return ptText;
	}
	public Integer getDeckId() {
		return deckId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setEnText(String enText) {
		this.enText = enText;
	}
	public void setPtText(String ptText) {
		this.ptText = ptText;
	}
	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	} 
	
}
