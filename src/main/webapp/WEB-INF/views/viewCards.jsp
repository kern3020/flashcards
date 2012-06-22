<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="resources/css/cardViewer.css" media="all" />	
	<script type="text/javascript" src="resources/jquery-ui-1.8.19custom/js/jquery-1.7.2.min.js" > </script>
	<script type="text/javascript" src="resources/jquery-ui-1.8.19custom/js/jquery-ui-1.8.19.custom.min.js" ></script>
	<script type="text/javascript" src="resources/js/jquery.flip.min.js" > </script>
	<script type="text/javascript" src="resources/js/cardViewer.js" charset="UTF-8"> </script>
	<script type="text/javascript">
    	var callbackURL = '<c:out value="${callbackURL}"/>';
    	var deckId = '<c:out value="${deckId}"/>';
   	</script>
	
	<title>${deckname}</title>
</head>
<body>

	<div id="container">
		<div id="deck_title">
	  		${deckname}
		</div>
		
		<a id="flipit" href="">
        	<div id="flipbox">
        	</div>
		</a>
		
		<div id="nav_buttons">
			<a id="previous" href=""> 
				<img src="resources/img/go-previous.png" />	
			</a>
			<a id="up" href=""> 
				<img src="resources/img/go-up.png" />	
			</a>
			<a id="next" href="">  
				<img src="resources/img/go-next.png" />
			</a>
		</div>
	<FOOTER> <!-- site wide footer -->
	<NAV>
  		<P id="social"> 
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