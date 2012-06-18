
CREATE TABLE IF NOT EXISTS decks (_id integer primary key NOT NULL AUTO_INCREMENT, name text, description text );

CREATE TABLE IF NOT EXISTS cards (_id integer primary key NOT NULL AUTO_INCREMENT, enText text, ptText text,deckId integer );

CREATE TABLE IF NOT EXISTS users (_id integer primary key NOT NULL AUTO_INCREMENT, cardId integer, name text, state integer);
