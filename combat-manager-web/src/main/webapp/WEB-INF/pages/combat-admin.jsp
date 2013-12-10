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
		<title>Combats</title>
	</head>
	<body>
		<div class="templatemo_side_bar margin_right_10">
			<div class="header_02">My Combats</div>
            <div class="header_01 toggle_header" toggle_id="player">I'm a Player in</div>
           	<div class="scroll_list_150 toggle_child" toggle_id="player">
           		<ul class="combats">
           		<c:forEach items="${combats}" var="combat">
           			<c:if test="${combat.campaign.gameMaster.name != pageContext.request.userPrincipal.principal.name }">
         				<li><a class="combat_list_item" combat_id="${combat.id }">${combat.name }</a></li>
         			</c:if>
           		</c:forEach>
                   
               </ul>
            </div>
            <div class="margin_bottom_10">&#160;</div>
            <div class="header_01 toggle_header" toggle_id="gm">I'm a GM of</div>
            <div class="scroll_list_150 toggle_child" toggle_id="gm">
           	<ul class="combats">
           		<c:forEach items="${combats}" var="combat">
         			<c:if test="${combat.campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name }">
         				<li><a class="combat_list_item" combat_id="${combat.id }">${combat.name }</a></li>
         			</c:if>
           		</c:forEach>
                   
               </ul>
            </div>
            
            <div class="margin_bottom_20">&#160;</div>
		</div>
		
		<div id="central_panel" class="templatemo_multi_content margin_right_10">
        	<div id="form_message" class="templatemo_content form_message margin_bottom_15 ${form_message==null?'hidden':''}">
        		${form_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
        	<div id="error_message" class="templatemo_content error_message margin_bottom_15 ${error_message==null?'hidden':''}">
        		${error_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
	        <div id="combat_info" class="templatemo_content dynamic ${show_content == null?'':'hidden' }">
	        
	        	<div  class="content_section">
	           	  <div class="header_02">Combat info</div>
	                <p><span>View and Manage Combats.</span> View and manage your combats, watch live action or start them from this panel.</p>
	              
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="combat_create" class="templatemo_content dynamic ${show_content == 'combat_create'?'':'hidden' }">
	        	<div class="header_02">Create Combat</div>
	            <p>You are creating a combat campaign <span>${campaign.name}</span></p>
	        	<c:url var="createCombatUrl" value="/combats/create"/>
	        	<form:form modelAttribute="create-combat" action="${createCombatUrl }" method="POST">
	        		<input type="hidden" name="campaignId" value="${campaign.id }"/>
	        		<div class="margin_bottom_20">&#160;</div>
					<div class="generic_label">
						<label for="name">Name</label>
					</div>
					<input type="text" id="name" name="name" autocomplete="off"
						class="generic_field"  />
					<form:errors element="div" path="name"/>				
					
					<div class="margin_bottom_20">&#160;</div>
					<div class="generic_label">
						<label for="description">Description</label>
					</div>
					<div style="display: inline-flex;">
						<textarea id="description" name="description"
							class="generic_field" rows="4" draggable="true">&#160;</textarea>
						<form:errors element="div" path="description"/>
					</div>
					
					<c:choose>
						<c:when test="${campaign.system == 'Pathfinder' }">
							<jsp:include page="systems/combat/pathfinder_input.jsp"/>
						</c:when>
					</c:choose>
					
					<div class="header_02">Select Combatants</div>
					<select name='players' id='players' multiple='multiple'>
					  <optgroup label='Player Managed'>
					    <c:forEach items="${campaign.playerCharacters }" var="character">
						    <option value='${character.id }'>${character.character.name }</option>
					    </c:forEach>
					  </optgroup>
					  <optgroup label='GM Managed'>
					    <c:forEach items="${campaign.nonPlayerCharacters }" var="monster">
						    <option value='${monster.id }'>${monster.character.name }</option>
					    </c:forEach>
					  </optgroup>
					</select>
					<input type="submit" value="Create" class="small_button"></input>
	        	</form:form>
	        	<div class="margin_bottom_40">&#160;</div>
	                <div class="cleaner">&#160;</div>
	        </div>
	        <div id="combat_view" combat_id="${combat.id }" class="templatemo_content dynamic ${show_content == 'combat_view'?'':'hidden' }">
	        	<div class="header_02" id="character_name">${combat.name }</div>
	        	
		        <div class="scroll_description long" id="character_description">
		        	<div id="start_combat" class="campaign_request_join ${(combat.campaign.gameMaster.name != pageContext.request.userPrincipal.principal.name or combat.active)? 'hidden':''}">
	            		<input id="start_combat_button" type="button" value="Start Combat" class="small_button">
          				</input>
	            	</div>
		        	<div id="view_combat_console" class="campaign_request_join ${(not combat.active)? 'hidden':''}">
	            		<input id="view_combat_console_button" type="button" value="Combat Console" class="small_button">
          				</input>
	            	</div>
		        	${combat.description }
		        </div>
		        <div class="border_top" id="character_campaign">Campaign ${combat.campaign.name }</div>
		        <c:choose>
					<c:when test="${combat.campaign.system == 'Pathfinder' }">
						<jsp:include page="systems/combat/pathfinder_data.jsp"/>
					</c:when>
				</c:choose>
					
		        <div class="header_03">Combatants</div>
		        <div class="nice_list scroll_list_219">
		        	<ul>
		        		<c:forEach items="${combat.combatCharacters}" var="combatCharacter">
		        			<c:if test="${!combatCharacter.hidden or combat.campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name }">
		        				<li><a>${combatCharacter.character.character.name}<span style="margin-left: 30px">Initiative: ${combatCharacter.initiative}</span></a></li>
		        			</c:if>
		        		</c:forEach>
		        	</ul>
		        </div>
	        </div>
	        
	   </div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#players').multiSelect({ 
					selectableOptgroup: false,
					afterSelect: function(values){
					    for(var i=0; i &lt; values.length; i++) {
					    	$("#"+values[i]+"-hiddencheck").css("display","")
					    	$("#"+values[i]+"-initiative").css("display","")
					    }
					  },
					  afterDeselect: function(values){
					    for(var i=0; i &lt; values.length; i++) {
					    	$("#"+values[i]+"-hiddencheck").css("display","none")
					    	$("#"+values[i]+"-initiative").css("display","none")
					    }
					  },
					  selectionHeader: '<div style="display: inline-block;position: absolute;z-index: 100;width: 150px;margin-left: 80px;"><span style="float:right;width:60px;margin-right:5px">Initiative</span><span style="float:right;width:40px;margin-right:5px;">Hidden</span></div>'
					
				});
				
				$("#optgroup-selection-GM_Managed li.ms-elem-selection").each(function() {
					var id = $(this).attr("id").split("-")[0]
					$(this).before($("<input />")
						.attr("id", id + "-initiative")
						.attr("type","text")
						.attr("name", "extraInfo["+id+"].initative")
						.val(0)
						.css("display","none")
						.css("float","right")
						.css("width","60px")
						.css("margin-top","0px")
						.css("margin-right","5px"))
					$(this).before($("<input />")
						.attr("id", id + "-hiddencheck")
						.attr("type","checkbox")
						.attr("name", "extraInfo["+id+"].hidden")
						.css("display","none")
						.css("float","right")
						.css("margin-right","5px")
						.css("margin-top","2px"))
					
				})
				
				$("#optgroup-selection-Player_Managed li.ms-elem-selection").each(function() {
					var id = $(this).attr("id").split("-")[0]
					$(this).before($("<input />")
						.attr("id", id + "-initiative")
						.attr("type","text")
						.attr("name", "extraInfo["+id+"].initative")
						.val(0)
						.css("display","none")
						.css("float","right")
						.css("width","60px")
						.css("margin-top","0px")
						.css("margin-right","5px"))
									
				})
				
				$("#start_combat_button").click(function(){
					<c:url var="startCombatUrl" value="/combats/[id]/start"/>
			    	var url = "${startCombatUrl}".replace("[id]", $("#combat_view").attr("combat_id"))
			    	window.location = url;
				})
				
				$("#view_combat_console_button").click(function(){
					<c:url var="viewCombatConsoleUrl" value="/combats/[id]/console/show"/>
			    	var url = "${viewCombatConsoleUrl}".replace("[id]", $("#combat_view").attr("combat_id"))
			    	window.location = url;
				})
			})
		</script>
	</body>
</html>
</jsp:root>