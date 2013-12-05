<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />
<html>
	<head>
		<title>Combat - Game Master</title>
	</head>
	<body>
		<c:choose>
			<c:when test="${combat.campaign.system == 'Pathfinder' }">
				<jsp:include page="systems/combat/pathfinder_gm_combat_console.jsp"/>
			</c:when>
		</c:choose>
	</body>
</html>
</jsp:root>