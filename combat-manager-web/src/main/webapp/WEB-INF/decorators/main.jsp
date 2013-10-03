<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    xmlns:page="http://www.opensymphony.com/sitemesh/page"
    xmlns:form="http://www.springframework.org/tags/form"
    xmlns:spring="http://www.springframework.org/tags"
    xmlns:sec="http://www.springframework.org/security/tags"
    xmlns:tags="urn:jsptagdir:/WEB-INF/tags" version="2.0">

  <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
  <jsp:output omit-xml-declaration="true" />
  <jsp:output doctype-root-element="HTML"
              doctype-system="about:legacy-compat" />
<html lang="en">
  <head>
    <title>Digital RPG - <decorator:title/></title>
    <c:url var="faviconUrl" value="/img/favicon.ico"/>
    <link rel="icon" type="image/x-icon" href="${faviconUrl}"/>
    
<c:url var="mainCssUrl" value="css/templatemo_style.css"/>
<link href="${mainCssUrl}" rel="stylesheet" type="text/css" />
<script src='http://code.jquery.com/jquery-1.10.2.js'>&#160;</script>
<script src='http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js'>&#160;</script>
</head>
<body>
<div id="templatemo_wrapper_outer">
<div id="templatemo_wrapper_inner">
	
    <div id="templatemo_banner">
      <a href="http://es.vectorhq.com/" title="vector from es.vectorhq.com"  class="banner_icon" target="_blank"><img src="images/templatemo_banner_icon.png" title="vector" alt="vector" /></a>
    </div> <!-- end of banner -->
    
    <div id="templatemo_menu">
	    <ul>
            <li><a href="#" class="current">Home</a></li>
            <li><a href="#">Games</a></li>
            <li><a href="#">Popular</a></li>
            <li><a href="#">Download</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
    </div> <!-- end of menu -->
    
    <div id="templatemo_content_wrapper">
    
    	<decorator:body/>
    	
        <div class="cleaner"></div>    
    </div> <!-- end of content wrapper -->
    
</div>
</div>

 <div id="templatemo_footer">
    <a href="#">Digital RPG</a> | Designed by <a href="http://www.templatemo.com" target="_parent">Free CSS Templates</a>
   	</div> <!-- end of footer -->
    <div class="margin_bottom_10"></div>
    
    <div class="content_section">
    	<center>
       <a href="http://validator.w3.org/check?uri=referer"><img style="border:0;width:88px;height:31px" src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Transitional" width="88" height="31" vspace="8" border="0" /></a>
	<a href="http://jigsaw.w3.org/css-validator/check/referer"><img style="border:0;width:88px;height:31px"  src="http://jigsaw.w3.org/css-validator/images/vcss-blue" alt="Valid CSS!" vspace="8" border="0" /></a>
	</center>
		<div class="margin_bottom_10"></div>
	</div>



</body>
</html>

</jsp:root>