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
            <div class="header_01"><a href="${lobbyUrl }">My Lobby</a></div>
           	<div class="header_01"><a href="${campaignUrl }">Featured Campaigns</a></div>
           	<ul>
           		<c:forEach items="${campaigns }" var="campaign">
               		<li><a href="${campaignsUrl + '/' + campaign.id }">${campaign.name }</a></li>
                </c:forEach>
            </ul>
            
			
            <div class="margin_bottom_20 horizontal_divider">&#160;</div> 
			<div class="margin_bottom_20">&#160;</div>

            <div class="header_01">Search Campaign</div>     
            <div id="search_form">
            	<input type="text" id="search_field" name="campaign" />
            	<input type="button" value="Search" id="search_button" class="small_button" />
            	<div style="clear:both; height: 0px">&#160;</div>
            </div>
            <div class="margin_bottom_20">&#160;</div>
        </div> <!-- end of left side bar -->
        
        <div class="templatemo_multi_content margin_right_10">
        
	        <div class="templatemo_content ">
	        
	        	<div class="content_section">
	           	  <div class="header_02">Welcome Hero!</div>
	                <p><span>This  game template is provided by <a href="http://www.templatemo.com" target="_parent">TemplateMo.com</a> for free of charge.</span> You may download, modify and apply this template for your personal or business websites. Credit goes to <a href="http://www.photovaco.com" target="_blank">Free Photos</a> for photos. Praesent tempor, arcu at egestas vestibulum, lorem elit viverra velit, sit amet gravida ligula ante et sem.</p>
	              <div class="margin_bottom_20">&#160;</div>
	                <div class="rc_btn_01 fl"><a href="#">Join us</a></div> 
	                
	                <div class="cleaner">&#160;</div>
	            </div>
	            
	            <div class="margin_bottom_40">&#160;</div>
	        </div> <!-- end of content -->
	        
	        <div class="templatemo_content margin_top_15">
	        
	            <div class="content_section">
	           	  <div class="header_02">What is DigitalRPG</div>
	              
	                <p><span>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</span> Maecenas pretium diam commodo mauris vestibulum id accumsan enim ultrices. Quisque massa magna, porta nec congue et, elementum eget sem. Nunc vel libero libero, in dignissim urna. </p>
				  <div class="margin_bottom_20">&#160;</div>
	              <div class="rc_btn_01 fl"><a href="#">Explore campaigns</a></div>  
	                <div class="cleaner">&#160;</div>              
	            </div>
	            
	        	<div class="margin_bottom_40">&#160;</div>
	        </div> <!-- end of content -->
        </div>
        <jsp:include page="ads.jsp"/>
</body>
</html>

</jsp:root>