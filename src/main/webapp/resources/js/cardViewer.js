
/** 
* The card view presents language flash cards. Initially it 
* presents the sentence in the users native(src) language. 
* Click on the card itself to flip it and present the foreign 
* language. Click on the button to the right and left to more 
* to the next card. 
* 
* We do not want the student to continually see the cards they 
* have previously seen. When we show the student a card, we set
* state to one. 
*
* When the student selects a new deck, the state is store to 
* localStorage on the browser. If state was stored on the browser for
* the selected deck, it is retrieved and the associated cards are 
* updated. 
*
* author: john kern
*/ 

// the flip plugin overrides the background-color property. So, set 
// it via the plugin. 
var oliveLike = "#CCFFCC";  

var srcLang = true; 
var index = 0; 
var deckName = "deckname_";
var cards = new Array() ;
var decks = "http://localhost:8080/Flashcards/";
var noMoreCards="No more cards in this deck. Return to list of decks?";


$(document).ready(function(){
	$('div#flipbox').text("cartões download");
	deckName += deckId; 
	index = retrieveIndex();
	$("#previous").click(function(e) {
		e.preventDefault();
		if (index > 0) {
			index--;
			setDaCard('rl'); 
		} else {
			if(confirm(noMoreCards)) {
				history.back();
			}
		}
	});

	$("#up").click(function(e) {
		e.preventDefault();
		storeIndex();
		history.back(); 
	});
	
	$("#flipit").click(function(e) {
		e.preventDefault();
		flipDaCard();
	});

	$("#next").click(function(e) {
		e.preventDefault();
		// is there another card? 
		if (index < (cards.length-1) ) {
			index++; 
			setDaCard('lr');
		} else {
			if(confirm(noMoreCards)) {
				history.back();
			}
		}
	});

	// callbackURL is defined by the server. 
	$.get(callbackURL,
		   function(data) { 
		    	cards = eval( data['flashcards'] );
		    	
		    	$('div#flipbox').text(cards[index].enText);
		    },
		    "json"  
		);
});



flipDaCard = function() { 
	if (srcLang) { 
		$("#flipbox").flip({
			direction:'tb',
			content:cards[index].ptText,
			color: oliveLike
		});
		srcLang=false;
	} else {
		$("#flipbox").flip({
			direction:'tb',
			content:cards[index].enText,
			color: oliveLike
		});
		srcLang=true;
	}
};
 
setDaCard = function(d) { 
	$("#flipbox").flip({
		direction: d,
		content:cards[index].enText,
		color: oliveLike
	});
	srcLang = true; 
};

/*
* Saving user state requires html5's localStorage facility 
*/
supports_html5_storage = function() {
	try {
	    return 'localStorage' in window && window['localStorage'] !== null;
	} catch (e) {
	    return false;
  	}
};

/** 
* Stores state for each card in the browsers localStorage. 
*/
storeIndex = function () {
	if (!supports_html5_storage()) return false; 
	localStorage.setItem(deckName, index);
};

/**
* Retrieves data from localStorage based on deckname. Use
* it to update cards. 
*/
retrieveIndex = function () {
	if (!supports_html5_storage()) return false; 
	var storedIndex = localStorage.getItem(deckName);
	return (storedIndex == null) ? 0 : storedIndex ; 
};


