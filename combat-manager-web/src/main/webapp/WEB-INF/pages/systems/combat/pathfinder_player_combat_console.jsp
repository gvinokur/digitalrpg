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
	<div class="templatemo_content margin_right_10" style="width: 470px;padding-bottom: 20px;">
		<div class="combat_header">
			Round <span id="round">${combat.currentRound }</span>/${combat.roundsPerTurn }
			- Turn <span id="turn">${combat.currentTurn }</span>/${combat.turns }
		</div>
		<div class="titles">
			<div class="lane" style="text-align: right;">Playing</div>
			<div class="lane" style="text-align: center;">Ready</div>
			<div class="lane" style="text-align: left;">Delayed</div>
		</div>
		<div class="gridster">
			<ul>
				<c:set var="hiddenCount" value="0"> </c:set>
				<c:forEach items="${combat.combatCharacters }" var="combatCharacter"
					varStatus="index">
					<c:choose>
						<c:when test="${combatCharacter.hidden}">
							<c:set var="hiddenCount" value="${hiddenCount + 1 }"></c:set>
						</c:when>
						<c:otherwise>
							<li data-row="${index.count - hiddenCount }"
								data-col="${combatCharacter.currentAction.ready ? '2' : combatCharacter.currentAction.delayed ? '3' : '1'  }"
								data-sizex="3" data-sizey="1" character-id="${combatCharacter.id}"
								class="${combatCharacter.id == combat.currentCharacter.id?'current':'' } combat-character ${combatCharacter.hidden?'hidden':''}">
								
								<div class="selected-char" style="width: 10px">&#160;</div>
								<div class="character-name">${combatCharacter.character.character.name }</div>
								<div class="initiative">Initiative
									${combatCharacter.initiative }</div>
								<div
									style="background-color:${combatCharacter.hitPointsStatus };color:${combatCharacter.hitPointsStatus };width:25px"
									class="hp" data-type="text">
									<!-- needed -->
								</div>
		
								<div class="conditions-and-effects"
									title="${combatCharacter.conditionsAndEffectsString }">${combatCharacter.conditionsAndEffectsString }</div>
							</li>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
			</ul>
		</div>
	</div>
	<div id="character-details" class="templatemo_content"
		style="width: 390px;">
		<div id="hitPoints">
			<div class="generic_label">
				<label for="current">Current Hit Points</label>
			</div>
		
			<c:url var="postHpUrl" value="/combats/character/currentHitPoints"></c:url>
			<div id="current_hp" class="generic_value micro editable" data-type="text"
							data-pk="${playerCharacter.id }" data-url="${postHpUrl }">
				${playerCharacter.currentHitPoints }
			</div>
			
			<div class="generic_label micro">
				<label for="Total">Of</label>
			</div>
			<div class="generic_value micro">
				${playerCharacter.character.hp }
			</div>
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#image">Image</a></li>
				<li><a href="#stats">Stats</a></li>
			</ul>
			<div id="image" class="overriden">
				<div class="character_image">
					<img id="character-image"
						src="${playerCharacter.character.character.pictureUrl}" />
				</div>
			</div>
			<div id="stats" class="overriden">
				<c:set var="systemCharacter" scope="request"
					value="${playerCharacter.character }" />
				<jsp:include page="../character/pathfinder_data.jsp" />
			</div>
		</div>

	</div>

	<script>
    $(document).ready(function() {
    	$("#tabs").tabs();
    
    	<c:url var="updateOrderAndActionUrl" value="/combats/${combat.id}/character/orderAndAction"/>
    	$(".gridster ul").gridster({
            widget_margins: [5, 5],
            widget_base_dimensions: [85, 40],
            min_cols:5,
            max_cols:5
        }).data('gridster').disable();
    
    	$.fn.editable.defaults.ajaxOptions = {
    		    beforeSend: beforePost
    		};
    	$.fn.editable.defaults.showbuttons = false;
    	
    	$("#current_hp").editable({
    		success : function(response, newValue) {
    		},
    		inputclass : "narrow_input"
    	})
    	
    	poll();
    })	
    
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
	
	<c:url var="combatStatusUrl" value="/combats/${combat.id}/status"/>
	function poll() {
		var url = "${combatStatusUrl}"
    	$.ajax({
			url: url,
			dataType: "json",
			type: "GET",
			success : function(combatStatus) {
				//Update combat overall status
				$(".combat-character.current").removeClass("current");
				$(".combat-character[character-id=" + combatStatus.current_character_id + "]").addClass("current")
				$("#round").text(combatStatus.current_round);
				$("#turn").text(combatStatus.current_turn);
				//Update character order and 
				for(var i = 0; i &lt; combatStatus.combat_characters.length; i++) {
					character = combatStatus.combat_characters[i];
					characterWidget = $(".combat-character[character-id='"+ character.id +"']")
					if(!character.hidden) {
						if(characterWidget.size() == 1) {
							characterWidget.attr("data-row", character.order)
							characterWidget.attr("data-col", character.current_action == "Ready" ? '2' : character.current_action == "Delayed" ? '3' : '1')
							$(".conditions-and-effects", characterWidget).text(character.conditions_and_effects)
							$(".conditions-and-effects", characterWidget).attr("title", character.conditions_and_effects)
							$(".hp", characterWidget).css('background-color',character.current_hit_point_status).css('color',character.current_hit_point_status)
						} else {
							// TODO: Create widget if doesn't exists
						}
					}
					//Delete widgets that are now hidden or removed
				}
			},
			complete: function() {
				setTimeout(poll, 5000); 
			}
		});
	}
	
    </script>


</jsp:root>
