<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="resources/css/deckList.css" media="all" />
	<title>Flashcards, Set 1</title>
</head>
<body>
<div id="container" >
<h1>
	  Flashcards, Brazilian Portuguese Set 1
</h1>
<p class="intro">
Speak Portuguese with confidence both socially & professionally.
</p>
<p class="intro">
Mastering verb conjugation is often the biggest challenge for students of Portuguese. This simple-to-use web app helps you practice verb conjugation while building vocabulary and improving your command of gender, articles, and prepositions.
</p>
<ul>
	<c:forEach var="d" items="${decks}" varStatus="loopStatus" >
	
		<li class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}" >
			<a href="${baseURL}/cards?id=${d.id}">
			<span id="deckName">${d.name} </span></a> <br/> 
			<span id="deckDescription">  ${d.description}</span>
			
		</li>
	</c:forEach>
</ul>
<FOOTER> <!-- site wide footer -->
	<NAV>
  		<P> 
		<a href="https://twitter.com/stsmartbrazil" ><img src="resources/img/twitter-32x32.png" /></a>
		<a href="http://www.youtube.com/user/StreetSmartBrazil" ><img src="resources/img/youtube_32x32.png" /></a> 
    	 <A HREF="http://www.facebook.com/pages/Street-Smart-Brazil/50113404491"><img src="resources/img/facebook.jpg" /></A>
    	 </P>
 	</NAV>
 	<P id="copyright" >Copyright © 2012 John Kern and Street Smart Brazil</P>
</FOOTER>
</div>
</body>
</html>
