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
           		<c:forEach items="${characters}" var="character">
           			<li class="player"><a id="pc_${character.id }">${character.character.name }</a></li>
           		</c:forEach>
                   
               </ul>
            
			<div class="margin_bottom_20">&#160;</div>

        </div> <!-- end of left side bar -->
        
        <div id="central_panel" class="templatemo_content margin_right_10">
        
        	<div id="character_info" class="content_section">
           	  <div class="header_02">Characters info</div>
                <p><span>View and Manage Characters.</span> View your character info and the campaign and combats they are involved in.</p>
     
               <div class="margin_bottom_20">&#160;</div>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div id="create_character"  class="content_section hidden">
            	<div class="header_02">Create Campaign</div>
            	<p>You are creating a character for ${campaign.gameMaster.name }'s campaign <span>${campaign.name}</span></p>
            	
            	<c:url value="/player-characters" var="createCharacterUrl"></c:url>
            	<form:form action="${createCharacterUrl }" modelAttribute="character" id="character" method="POST">
            		
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
						<label for="description">Description</label>
					</div>
					<div style="display: inline-flex;">
						<textarea id="description" name="description"
							class="generic_field" rows="4" draggable="true">&#160;</textarea>
						<form:errors element="div" path="description"/>
					</div>
					
					<div class="margin_bottom_20">&#160;</div>
					<div class="generic_label narrowest">
						<label for="race">Race</label>
					</div>
					<input type="text" id="race" name="race" autocomplete="off"
						class="generic_field narrowest" />
					
					<div class="generic_label narrowest">
						<label for="class">Class</label>
					</div>
					<input type="text" id="class" name="class" autocomplete="off"
						class="generic_field narrowest" />
					
					<div class="generic_label narrowest">
						<label for="hp">HP</label>
					</div>
					<input type="text" id="hp" name="hp" autocomplete="off"
						class="generic_field tinier" />
					
					<div class="character_main_attributes pathfinder">
						<div class="header_02">Abilities</div>
						<div class="generic_label narrow">
							<label for="pathfinder.strength">Strength</label>
						</div>
						<input type="text" id="pathfinder_strength" name="pathfinder.strength" autocomplete="off"
							class="generic_field tinier" />
							
						<div class="generic_label narrow">
							<label for="pathfinder.dexterity">Dexterity</label>
						</div>
						<input type="text" id="pathfinder_dexterity" name="pathfinder.dexterity" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.constitution">Constitution</label>
						</div>
						<input type="text" id="pathfinder_constitution" name="pathfinder.constitution" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.intelligence">Intelligence</label>
						</div>
						<input type="text" id="pathfinder_intelligence" name="pathfinder.intelligence" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.wisdom">wisdom</label>
						</div>
						<input type="text" id="pathfinder_wisdom" name="pathfinder.wisdom" autocomplete="off"
							class="generic_field tinier" />	
							
						<div class="generic_label narrow">
							<label for="pathfinder.charisma">Charisma</label>
						</div>
						<input type="text" id="pathfinder_charisma" name="pathfinder.charisma" autocomplete="off"
							class="generic_field tinier" />		
					</div>
					
					<input type="submit" value="Create" class="small_button"></input>
						
            	</form:form>
                <div class="margin_bottom_20">&#160;</div>
                <div class="cleaner">&#160;</div>
            </div>
            
        	<div class="margin_bottom_40">&#160;</div>
        </div> <!-- end of content -->
        
        <jsp:include page="ads.jsp"/>
   	
   
<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${not empty show_content}">
	   		$("#central_panel > .content_section").addClass("hidden");
			$("#${show_content}").removeClass("hidden");
	   	</c:if>
	
		$("#create_character_button").click(function() {
			$("#central_panel > .content_section").addClass("hidden");
			$("#create_character").removeClass("hidden");
		})
		
	})
</script> 	
   
</body>
</html>

</jsp:root>