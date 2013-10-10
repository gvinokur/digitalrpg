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
        	
            <div class="header_01">My Campaigns</div>
           	<ul class="campaigns">
           		<c:forEach items="${campaigns}" var="campaign">
           			<c:choose>
           				<c:when test="${campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name }">
           					<li class="gm"><a class="campaign_list_item" id="campaign_${campaign.id }">${campaign.name }</a></li>
           				</c:when>
           				<c:otherwise>
           					<li class="player"><a class="campaign_list_item" id="campaign_${campaign.id }">${campaign.name }</a></li>
           				</c:otherwise>
           			</c:choose>
           		</c:forEach>
                   
               </ul>
            
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
        
        <div id="central_panel" class="templatemo_content margin_right_10">
        
        	<div id="campaign_info" class="content_section">
           	  <div class="header_02">Campaign info</div>
                <p><span>View and Manage Campaigns.</span> Green campaigns are the ones you are GM of, black ones are the ones you are participating in.</p>
               
                <p><span>Search Campaigns.</span> Search for public campaigns and join them.</p>
               
                <p><span>Create Campaign.</span> Create a new Campaign and invite you friends to play.</p>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div id="create_campaign"  class="content_section hidden">
            	<div class="header_02">Create Campaign</div>
            	<c:url value="/campaigns" var="createCampaignUrl"></c:url>
            	<form:form action="${createCampaignUrl }" modelAttribute="campaign" id="campaign" method="POST">
            	
            		
            		
            		<div class="margin_bottom_20">&#160;</div>
					<div class="campaign_label">
						<label for="name">Name</label>
					</div>
					<input type="text" id="name" name="name" autocomplete="off"
						class="field"  />
					<form:errors element="div" path="name"/>
					
					<div class="margin_bottom_20">&#160;</div>
					<div class="campaign_label">
						<label for="description">Description</label>
					</div>
					<textarea id="description" name="description"
						class="field" rows="4" draggable="true">&#160;</textarea>
					<form:errors element="div" path="description"/>
					
					<div class="margin_bottom_20">&#160;</div>
					<div class="campaign_checkbox_label">
						<label for="isPublic">Show campaign in search results</label>
					</div>
					<input type="checkbox" id="isPublic" name="isPublic"
						class="field" checked="checked"/>
					
					<input type="submit" value="Create" class="small_button"></input>
						
            	</form:form>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div id="campaign_search_result" class="content_section hidden">
            	<div class="header_02">Search Result for <span id="search_param"></span></div>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div id="campaign_view" class="content_section hidden">
            	<div class="header_02"><span id="campaign_name"></span></div>
            	<p id="campaign_description"></p>
            	<p>Game Master: <span id="campaign_gm"></span></p>
            	
           		<div id="campaign_active_players">
           			<span>Active Players:</span>
           			<ul id="campaign_active_players_list">
           			</ul>
           		</div>
           		<div id="campaign_pending_players" class="hidden">
           			<span>Pending Invitations:</span>
           			<ul id="campaign_pending_players_list">
           			</ul>
           		</div>
           		<div id="campaign_requested_players" class="hidden">
           			<span>Pending Requests:</span>
           			<ul id="campaign_pending_requests_list">
           			</ul>
           		</div>
           		<input id="invite_player_button" type="button" value="Invite Player" class="small_button hidden">
           		</input>            		
            	
                <div class="cleaner">&#160;</div>
            </div>
            
            <c:if test="${campaign != null and message != null }"> 
            <div id="campaign_join" class="content_section hidden">
            	<div class="header_02">Join ${campaign.name} </div>
            	<p id="campaign_description">${campaign.description }</p>
            	<p>You have been invited to join ${campaign.gameMaster.name}'s campaign, select one of your player characters to add to the campaign.</p>
            	<c:url var="joinCampaignUrl" value="/campaigns/${campaign.id }/join"/>
            	<form:form action="${joinCampaignUrl }" modelAttribute="joinCampaign" method="POST">
            		<input type="hidden" name="messageId" value="${message.id }"/>
					<div class="campaign_label">
						<label for="player_character">Player Character</label>
					</div>
					<select name="characterId" id="player_character">
						<c:forEach items="${characters }" var="character">
							<option value="${character.id }">${character.character.name }</option>
						</c:forEach>
					</select>
					
					<input type="submit" value="Join" class="small_button"/>
					            		
            	</form:form>            	
            	
                <div class="cleaner">&#160;</div>
            </div>
            </c:if>           
            
        	<div class="margin_bottom_40">&#160;</div>
        </div> <!-- end of content -->
        
        <jsp:include page="ads.jsp"/>
   		
   		<div id="invite_user_form" style="display:none; cursor: default"> 
   			<form:form id="invite_form">
   				<h1>Invite To Campaign</h1>
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
	   		$("#central_panel > .content_section").addClass("hidden");
			$("#${show_content}").removeClass("hidden");
	   	</c:if>
	
		$("#create_campaign_button").click(function() {
			$("#central_panel > .content_section").addClass("hidden");
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
					$("#campaign_search_result > p").remove();
					var lastItem = $("#campaign_search_result > .header_02")
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
						newItem.append($("<span/>").append("Game Master: " + result[i].game_master.name))
						lastItem.after(newItem);
						lastItem = newItem;
						var newItem = $("<p/>")
						newItem.append("&#160;");
						lastItem.after(newItem);
						lastItem = newItem;
					}
					$("#central_panel > .content_section").addClass("hidden");
					$("#campaign_search_result").removeClass("hidden");
				}).always(function() {
					$('#search_form').unblock()
				})
			} 
		})
		
		<c:url var="getUrl" value="/campaigns/"/>
		$(".campaign_list_item").click(function(){
			var url = "${getUrl}" + $(this).attr("id").split("_")[1];
			$.ajax(url).done(function(campaign) {
				$("#campaign_view").attr("campaign_id", campaign.id)
				$("#campaign_name").text(campaign.name)
				$("#campaign_description").text(campaign.description)
				$("#campaign_gm").text(campaign.game_master.name)
				
				$("#campaign_active_players_list li").remove()
				for(i=0; i &lt; campaign.player_characters.length; i++) {
					var newLine = $("<li/>")
					newLine.attr("id", "campaign_pc_" + campaign.player_characters[i].id)
					newLine.html("<span>" + campaign.player_characters[i].character.name + "</span> Owner: " + campaign.player_characters[i].character.owner.name)
					$("#campaign_active_players_list").append(newLine)
				}
				
				$("#campaign_pending_players_list li").remove()
				for(i=0; i &lt; campaign.pending_invitations.length; i++) {
					var newLine = $("<li/>")
					if(campaign.pending_invitations[i].to) {
						newLine.html("<span>" + campaign.pending_invitations[i].to.name + "</span> Email: " + campaign.pending_invitations[i].to.email)
					}else {
						newLine.html("<span>" + campaign.pending_invitations[i].mail_to + "</span>")
					}
					$("#campaign_pending_players_list").append(newLine)
					
				}
				var principalName = "${pageContext.request.userPrincipal.principal.name}"
				if(principalName == campaign.game_master.name) {
					$("#campaign_pending_players").removeClass("hidden");
					$("#campaign_requested_players").removeClass("hidden");
					$("#invite_player_button").removeClass("hidden");	
				} else {
					$("#campaign_pending_players").addClass("hidden");
					$("#campaign_requested_players").addClass("hidden");
					$("#invite_player_button").addClass("hidden");
				}
				$("#central_panel > .content_section").addClass("hidden");
				$("#campaign_view").removeClass("hidden");
			})
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
	})
</script> 	
   
</body>
</html>

</jsp:root>