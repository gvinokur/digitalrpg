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
<title>Digital RPG - <decorator:title /></title>
<c:url var="faviconUrl" value="/img/favicon.ico" />
<link rel="icon" type="image/x-icon" href="${faviconUrl}" />

<c:url var="mainCssUrl" value="/css/templatemo_style.css" />
<link href="${mainCssUrl}" rel="stylesheet" type="text/css" />
<c:url var="multiSelectCssUrl" value="/css/multi_select.css" />
<link href="${multiSelectCssUrl}" rel="stylesheet" type="text/css" />
<c:url var="jQueryUIEditableCssUrl" value="/css/jqueryui-editable.css" />
<link href="${jQueryUIEditableCssUrl}" rel="stylesheet" type="text/css" />
<c:url var="jQueryGridsterCssUrl" value="/css/jquery.gridster.css" />
<link href="${jQueryGridsterCssUrl}" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src='http://code.jquery.com/jquery-1.10.2.js'>&#160;</script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js">&#160;</script>
<script
	src='http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js'>&#160;</script>
<c:url var="jQueryBlockUIUrl" value="/js/jquery.blockUI.js" />
<script src='${jQueryBlockUIUrl}'>&#160;</script>
<c:url var="jQueryMultiSelectUrl" value="/js/jquery.multiSelect.js" />
<script src='${jQueryMultiSelectUrl}'>&#160;</script>
<c:url var="jQueryUIEditableUrl" value="/js/jqueryui-editable.js" />
<script src='${jQueryUIEditableUrl}'>&#160;</script>
<c:url var="jQueryGridsterUrl" value="/js/jquery.gridster.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>

<c:url var="jQueryGridsterUrl" value="/js/utils.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>
<c:url var="jQueryGridsterUrl" value="/js/jquery.draggable.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>
<c:url var="jQueryGridsterUrl" value="/js/jquery.coords.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>
<c:url var="jQueryGridsterUrl" value="/js/jquery.collision.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>
<c:url var="jQueryGridsterUrl" value="/js/jquery.gridster.extras.js" />
<script src='${jQueryGridsterUrl}'>&#160;</script>

<script>
	$(document).ready(function(){
		$.blockUI.defaults.css = {}; 
	});
</script>
</head>
<body>
	<div id="templatemo_wrapper_outer">
		<div id="templatemo_wrapper_inner">

			<div id="templatemo_banner" class="${param['bannerClass'] }">
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal !=null }">
						<c:url var="logoutUrl" value="/logout" />
						<form:form class="user_details navbar-form pull-right"
							action="${logoutUrl}" method="post">
							Welcome <span>${ pageContext.request.userPrincipal.principal.name}</span>
							<input type="submit" value="Log out" class="logout_button" />
						</form:form>
					</c:when>
					<c:otherwise>
						<div class="user_details">
							<c:url var="loginBarUrl" value="/login" />
							<c:url var="registerBarUrl" value="/register" />
							<span>Welcome Hero!</span>&#160;
							<span><a href="${loginBarUrl }">Login</a></span> or
							<span><a href="${registerBarUrl }">Register</a></span> 
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- end of banner -->

			<div id="templatemo_menu">
				<ul>
					<c:url var="homeUrl" value="/home" />
					<c:url var="lobbyUrl" value="/lobby" />
					<c:url var="campaignsUrl" value="/campaigns" />
					<c:url var="pcsUrl" value="/player-characters" />
					<c:url var="combatsUrl" value="/combats" />
					<c:if test="${pageContext.request.userPrincipal !=null }">
						<li><a href="${homeUrl}"
							class="${fn:startsWith(pageContext.request.servletPath,homeUrl)?'current':' '}">Home</a></li>
						<li id="lobby"><a href="${lobbyUrl }"
							class="${fn:startsWith(pageContext.request.servletPath,lobbyUrl)?'current':' '}">My Lobby</a></li>
						<li><a href="${campaignsUrl }"
							class="${fn:startsWith(pageContext.request.servletPath,campaignsUrl)?'current':' '}">Campaigns</a></li>
						<li><a href="${pcsUrl }"
							class="${fn:startsWith(pageContext.request.servletPath,pcsUrl)?'current':''}">Characters</a></li>
						<li><a href="${combatsUrl }"
							class="${fn:startsWith(pageContext.request.servletPath,combatsUrl)?'current':''}">Combats</a></li>
					</c:if>
				</ul>
			</div>
			<!-- end of menu -->

			

			<div id="templatemo_content_wrapper">

				<decorator:body />

				<div class="cleaner"></div>
			</div>
			<!-- end of content wrapper -->

		</div>
	</div>

	<div id="templatemo_footer">
		<a href="#">Digital RPG</a> | Designed by <a
			href="http://www.templatemo.com" target="_parent">Free CSS
			Templates</a>
	</div>
	<!-- end of footer -->
	<div class="margin_bottom_10"></div>

	<div class="content_section">
		<center>
			<a href="http://validator.w3.org/check?uri=referer"><img
				style="border: 0; width: 88px; height: 31px"
				src="http://www.w3.org/Icons/valid-xhtml10"
				alt="Valid XHTML 1.0 Transitional" width="88" height="31" vspace="8"
				border="0" /></a> <a
				href="http://jigsaw.w3.org/css-validator/check/referer"><img
				style="border: 0; width: 88px; height: 31px"
				src="http://jigsaw.w3.org/css-validator/images/vcss-blue"
				alt="Valid CSS!" vspace="8" border="0" /></a>
		</center>
		<div class="margin_bottom_10"></div>
	</div>


<script>
	<c:url var="allMessagesUrl" value="/messages"/>
	$(document).ready(function(){
		$.ajax("${allMessagesUrl}").done(function(result) {
			if(result.length > 0 ) {
			 	$("#lobby a").addClass("unread_messages")
			 	$("#lobby a").attr("title", "You have " + result.length + " unread messages")
			}
		})
	});
</script>

</body>
	</html>

</jsp:root>