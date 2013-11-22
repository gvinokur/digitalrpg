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
<title>Campaigns</title>
</head>
<body>
    
    	<div class="templatemo_side_bar margin_right_10">
        	
            <div class="header_02">My Campaigns</div>
            <div class="header_01 toggle_header" toggle_id="player">I'm a Player in</div>
           	<div class="scroll_list_150 toggle_child" toggle_id="player">
           		<ul class="campaigns">
           		<c:forEach items="${campaigns}" var="campaign">
           			<c:if test="${campaign.gameMaster.name != pageContext.request.userPrincipal.principal.name }">
         				<li><a class="campaign_list_item" id="campaign_${campaign.id }">${campaign.name }</a></li>
         			</c:if>
           		</c:forEach>
                   
               </ul>
            </div>
            <div class="margin_bottom_10">&#160;</div>
            <div class="header_01 toggle_header" toggle_id="gm">I'm a GM of</div>
            <div class="scroll_list_150 toggle_child" toggle_id="gm">
           	<ul class="campaigns">
           		<c:forEach items="${campaigns}" var="campaign">
         			<c:if test="${campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name }">
         				<li><a class="campaign_list_item" id="campaign_${campaign.id }">${campaign.name }</a></li>
         			</c:if>
           		</c:forEach>
                   
               </ul>
            </div>
            
            <div class="margin_bottom_20">&#160;</div>
            
            
           	<input type="button" value="New Campaign" id="create_campaign_button" class="small_button"/>
            
            <div class="margin_bottom_20 horizontal_divider">&#160;</div> 
			<div class="margin_bottom_20">&#160;</div>

			
            <div class="header_01">Search Campaign</div>     
            <div id="search_form">
            	<input type="text" id="search_field" name="campaign" />
            	<input type="button" value="Search" id="search_button" class="small_button" />
            	<div style="clear:both; height: 0px">&#160;</div>
            </div>
            <div class="margin_bottom_20">&#160;</div>
        </div> <!-- end of left side bar -->
        
        <div id="central_panel" class="templatemo_multi_content margin_right_10">
        	<div id="form_message" class="templatemo_content form_message margin_bottom_15 ${form_message==null?'hidden':''}">
        		${form_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
        	<div id="error_message" class="templatemo_content error_message margin_bottom_15 ${error_message==null?'hidden':''}">
        		${error_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
	        <div id="campaign_info" class="templatemo_content dynamic">
	        
	        	<div  class="content_section">
	           	  <div class="header_02">Campaign info</div>
	                <p><span>View and Manage Campaigns.</span> View and manage the campaigns you are GM of, check new combats of the campaigns you are playing.</p>
	               
	                <p><span>Search Campaigns.</span> Search for public campaigns and join them.</p>
	               
	                <p><span>Create Campaign.</span> Create a new Campaign and invite you friends to play.</p>
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="create_campaign" class="templatemo_content dynamic hidden">
	            
	            <div class="content_section">
	            	<div class="header_02">Create Campaign</div>
	            	<c:url value="/campaigns" var="createCampaignUrl"></c:url>
	            	<form:form action="${createCampaignUrl }" modelAttribute="campaign" id="campaign" method="POST">
	            		
	            		<div class="margin_bottom_20">&#160;</div>
						<div class="generic_label">
							<label for="name">Name</label>
						</div>
						<input type="text" id="name" name="name" autocomplete="off"
							class="generic_field"  />
						<form:errors element="div" path="name"/>
						
						<div class="margin_bottom_10">&#160;</div>
						<div class="generic_label">
							<label for="description">Description</label>
						</div>
						<div style="display: inline-flex;">
							<textarea id="description" name="description"
								class="generic_field" rows="4" draggable="true">&#160;</textarea>
							<form:errors element="div" path="description"/>
						</div>
						
						<div class="margin_bottom_10">&#160;</div>
						
						<div class="generic_label">
							<label for="system">System</label>
						</div>
						<select name="systemType" class="generic_field">
							<c:forEach items="${systems }" var="system">
								<option value="${system }">${system }</option>
							</c:forEach>
						</select>
						
						<div class="margin_bottom_10">&#160;</div>
						<div class="generic_label widest">
							<label for="isPublic">Show campaign in search results</label>
						</div>
						<input type="checkbox" id="isPublic" name="isPublic"
							class="generic_field" checked="checked"/>
						
						<div class="margin_bottom_10">&#160;</div>
						<input type="submit" value="Create" class="small_button"></input>
							
	            	</form:form>
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="campaign_search_result" class="templatemo_content dynamic hidden">
	            <div id="search_result_holder" class="content_section">
	            	<div class="header_02">Search Result for <span id="search_param"></span></div>
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="campaign_view" campaign_id="${campaign.id }" class="templatemo_multi_content dynamic hidden">
	            <div class="templatemo_content width_70_p margin_right_10">
	            	<div class="content_section">
		            	<div class="header_02 campaign_header"><span id="campaign_name">${campaign.name }</span></div>
		            	
		            	<div id="campaign_description" class="scroll_description">
			            	<div id="request_join" class="campaign_request_join ${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? 'hidden':''}">
			            		<input id="request_join_button" type="button" value="Join Campaign" class="small_button">
	           					</input>
			            	</div>
			            	<div id="invite_user" class="campaign_request_join ${(campaign.gameMaster.name != pageContext.request.userPrincipal.principal.name)? 'hidden':''}">
			            		<input id="invite_player_button" type="button" value="Invite Friend" class="small_button">
	           					</input>
			            	</div>
		            		${campaign.description }
		            	</div>
		            	<p class="border_top">Current Combat: </p>
		            	<p class="border_top">Game Master: <span id="campaign_gm">${campaign.gameMaster.name }</span></p>
		            </div>
		            <div class="margin_bottom_20">&#160;</div>
		        </div>
		        
		        <div class="templatemo_content width_25_p">
		        	<div class="header_03">Characters:</div>
	           		<div class="margin_bottom_10">&#160;</div>
	           		<div class="content_section nice_list scroll_list_219" id="campaign_active_players">
	           			
	           			<ul id="campaign_active_players_list">
	           				<c:forEach items="${campaign.playerCharacters }" var="pc">
	           					<li pc_id="${pc.id}"><a title="Owner ${pc.owner.name }">${pc.name }</a></li>
	           				</c:forEach>
	           				<c:forEach items="${campaign.monsters }" var="npc">
	           					<li pc_id="${npc.id}"><a>${npc.name }</a></li>
	           				</c:forEach>
	           			</ul>
	           		</div>
	           		<input id="create_monster_button" type="button" campaign_id="${campaign.id }" value="Create Character" 
	           			class="small_button ${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">
	           		</input>
	           		<div class="margin_bottom_20">&#160;</div>
	           	</div>
	           	
	           	<div id="campaign_combats" class="templatemo_content margin_top_15 margin_right_10 width_30_p ${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">
	           		<span>Coming Combats:</span>
					<div class="margin_bottom_10">&#160;</div>
	           		<div class="nice_list">
	           			<ul id="coming_combats_list">
	           				<c:forEach items="${campaign.combats }" var="combat">
	           					<li combat_id="${combat.id}" class="combat_view"><a>${combat.name }</a></li>
	           				</c:forEach>
	           			</ul>
	           		</div>
	           		<input id="create_combat_button" campaign_id="${campaign.id }" type="button" value="Create Combat" 
	           			class="small_button">
	           		</input>
	           		
	           		<div class="margin_bottom_20">&#160;</div>
	           	</div>
	           	
	           	
	           	<div id="campaign_pending_players" class="templatemo_content margin_top_15 margin_right_10 width_30_p ${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">	
	           		<span>Pending Invitations:</span>
					<div class="margin_bottom_10">&#160;</div>
	           		<div class="nice_list">
	           			<ul id="campaign_pending_players_list">
	           				<c:forEach items="${campaign.pendingInvitations }" var="invite">
	           					<c:choose>
	           					<c:when test="${invite.to != null }">
	           						<li><a title="${invite.to.email }">${invite.to.name }</a></li>
	           					</c:when>
	           					<c:otherwise>
	           						<li><a>${invite.mailTo }</a></li>
	           					</c:otherwise>
	           					</c:choose>
	           					
	           				</c:forEach>
	           			</ul>
	           		</div>
	           		<div class="margin_bottom_20">&#160;</div>

	           	</div>
	           	<div id="campaign_requested_players" class="templatemo_content margin_top_15 width_30_p ${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name)? '':'hidden'}">
	           		<span>Pending Requests:</span>
	           		<div class="margin_bottom_10">&#160;</div>
	           		<div class="nice_list">
	           			<ul id="campaign_pending_requests_list">
	           				<c:forEach items="${campaign.pendingRequests }" var="request">
	           					<c:url var="acceptUrl" value="/campaigns/${campaign.id }/accept/${request.id }"/>
	           					<li ><a title="Accept Request" class="accept_request" url="${acceptUrl }">${request.from.name }</a></li>
	           				</c:forEach>
	           			</ul>
	           		</div>
	           		<div class="margin_bottom_20">&#160;</div>
	           	</div>
	            	
	                <div class="cleaner">&#160;</div>
	    			<div class="margin_bottom_40">&#160;</div>
	          </div>  
	            <c:if test="${campaign != null and message != null }">
	            <div id="campaign_join" class="templatemo_content dynamic hidden"> 
		            <div class="content_section">
		            	<div class="header_02">Join ${campaign.name} </div>
		            	<p id="campaign_description">${campaign.description }</p>
		            	<div class="margin_bottom_10">&#160;</div>
		            	<p>You have been invited to join ${campaign.gameMaster.name}'s campaign, create a player character for this campaign.</p>
		            	<c:url var="joinCampaignUrl" value="/player-characters/create"/>
		            	<form action="${joinCampaignUrl }" method="GET">
		            		<input type="hidden" name="messageId" value="${message.id }"/>
		            		<input type="hidden" name="campaignId" value="${campaign.id }"/>
							
							<input type="submit" value="Join" class="small_button"/>
							            		
		            	</form>            	
		            	
		                <div class="cleaner">&#160;</div>
		            </div>
		            <div class="margin_bottom_40">&#160;</div>
	            </div>
	            </c:if>           
	            
	        	
	        </div> <!-- end of content -->
        <jsp:include page="ads.jsp"/>
   		
   		<div id="invite_user_form" style="display:none; cursor: default"> 
   			<form:form id="invite_form">
   				<h2>Invite To Campaign</h2>
   				<p>Send an Invitation to another user and ask him to join your campaign</p>
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
		<c:if test="${not empty show_content}">
	   		$("#central_panel > .dynamic").addClass("hidden");
			$("#${show_content}").removeClass("hidden");
	   	</c:if>
	
		$("#create_campaign_button").click(function() {
			$("#central_panel > .dynamic").addClass("hidden");
			$("#create_campaign").removeClass("hidden");
		})
		<c:url var="searchUrl" value="/campaigns/search/"/>
		$("#search_button").click(function(){
			
			var searchValue = $("#search_field").val();
			$('#search_form').block({message : "<h3>Searching Campaigns...</h3>", css: { border: '2px solid #363434', width: '100%', left: '0px', height: '100%', top: '0px' }})			
			var url = "${searchUrl}" + searchValue
			if(searchValue.length > 0) {
				$.ajax({
					url: url
				}).done(function(result) {
					$("#search_param").text(searchValue + "(" + result.length +")")
					$("#search_result_holder > p").remove();
					var lastItem = $("#search_result_holder > .header_02")
					for(i= 0; i &lt; result.length; i++) {
						var newItem = $("<p/>")
						newItem.append($("<span/>").append(result[i].name))
						lastItem.after(newItem);
						lastItem = newItem;
						var newItem = $("<p/>")
						if(result[i].description) {
							newItem.append(result[i].description)
						} else {
							newItem.append("&#160;");
						}
						lastItem.after(newItem);
						lastItem = newItem;
						var newItem = $("<p/>")
						var link = $("<a/>")
						link.addClass("campaign_list_item")
						link.addClass("search_result")
						link.attr("id", "campaign_" + result[i].id)
						link.append("Show More")
						newItem.append(link)
						lastItem.after(newItem);
						lastItem = newItem;
						var newItem = $("<p/>")
						newItem.append($("<span/>").append("Game Master: " + result[i].game_master.name))
						lastItem.after(newItem);
						lastItem = newItem;
						var newItem = $("<p/>")
						newItem.append("&#160;");
						lastItem.after(newItem);
						lastItem = newItem;
					}
					$("#central_panel > .dynamic").addClass("hidden");
					$("#campaign_search_result").removeClass("hidden");
				}).always(function() {
					$('#search_form').unblock()
				})
			} 
		})
		
		<c:url var="getUrl" value="/campaigns/[id]/show"/>
		$(document).on("click", ".campaign_list_item", function(){
			var url = "${getUrl}".replace("[id]", $(this).attr("id").split("_")[1]);
			window.location = url;
		})
		
		$('#invite_player_button').click(function() { 
            $.blockUI({ message: $('#invite_user_form'), css: { width: '325px' } }); 
        });
        
        <c:url var="inviteUrl" value="/campaigns/[id]/invite/[email]"/>
        $('#send_invite_button').click(function() {
        	var isValid = $("#invite_form").valid();
	          if(isValid) {
	            var campaignId = $("#campaign_view").attr("campaign_id")
	            var email = $("#invite_email").val();
	          	var url = "${inviteUrl}".replace("[id]",campaignId).replace("[email]", email)
	          	$.blockUI({ message: "<h1>Sending invitation...</h1>" });
	          	$.ajax(url).done(function(result) {
	          		if(result == true) {
		          		var pendingList = $("#campaign_pending_players ul")
		          		newItem = $("<li/>")
		          		newItem.text(email);
		          		pendingList.append(newItem);
		          		$.unblockUI();
	          		} else {
	          			alert("Could not send mail");
	          			$.blockUI({ message: $('#invite_user_form'), css: { width: '275px' } }); 
	          		}
	          	});
	          }
        }) 
        
        $('#cancel_invite_button').click(function(){
        	$.unblockUI();
        })
	    
	    $("#invite_form").validate({
	    	rules: {
				email: {
			      required: true,
			      email: true
			    }
			}
	    })
	    
	    $(".toggle_header").click(function(){
	    	var toggle_id = $(this).attr("toggle_id")
	    	$(this).toggleClass("closed")
	    	$(".toggle_child[toggle_id=" + toggle_id + "]").slideToggle("slow")
	    })
	    
	    <c:url var="requestJoinUrl" value="/campaigns/[id]/join/request"/>
	    $("#request_join_button").click(function(){
	    	var campaignId = $("#campaign_view").attr("campaign_id")
	    	var url = "${requestJoinUrl}".replace("[id]", campaignId)
	    	$.blockUI({ message: "<h1>Sending Request...</h1>" })
	    	$.ajax(url).done(function() {
	    		setTimeout(unblock,3000)
	    		$.blockUI({ message: "<h1>Message Sent</h1><p>check your Lobby later for the response.</p>" })
	    	}).error(function(){
	    		setTimeout(unblock,3000)
	    		$.blockUI({ message: "<h1>There was an error sending the request, please try again later.</h1>" })
	    	}) 
	    })
	    
	    function unblock() {
	    	$.unblockUI();
	    }
	    
	    $("#campaign_pending_requests_list").on("click",".accept_request",function() {
	    	var url = $(this).attr("url")
	    	var parent = $(this).parent()
	    	$.ajax(url).done(function() {
	    		parent.remove();
	    	})
	    	
	    })
	    
	    $("#create_monster_button").click(function(){
	    	<c:url var="createMonsterUrl" value="/characters/create?campaignId=[id]"/>
	    	window.location = "${createMonsterUrl}".replace("[id]", $(this).attr("campaign_id"))
	    })
	    
	    $("#campaign_active_players_list li").click(function() {
	    	<c:url var="showCharacterUrl" value="/player-characters/[id]/show"/>
	    	var url = "${showCharacterUrl}".replace("[id]", $(this).attr("pc_id"))
	    	window.location = url;
	    })
	    
	    $("#campaign_monsters_list li").click(function() {
	    	<c:url var="showMonsterUrl" value="/characters/[id]/show"/>
	    	var url = "${showMonsterUrl}".replace("[id]", $(this).attr("npc_id"))
	    	window.location = url;
	    })
	    
	    $("#create_combat_button").click(function() {
	    	<c:url var="createCombatUrl" value="/combats/create?campaignId=[id]"/>
	    	var url = "${createCombatUrl}".replace("[id]", $(this).attr("campaign_id"))
	    	window.location = url;
	    })
	    
	    $(".combat_view").click(function(){
	    	<c:url var="showCombatUrl" value="/combats/[id]/show"/>
	    	var url = "${showCombatUrl}".replace("[id]", $(this).attr("combat_id"))
	    	window.location = url;
	    })
	    
	})
</script> 	
   
</body>
</html>

</jsp:root>