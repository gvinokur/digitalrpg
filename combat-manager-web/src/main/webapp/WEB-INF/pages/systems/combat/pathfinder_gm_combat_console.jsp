<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />
	
   	<div id="form_message" class="templatemo_content form_message margin_bottom_15 ${form_message==null?'hidden':''}">
   		${form_message }
   		<div class="margin_bottom_20">&#160;</div>
   	</div>
   	<div id="error_message" class="templatemo_content error_message margin_bottom_15 ${error_message==null?'hidden':''}">
   		${error_message }
   		<div class="margin_bottom_20">&#160;</div>
   	</div>
   	
   	<table>
   		<thead>
   			<tr>
   				<td>Character</td>
   				<td>Hidden</td>
   				<td>Initiative</td>
   				<td>Action</td>
   				<td>HP</td>
   				<td>Conditions/Effects</td>
   			</tr>
   		</thead>
   		<tbody>
   			<c:forEach items="${combat.combatCharacters }" var="combatCharacter">
   				<tr>
   					<c:choose>
   						<c:when test="${combatCharacter.character.character['class'].simpleName == 'NonPlayerCharacter'  and combatCharacter.character.character.createdBy.name == pageContext.request.userPrincipal.principal.name }">
   							<c:set var="npc" value="true" ></c:set>
   						</c:when>
   						<c:otherwise>
   							<c:set var="npc" value="false" ></c:set>
   						</c:otherwise>
   					</c:choose>
   					
   					<td>${combatCharacter.character.character.name }</td>
   					<td class="${npc=='true'?'editable':''}" data-type="select" data-source="[{value:true, text:'True'}, {value:false, text:'False'}]">${combatCharacter.hidden }</td>
   					<td>${combatCharacter.initiative }</td>
   					<td>${combatCharacter.currentAction.label }</td>
   					<td style="background-color:${combatCharacter.hitPointsStatus };color:${combatCharacter.hitPointsStatus };width:30px">${combatCharacter.currentHitPoints}</td>
   					<td>${combatCharacter.conditionsAndEffectsString }</td>
   				</tr>
   			</c:forEach>
   		</tbody>
   	</table>
    <script>
    $(document).ready(function() {
    	$.fn.editable.defaults.mode = 'inline';
    	$(".editable").editable();
    })	
    </script>    	
       	
	
</jsp:root>	             