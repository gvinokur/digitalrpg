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
			Round <span id="round">${combat.currentRound }</span>/${combat.roundsPerTurn } - Turn <span id="turn">${combat.currentTurn }</span>/${combat.turns }
			<div class="right"><input id="end_button" type="Button" value="End Combat" class="small_button"></input></div>
		</div>
		<div class="titles">
			<div class="lane" style="text-align: right;">Playing</div>
			<div class="lane" style="text-align: center;">Ready</div>
			<div class="lane" style="text-align: left;">Delayed</div>
		</div>
		<div class="loading-gridster"><!--  -->
		</div>
		<div class="gridster" style="display: none;">
			<ul>
				<c:forEach items="${combat.combatCharacters }" var="combatCharacter" varStatus="index">
					<li data-row="${index.count }" data-col="${combatCharacter.currentAction.ready ? '2' : combatCharacter.currentAction.delayed ? '3' : '1'  }" data-sizex="3" data-sizey="1" character-id="${combatCharacter.id}" class="${combatCharacter.id == combat.currentCharacter.id?'current selected':'' } combat-character">
						<c:choose>
							<c:when
								test="${combatCharacter.character.character['class'].simpleName == 'NonPlayerCharacter'  and combatCharacter.character.character.createdBy.name == pageContext.request.userPrincipal.principal.name }">
								<c:set var="npc" value="true"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="npc" value="false"></c:set>
							</c:otherwise>
						</c:choose>
						<div class="selected-char" style="width: 10px">&#160;</div>
						<div class="character-name">${combatCharacter.character.character.name }</div>
						<div class="initiative">Initiative ${combatCharacter.initiative }</div>
						<c:url var="postHpUrl" value="/combats/character/currentHitPoints"></c:url>
						<div
							style="background-color:${combatCharacter.hitPointsStatus };color:${combatCharacter.hitPointsStatus };width:25px"
							class="hp ${npc=='true'?'editable':''}" data-type="text"
							data-pk="${combatCharacter.id }" data-url="${postHpUrl }" >${combatCharacter.currentHitPoints}</div>
						<c:url var="postHiddenUrl" value="/combats/character/hidden"></c:url>
						<div class="hiddenSelect ${npc=='true'?'editable':''}"
							data-pk="${combatCharacter.id }" data-url="${postHiddenUrl }">
								Hidden 
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
								
							</div>	
						
						<div class="conditions-and-effects" title="${combatCharacter.conditionsAndEffectsString }">${combatCharacter.conditionsAndEffectsString }</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="combat-actions">
			<input id="next_button" type="Button" value="Next" class="small_button"></input>
			<input id="previous_button" type="Button" value="Previous" class="small_button"></input>
			
			<input id="add_combatant_button" type="Button" value="Add ..." class="small_button"></input>
			<input id="remove_combatant_button" type="Button" value="Remove" class="small_button"></input>
		</div>
		
	</div>
	<div id="character-details" class="templatemo_content" style="width: 390px;padding-bottom: 20px;">
		<div class="loading-gridster small"><!--  -->
		</div>
		<div id="tabs" style="display: none;">
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
	
	<div id="add_combatant" style="display:none; cursor: default"> 
   			<form:form id="add_combatant_form">
   				<h2>Add Combatant</h2>
   				<p>Add a campaign character to the current combat</p>
   				<div class="margin_bottom_20">&#160;</div>
   				
				<div class="generic_label">
					<label for="email">Character</label>
				</div>
				<div class="generic_label narrow">
					<select id="nonPlayingCombants">&#160;</select>
				</div>
				
				<div class="margin_bottom_20">&#160;</div>
				
				<input id="confirm_add_combatant_button" type="button" value="Add" class="small_button">
           		</input>
           		<input id="cancel_add_combatant_button" type="button" value="Cancel" class="small_button">
           		</input>  
   			</form:form>
   		</div>
   		
   		<div id="remove_combatant" style="display:none; cursor: default"> 
   			<form:form id="add_combatant_form">
   				<h2>Remove Combatant</h2>
   				<p>Remove <span id="character_to_remove"><!--  --></span> from Combat?</p>
   				<div class="margin_bottom_20">&#160;</div>
   				
				<input id="confirm_remove_combatant_button" type="button" value="Confirm" class="small_button">
           		</input>
           		<input id="cancel_remove_combatant_button" type="button" value="Cancel" class="small_button">
           		</input>  
   			</form:form>
   		</div>
	<script>
	var gridster;
	
    $(document).ready(function() {
    	$(".loading-gridster").css("display","none");
    	$("#tabs").css("display","block");
    	$("#tabs").tabs();
    
    	<c:url var="updateOrderAndActionUrl" value="/combats/${combat.id}/character/orderAndAction"/>
    	$(".gridster").css("display","block");
    	gridster = $(".gridster ul").gridster({
            widget_margins: [5, 5],
            widget_base_dimensions: [85, 40],
            min_cols:5,
            max_cols:5,
            draggable : {
            	stop : function(e, ui, $widget){
            		var data = buildSortAndStatusData();
            		$.ajax({
        				url : "${updateOrderAndActionUrl}",
        				dataType : "json",
        				type : "POST",
        				contentType : "application/json",
        				data: JSON.stringify(data),
        				beforeSend : beforePost,
        				success : function () {
        				}
        			})
            	}
            }
        }).data("gridster");
    	
//     	$.fn.editable.defaults.mode = 'inline';
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
    	
    	$(".gridster").on("click",".combat-character", function(){
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
					$(".combat-character[character-id=" + combatCharacter.id+ "] .conditions-and-effects").attr("title",combatCharacter.conditions_and_effects_string)
				}
			})
    	})
    	<c:url var="endCombatUrl" value="/combats/${combat.id}/end"/>
    	$("#end_button").click(function(){
    		window.location = "${endCombatUrl}"
    	})
    	
    	//Initial set up of conditions:
    	<c:forEach items="${combat.currentCharacter.conditions}" var="condition">
    	$(".condition input[type=checkbox][condition-id=${condition.id}]").prop("checked", true)
    	</c:forEach>
    	
    	<c:url var="nonPlayingCombatantsUrl" value="/combats/${combat.id}/nonPlayingCombatants"/>
    	$("#add_combatant_button").click(function(){
    		$("#nonPlayingCombants option").remove();
    		$.ajax({
    			url: "${nonPlayingCombatantsUrl}",
    			dataType: "json",
    			type: "GET",
    			async : false,
    			success : function(characters) {
    				//Only update the HP as the rest is managed in the page. 
    				for(character in characters) {
    					$("#nonPlayingCombants").append($("<option/>").val(character).text(characters[character]))
    				}
    			}
    		});
    		$.blockUI({ message: $('#add_combatant'), css: { width: '325px' } });
    	})
    	
    	$("#remove_combatant_button").click(function(){
    		$("#character_to_remove").text($(".combat-character.selected .character-name").text())
    		$.blockUI({ message: $('#remove_combatant'), css: { width: '325px' } });
    	})
    	
    	<c:url var="addCombatantUrl" value="/combats/${combat.id}/character/[characterId]/add"/>
    	$('#confirm_add_combatant_button').click(function(){
    		var url = "${addCombatantUrl}".replace("[characterId]", $("#nonPlayingCombants").val())
    		$.ajax({
				url : url,
				dataType : "json",
				type : "POST",
				async : false,
				beforeSend : beforePost,
				success : function (combatStatus) {
					//Add not existing character
					for(index in combatStatus.combat_characters) {
						character = combatStatus.combat_characters[index]
						characterWidget = $(".combat-character[character-id='"+ character.id +"']")
						if(characterWidget.size() == 0) {
							newWidget = $(".combat-character").first().clone();
							html = newWidget.attr("character-id", character.id).get();
							gridster.add_widget( 
									html, 3, 1, 
									character.current_action == "Ready" ? '2' : character.current_action == "Delayed" ? '3' : '1', 
									parseInt(index)+1)
							characterWidget = $(".combat-character[character-id='"+ character.id +"']")
							$(".character-name",characterWidget).text(character.name)
							$(".initiative",characterWidget).text("Initiative 0")
							$(".conditions-and-effects", characterWidget).text(character.conditions_and_effects)
							$(".conditions-and-effects", characterWidget).attr("title", character.conditions_and_effects)
							$(".hp", characterWidget).css('background-color',character.current_hit_point_status).css('color',character.current_hit_point_status)
							characterWidget.removeClass("selected")
							characterWidget.removeClass("current")
						} else {
							characterWidget.attr("data-row", parseInt(index)+1)
						}
					}
				}
			})
        	$.unblockUI();
        })
    	
        <c:url var="removeCombatantUrl" value="/combats/${combat.id}/character/[characterId]/delete"/>
    	$('#confirm_remove_combatant_button').click(function(){
    		var url = "${removeCombatantUrl}".replace("[characterId]", $(".combat-character.selected").attr("character-id"))
    		$.ajax({
				url : url,
				dataType : "json",
				type : "POST",
				async : false,
				beforeSend : beforePost,
				success : function (combatStatus) {
					//Add not existing character
					$(".combat-character").each(function() {
						var found = false;
						for(index in combatStatus.combat_characters) {
							character = combatStatus.combat_characters[index]
							if(character.id == $(this).attr("character-id")) {
								found = true;
								//TODO: Hid hidden.
							}
						}
						if(!found) {
							gridster.remove_widget($(this).get());
						}
					})
					$(".combat-character[character-id='"+ combatStatus.current_character_id +"']").addClass("current").click();
					
				}
			})
        	$.unblockUI();
        })
        
    	$('#cancel_add_combatant_button').click(function(){
        	$.unblockUI();
        })
        
        $('#cancel_remove_combatant_button').click(function(){
        	$.unblockUI();
        })
        
    	poll();
    })	
    
    function buildSortAndStatusData() {
    	var result = new Object();
    	var currentPassed = false;
    	$("li.combat-character").each(function(){
    		id = $(this).attr("character-id")
    		order = $(this).attr("data-row")
    		col = $(this).attr("data-col")
    		current = $(this).hasClass("current")
    		if(current) currentPassed = true  
    		action = col == 2 ? "Ready" : col == 3 ? "Delayed" : current ? "In Progress" : currentPassed ? "Pending" : "Taken"
    		result[id] = {order : order, action: action }
    	});
    	return result
    }
    
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
	
	<c:url var="combatStatusUrl" value="/combats/${combat.id}/status"/>
	function poll() {
		var url = "${combatStatusUrl}"
    	$.ajax({
			url: url,
			dataType: "json",
			type: "GET",
			success : function(combatStatus) {
				//Only update the HP as the rest is managed in the page. 
				for(var i = 0; i &lt; combatStatus.combat_characters.length; i++) {
					character = combatStatus.combat_characters[i];
					characterWidget = $(".combat-character[character-id='"+ character.id +"']")
					if(!character.hidden) {
						if(characterWidget.size() == 1) {
							$(".hp", characterWidget).css('background-color',character.current_hit_point_status).css('color',character.current_hit_point_status)
						}
					}
				}
			},
			complete: function() {
				setTimeout(poll, 5000); 
			}
		});
	}
    </script>


</jsp:root>
