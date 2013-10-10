<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />
<html>
<head>
<title>Home</title>
</head>
<body>
    
    	<div class="templatemo_side_bar margin_right_10">
        	<c:url var="lobbyUrl" value="/lobby" />
			<c:url var="campaignsUrl" value="/campaigns/public" />
            <div class="header_01">Recent Campaigns</div>
           	<ul>
           		<c:forEach items="${campaigns }" var="campaign">
               		<li><a href="${campaignsUrl + '/' + campaign.id }">${campaign.name }</a></li>
                </c:forEach>
            </ul>
           	<div class="margin_bottom_20">&#160;</div>
           	<div class="header_01">Recent Characters</div>
           	<div class="margin_bottom_20">&#160;</div>
           	<div class="header_01">Friends</div>
           	<div class="margin_bottom_20">&#160;</div>
            
            <div class="margin_bottom_20">&#160;</div>
        </div> <!-- end of left side bar -->
        
        <div class="templatemo_multi_content margin_right_10">
        
	        <div class="templatemo_content ">
	        
	        	<div class="content_section">
	           	  <div class="header_02">Welcome ${pageContext.request.userPrincipal.principal.name} !</div>
	                <p><span>This is your personal Lobby.</span> In this page you can check your recently played campaigns, created characters and check your messages</p>
	              	<div class="margin_bottom_20">&#160;</div>
	                <p><span>Enjoy all the features of DigitalRPG.</span> </p>
	                <p>Go to the Campaign page to create your own campaign or join a public one.</p>
	                <p>Go to the Characters page to create and administrate your own characters.</p>
	                <p>Go to the Combats page and watch your active combats and combats history</p>
	                
	                <div class="cleaner">&#160;</div>
	            </div>
	            
	            <div class="margin_bottom_40">&#160;</div>
	        </div> <!-- end of content -->
	        
	        <jsp:include page="messages.jsp"/>
        </div>
        <jsp:include page="ads.jsp"/>
</body>
</html>

</jsp:root>