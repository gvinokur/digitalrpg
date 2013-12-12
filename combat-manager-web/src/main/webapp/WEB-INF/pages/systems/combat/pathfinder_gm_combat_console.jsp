<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />

	<div id="form_message"
		class="templatemo_content form_message margin_bottom_15 ${form_message==null?'hidden':''}">
		${form_message }
		<div class="margin_bottom_20">&#160;</div>
	</div>
	<div id="error_message"
		class="templatemo_content error_message margin_bottom_15 ${error_message==null?'hidden':''}">
		${error_message }
		<div class="margin_bottom_20">&#160;</div>
	</div>

	<table style="display:inline-block;">
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
				<tr class="${combatCharacter.id == combat.currentCharacter.id?'current':'' }">
					<c:choose>
						<c:when
							test="${combatCharacter.character.character['class'].simpleName == 'NonPlayerCharacter'  and combatCharacter.character.character.createdBy.name == pageContext.request.userPrincipal.principal.name }">
							<c:set var="npc" value="true"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="npc" value="false"></c:set>
						</c:otherwise>
					</c:choose>

					<td>${combatCharacter.character.character.name }</td>
					<c:url var="postHiddenUrl" value="/combats/character/hidden"></c:url>
					<td class="hiddenSelect ${npc=='true'?'editable':''}" data-type="select"
						data-pk="${combatCharacter.id }" data-url="${postHiddenUrl }"
						data-source="[{value:true, text:'True'}, {value:false, text:'False'}]">${combatCharacter.hidden }</td>
					<td>${combatCharacter.initiative }</td>
					<td>${combatCharacter.currentAction.label }</td>
					<c:url var="postHpUrl" value="/combats/character/currentHitPoints"></c:url>
					<td
						style="background-color:${combatCharacter.hitPointsStatus };color:${combatCharacter.hitPointsStatus };width:30px"
						class="hp ${npc=='true'?'editable':''}" data-type="text"
						data-pk="${combatCharacter.id }" data-url="${postHpUrl }" >${combatCharacter.currentHitPoints}</td>
					<td>${combatCharacter.conditionsAndEffectsString }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div id="character-details" style="display: inline-table;margin-left: 15px">
		<div id="tabs">
			<ul>
				<li><a href="#image">Image</a></li>
				<li><a href="#stats">Stats</a></li>
				<li><a href="#conditions">Conditions</a></li>
				<li><a href="#magical-effects">Magical Effects</a></li>
			</ul>
			<div id="image">
				<div class="character_image">
	       			<img src="${combat.currentCharacter.character.character.pictureUrl}"/>
	        	</div>
			</div>
			<div id="stats">
				<c:set var="systemCharacter" scope="request" value="${combat.currentCharacter.character }"/>
				<jsp:include page="../character/pathfinder_data.jsp"/>
			</div>
			<div id="conditions">
				<!-- TODO: -->
			</div>
			<div id="magical-effects">
				<!-- TODO: -->
			</div>
		</div>
		
	</div>
	
	<script>
    $(document).ready(function() {
    	$("#tabs").tabs();
    
    	$.fn.editable.defaults.mode = 'inline';
    	$.fn.editable.defaults.ajaxOptions = {
    		    beforeSend: function(xhr, settings) {
    		        if (!csrfSafeMethod(settings.type) &amp;&amp; sameOrigin(settings.url)) {
    		            // Send the token to same-origin, relative URLs only.
    		            // Send the token only if the method warrants CSRF protection
    		            // Using the CSRFToken value acquired earlier
    		            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
    		        }
    		    }
    		};
    	$.fn.editable.defaults.showbuttons = false;
    	$(".editable.hiddenSelect").editable();
    	$(".editable.hp").editable({
    		success : function(response, newValue) {
    			$(this).css("color", response.hit_point_status);
    			$(this).css("background-color", response.hit_point_status);
    		}
    	})
    })	
    
    
    function csrfSafeMethod(method) {
	    // these HTTP methods do not require CSRF protection
	    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
	}
	function sameOrigin(url) {
	    // test that a given url is a same-origin URL
	    // url could be relative or scheme relative or absolute
	    var host = document.location.host; // host + port
	    var protocol = document.location.protocol;
	    var sr_origin = '//' + host;
	    var origin = protocol + sr_origin;
	    // Allow absolute or scheme relative URLs to same origin
	    return (url == origin || url.slice(0, origin.length + 1) == origin + '/') ||
	        (url == sr_origin || url.slice(0, sr_origin.length + 1) == sr_origin + '/') ||
	        // or any other URL that isn't scheme relative or absolute i.e relative.
	        !(/^(\/\/|http:|https:).*/.test(url));
	}
    </script>


</jsp:root>
