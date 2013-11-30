<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />
	<div class="margin_bottom_20">&#160;</div>
	<div class="generic_label narrower">
		<label for="race">Turns</label>
	</div>
	<input type="text" id="pathfinder_turns" name="pathfinder.turns" autocomplete="off"
		class="generic_field narrowest" />
		
	<div class="generic_label narrower">
		<label for="race">Rounds per Turn</label>
	</div>
	<input type="text" id="pathfinder_turns" name="pathfinder.roundsPerTurn" autocomplete="off"
		class="generic_field narrowest" />
</jsp:root>