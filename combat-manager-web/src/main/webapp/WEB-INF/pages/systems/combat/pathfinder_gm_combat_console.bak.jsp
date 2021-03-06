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
	<div class="templatemo_content margin_right_10" style="width: 370px;">
		<div class="combat_header">
			Round <span id="round">${combat.currentRound }</span>/${combat.roundsPerTurn } - Turn <span id="turn">${combat.currentTurn }</span>/${combat.turns }
		</div>
		<table style="border-spacing: 0px">
			<thead>
				<tr class="combat-character-header">
					<td>&#160;</td>
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
					<tr character-id="${combatCharacter.id}" class="${combatCharacter.id == combat.currentCharacter.id?'current selected':'' } combat-character">
						<c:choose>
							<c:when
								test="${combatCharacter.character.character['class'].simpleName == 'NonPlayerCharacter'  and combatCharacter.character.character.createdBy.name == pageContext.request.userPrincipal.principal.name }">
								<c:set var="npc" value="true"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="npc" value="false"></c:set>
							</c:otherwise>
						</c:choose>
						<td style="width: 10px">&#160;</td>
						<td>${combatCharacter.character.character.name }</td>
						<c:url var="postHiddenUrl" value="/combats/character/hidden"></c:url>
						<td class="hiddenSelect ${npc=='true'?'editable':''}"
							data-pk="${combatCharacter.id }" data-url="${postHiddenUrl }">
								<c:choose>
									<c:when test="${combatCharacter.hidden }">
										<input type="checkbox" checked="checked"/>
									</c:when>
									<c:when test="${npc=='true' }">
										<input type="checkbox"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" disabled="true"/>
									</c:otherwise>
								</c:choose>
								
							</td>
						<td>${combatCharacter.initiative }</td>
						<c:url var="postCurrentActionUrl" value="/combats/character/currentAction"></c:url>
						<td class="currentAction editable" data-type="select"
							data-pk="${combatCharacter.id }" data-url="${postCurrentActionUrl}">${combatCharacter.currentAction.label }</td>
						<c:url var="postHpUrl" value="/combats/character/currentHitPoints"></c:url>
						<td
							style="background-color:${combatCharacter.hitPointsStatus };color:${combatCharacter.hitPointsStatus };width:30px"
							class="hp ${npc=='true'?'editable':''}" data-type="text"
							data-pk="${combatCharacter.id }" data-url="${postHpUrl }" >${combatCharacter.currentHitPoints}</td>
						<td class="conditions-and-effects">${combatCharacter.conditionsAndEffectsString }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input id="next_button" type="Button" value="Next" class="small_button"></input>
		<input id="previous_button" type="Button" value="Previous" class="small_button"></input>
	</div>
	<div id="character-details" class="templatemo_content" style="width: 390px;">
		<div id="tabs">
			<ul>
				<li><a href="#image">Image</a></li>
				<li><a href="#stats">Stats</a></li>
				<li><a href="#conditions">Conditions</a></li>
				<li><a href="#magical-effects">Magical Effects</a></li>
			</ul>
			<div id="image" class="overriden">
				<div class="character_image">
	       			<img id="character-image" src="${combat.currentCharacter.character.character.pictureUrl}"/>
	        	</div>
			</div>
			<div id="stats" class="overriden">
				<c:set var="systemCharacter" scope="request" value="${combat.currentCharacter.character }"/>
				<jsp:include page="../character/pathfinder_data.jsp"/>
			</div>
			<div id="conditions" class="overriden">
				<div class="nice_list scroll_list_219">
					<ul>
						<c:forEach items="${items.conditions }" var="condition">
							<li class="condition">
								<a>
									${condition.label }
									<input type="checkbox" condition-id="${condition.id }" style="float: right;margin-right: 20px"/>
								</a> 
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="magical-effects" class="overriden">
				<div class="nice_list scroll_list_219">
					<ul>
						<c:forEach items="${items.magicalEffects }" var="magicalEffect">
							<li class="magicalEffect">
								<a> ${magicalEffect.label }
									<input type="checkbox" condition-id="${magicalEffect.id }" style="float: right;margin-right: 20px"/>
								</a> 
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		
	</div>
	
	<script>
    $(document).ready(function() {
    	$("#tabs").tabs();
    
    	$.fn.editable.defaults.mode = 'inline';
    	$.fn.editable.defaults.ajaxOptions = {
    		    beforeSend: beforePost
    		};
    	$.fn.editable.defaults.showbuttons = false;
    	$(".editable.hiddenSelect.editable input").click(function(){
    		var pk = $(this).parent().attr("data-pk");
    		var url = $(this).parent().attr("data-url");
    		var checked = $(this).prop( "checked" )
    		$.ajax({
    			url: url,
    			beforeSend : beforePost,
    			type: "POST",
    			data: {pk : pk, value : checked},
    			dataType : "json",
    			success : function(combatStatus) {
    			}
    		})
    	});
    	$(".editable.currentAction").editable({
    		source : getAvailableActions
    	});
    	$(".editable.hp").editable({
    		success : function(response, newValue) {
    			$(this).css("color", response.hit_point_status);
    			$(this).css("background-color", response.hit_point_status);
    		},
    		inputclass : "narrow_input"
    	})
    	
    	<c:url var="nextUrl" value="/combats/${combat.id}/character/next"/>
    	$("#next_button").click(function(){
    		$.ajax({
    			url: "${nextUrl}",
    			beforeSend : beforePost,
    			type: "POST",
    			dataType : "json",
    			success : function(combatStatus) {
    				$(".combat-character.current").removeClass("current");
    				$(".combat-character[character-id=" + combatStatus.current_character_id+ "]").addClass("current")
    				$("#round").text(combatStatus.current_round);
    				$("#turn").text(combatStatus.current_turn)
    			}
    		})
    	})
    	
    	<c:url var="previousUrl" value="/combats/${combat.id}/character/previous"/>
    	$("#previous_button").click(function(){
    		$.ajax({
    			url: "${previousUrl}",
    			beforeSend : beforePost,
    			type: "POST",
    			dataType : "json",
    			success : function(combatStatus) {
    				$(".combat-character.current").removeClass("current");
    				$(".combat-character[character-id=" + combatStatus.current_character_id+ "]").addClass("current")
    				$("#round").text(combatStatus.current_round);
    				$("#turn").text(combatStatus.current_turn)
    			}
    		})
    	})
    	
    	$(".combat-character").click(function(){
    		$(".combat-character").removeClass("selected")
    		$(this).addClass("selected")
    		showCharacter($(this).attr("character-id"))
    	})
    	
    	<c:url var="conditionChangeUrl" value="/combats/character/conditions/[action]"/>
    	$(".condition input[type=checkbox]").click(function(){
    		var checked = $(this).prop("checked")
    		var characterId = $(".combat-character.selected").attr("character-id")
    		var url = "${conditionChangeUrl}"
    		var data = { pk : characterId, itemId : $(this).attr("condition-id") }
    		if(checked) {
    			url = url.replace("[action]","ADD")
    		} else {
    			url = url.replace("[action]","REMOVE")
    		}
    		$.ajax({
				url : url,
				dataType : "json",
				type : "POST",
				data: data,
				beforeSend : beforePost,
				success : function (combatCharacter) {
					$(".combat-character[character-id=" + combatCharacter.id+ "] .conditions-and-effects").text(combatCharacter.conditions_and_effects_string)
				}
			})
    	})
    	
    	//Initial set up of conditions:
    	<c:forEach items="${combat.currentCharacter.conditions}" var="condition">
    	$(".condition input[type=checkbox][condition-id=${condition.id}]").prop("checked", true)
    	</c:forEach>
    })	
    
    function getAvailableActions() {
    	var actions = []
    	<c:forEach items="${items.actions}" var="action">
    	actions.push({value: "${action.id }", text: "${action.label}"})
    	</c:forEach>
    	return actions;
    } 
    
    <c:url var="combatCharacterDataUrl" value="/combats/characters/[id]"/>
    function showCharacter(id) {
    	var url = "${combatCharacterDataUrl}".replace("[id]", id)
    	$.ajax({
    		url: url,
    		dataType: "json",
    		type: "GET",
    		success : function(characterData) {
    			$("#character-image").attr("src", characterData.image_url);
    			for(key in characterData) {
    				$("#character-" + key).text(characterData[key]);
    			}	
    			$(".condition input[type=checkbox]").prop("checked", false)
    			for(var i=0; i &lt; characterData.current_conditions.length;i++) {
    				$(".condition input[type=checkbox][condition-id=" + characterData.current_conditions[i].id + "]").prop("checked", true)
    			}
    			//TODO: Populatemagical effects
    		}
    	})
    }
    
    function beforePost(xhr, settings) {
        if (!csrfSafeMethod(settings.type) &amp;&amp; sameOrigin(settings.url)) {
            // Send the token to same-origin, relative URLs only.
            // Send the token only if the method warrants CSRF protection
            // Using the CSRFToken value acquired earlier
            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        }
    }
    
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
