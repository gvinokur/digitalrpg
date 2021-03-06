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
<title>Characters</title>
</head>
<body>
    
    	<div class="templatemo_side_bar margin_right_10">
        	
            <div class="header_01">My Characters</div>
           	<ul class="campaigns">
           		<c:forEach items="${characters}" var="character">
           			<c:url var="characterUrl" value="/characters/${character.id }/show"/>
           			<li class="player"><a id="npc_${character.id }" href="${characterUrl }">${character.character.name }</a></li>
           		</c:forEach>
                   
               </ul>
            
			<div class="margin_bottom_20">&#160;</div>

        </div> <!-- end of left side bar -->
        
        <div id="central_panel" class="templatemo_multi_content margin_right_10" >
        
        	<div id="character_info" class="templatemo_content dynamic ${show_content == null?'':'hidden' }">
	        	<div class="content_section">
	           	  <div class="header_02">Character info</div>
	                <p><span>View and Manage Characters.</span> View and Edit your campaign characters.</p>
	     
	               <div class="margin_bottom_40">&#160;</div>
	                <div class="cleaner">&#160;</div>
	            </div>
            </div>
            
            <div id="create_character"  class="templatemo_content dynamic ${show_content == 'create_character'?'':'hidden' }">
            	<div class="content_section">
	            	<div class="header_02">Create Character</div>
	            	<p>You are creating a character for your campaign <span>${campaign.name}</span></p>
	            	
	            	<c:url value="/characters" var="createCharacterUrl"></c:url>
	            	<form:form action="${createCharacterUrl }" modelAttribute="createCharacter" id="character" method="POST">
	            		
	            		<input type="hidden" name="campaignId" value="${campaign.id}"/>
	            		
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
            
        	<div id="view_character" class="templatemo_multi_content ${show_content == 'view_character'?'':'hidden' } dynamic">
	        	<div class="templatemo_content width_70_p margin_right_10">
	        		<div class="content_section">
		        		<div class="header_02" id="character_name">${character.character.name }</div>
		        		<div class="scroll_description long" id="character_description">${character.character.description }</div>
		        		<div class="border_top" id="character_campaign">Campaign ${character.campaign.name }</div>
		        	</div>
		        	<div class="margin_bottom_40">&#160;</div>
	        	</div>
	        	<c:if test="${character.character.pictureUrl != null }">
		        	<div class="templatemo_content width_25_p character_image">
	        			<img src="${character.character.pictureUrl}"/>
		        	</div>
	        	</c:if>
	        	<div id="system_attributes ">
	        		<div class="templatemo_content width_25_p margin_top_15">
	        			<c:choose>
	        				<c:when test="${character.campaign.system == 'Pathfinder' }">	
	        					<jsp:include page="systems/character/pathfinder_data.jsp"/>
	        				</c:when>
	        			</c:choose>
		        	</div>
	        	</div>
	        		
	        </div>
        	
        	
        	
        </div> <!-- end of content -->
        
        
        
        <jsp:include page="ads.jsp"/>
   	
   
</body>
</html>

</jsp:root>