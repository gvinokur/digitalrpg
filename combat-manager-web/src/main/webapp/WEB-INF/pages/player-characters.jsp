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
<title>Player Characters</title>
</head>
<body>
    
    	<div class="templatemo_side_bar margin_right_10">
        	
            <div class="header_01">My Characters</div>
           	<ul class="campaigns">
           		<c:forEach items="${characters}" var="aCharacter">
           			<c:url var="characterUrl" value="/player-characters/${aCharacter.id }/show"/>
           			<li class="player"><a id="pc_${aCharacter.id }" href="${characterUrl }">${aCharacter.character.name }</a></li>
           		</c:forEach>
                   
               </ul>
            
			<div class="margin_bottom_20">&#160;</div>

        </div> <!-- end of left side bar -->
        
        <div id="central_panel" class="templatemo_multi_content margin_right_10" >
        
        	<div id="character_info" class="templatemo_content dynamic ${show_content == null?'':'hidden' }">
	        	<div class="content_section">
	           	  <div class="header_02">Characters info</div>
	                <p><span>View and Manage Characters.</span> View your character info and the campaign and combats they are involved in.</p>
	     
	               <div class="margin_bottom_40">&#160;</div>
	                <div class="cleaner">&#160;</div>
	            </div>
            </div>
            
            <div id="create_character"  class="templatemo_content dynamic ${show_content == 'create_character'?'':'hidden' }">
            	<div class="content_section">
	            	<div class="header_02">Create Character</div>
	            	<p>You are creating a character for ${campaign.gameMaster.name }'s campaign <span>${campaign.name}</span></p>
	            	
	            	<c:url value="/player-characters" var="createCharacterUrl"></c:url>
	            	<form:form action="${createCharacterUrl }" modelAttribute="createCharacter" id="character" method="POST">
	            		
	            		<input type="hidden" name="campaignId" value="${campaign.id}"/>
	            		<input type="hidden" name="messageId" value="${message.id}"/>
	            		
	            		<div class="margin_bottom_20">&#160;</div>
						<div class="generic_label">
							<label for="name">Name</label>
						</div>
						<input type="text" id="name" name="name" autocomplete="off"
							class="generic_field"  />
						<form:errors element="div" path="name"/>
						
						<div class="margin_bottom_20">&#160;</div>
						<div class="generic_label">
							<label for="pictureUrl">Picture URL</label>
						</div>
						<input type="text" id="pictureUrl" name="pictureUrl" autocomplete="off"
							class="generic_field"  />					
						
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
								<jsp:include page="systems/character/pathfinder_input.jsp"/>
							</c:when>
						</c:choose>
						
						
						<input type="submit" value="Create" class="small_button"></input>
							
	            	</form:form>
	                <div class="margin_bottom_40">&#160;</div>
	                <div class="cleaner">&#160;</div>
                </div>
            </div>
            
        	<div id="view_character" character_id="${systemCharacter.id }" class="templatemo_multi_content ${show_content == 'view_character'?'':'hidden' } dynamic">
	        	<div class="templatemo_content width_70_p margin_right_10">
	        		<div class="content_section">
		        		<div class="header_02" id="character_name">${systemCharacter.character.name }</div>
		        		
		        		<div class="scroll_description long" id="character_description">
		        			<div id="invite_user" 
			        			class="campaign_request_join ${(systemCharacter.character.class.simpleName == 'NonPlayerCharacter' and systemCharacter.campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">
			            		<input id="invite_player_button" type="button" value="Send to Friend" class="small_button">
	           					</input>
			            	</div>
			            	<div id="claim_character" class="campaign_request_join ${message != null ? '': 'hidden' }">
			            		<input message_id="${message.id }" id="claim_character_button" type="button" value="Claim Character" class="small_button">
	           					</input>
			            	</div>
		            		<div id="take_character" 
			        			class="campaign_request_join ${(systemCharacter.character.class.simpleName == 'PlayerCharacter' and systemCharacter.campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">
			            		<input id="take_character_button" type="button" value="Take Character" class="small_button">
	           					</input>
			            	</div>
		            		${systemCharacter.character.description }
		            	</div>
		        		<div class="border_top" id="character_campaign">Campaign ${systemCharacter.campaign.name }</div>
		        	</div>
		        	<div class="margin_bottom_40">&#160;</div>
	        	</div>
	        	<c:if test="${systemCharacter.character.pictureUrl != null }">
		        	<div class="templatemo_content width_25_p character_image">
	        			<img src="${systemCharacter.character.pictureUrl}"/>
		        	</div>
	        	</c:if>
	        	<div id="system_attributes ">
	        		<div class="templatemo_content width_25_p margin_top_15">
	        			<c:choose>
	        				<c:when test="${systemCharacter.campaign.system == 'Pathfinder' }">	
	        					<jsp:include page="systems/character/pathfinder_data.jsp"/>
	        				</c:when>
	        			</c:choose>
		        	</div>
	        	</div>
	        		
	        </div>
        	
        	
        	
        </div> <!-- end of content -->
        
        
        
        <jsp:include page="ads.jsp"/>
   	
   		<div id="invite_user_form" style="display:none; cursor: default"> 
   			<form:form id="invite_form">
   				<h2>Send to Friend</h2>
   				<p>Send an Invitation to another user and ask him to claim this character</p>
   				<div class="margin_bottom_20">&#160;</div>
				<div class="to_email">
					<label for="email">Email</label>
				</div>
				<input type="text" id="invite_email" name="email"
					class="field" email="true"/>
					
				<input id="send_invite_button" type="button" value="Invite" class="small_button">
           		</input>
           		<input id="cancel_invite_button" type="button" value="Cancel" class="small_button">
           		</input>  
   			</form:form>
   		</div>
<script type="text/javascript">
	$(document).ready(function(){
		
	   	$('#invite_player_button').click(function() { 
            $.blockUI({ message: $('#invite_user_form'), css: { width: '325px' } }); 
        });
	   	
	   	<c:url var="inviteUrl" value="/characters/[id]/invite/[email]"/>
        $('#send_invite_button').click(function() {
        	var isValid = $("#invite_form").valid();
	          if(isValid) {
	            var campaignId = $("#view_character").attr("character_id")
	            var email = $("#invite_email").val();
	          	var url = "${inviteUrl}".replace("[id]",campaignId).replace("[email]", email)
	          	$.blockUI({ message: "<h1>Sending invitation...</h1>" });
	          	$.ajax(url).done(function(result) {
	          		if(result == true) {
	          			$.blockUI({ message: "<h1>Invitation Sent.</h1>" });
	          			setTimeout($.unblockUI(), 1500);
	          		} else {
	          			alert("Could not send mail, please try again.");
	          			$.blockUI({ message: $('#invite_user_form'), css: { width: '275px' } }); 
	          		}
	          	})
	          }
        }) 
        
        $('#cancel_invite_button').click(function(){
        	$.unblockUI();
        })
        
        <c:url var="claimUrl" value="/characters/[id]/claim/[messageId]"/>
        $('#claim_character_button').click(function(){
        	var characterId = $("#view_character").attr("character_id")
        	var messageId = $(this).attr("message_id")
        	var url = "${claimUrl}".replace("[id]",characterId).replace("[messageId]", messageId)
        	window.location = url;
        })
        
        <c:url var="takeUrl" value="/characters/[id]/take"/>
        $('#take_character_button').click(function(){
        	var characterId = $("#view_character").attr("character_id")
        	var url = "${takeUrl}".replace("[id]",characterId)
        	window.location = url;
        })
	})
</script> 	
   
</body>
</html>

</jsp:root>