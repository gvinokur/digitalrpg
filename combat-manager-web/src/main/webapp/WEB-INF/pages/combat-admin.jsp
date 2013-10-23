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
	        <div id="combat_info" class="templatemo_content dynamic">
	        
	        	<div  class="content_section">
	           	  <div class="header_02">Combat info</div>
	                <p><span>View and Manage Combats.</span> View and manage your combats, watch live action or start them from this panel.</p>
	              
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="combat_create" class="templatemo_content dynamic">
	        	<div class="header_02">Create Combat</div>
	            <p>You are creating a combat campaign <span>${campaign.name}</span></p>
	        	<c:url var="createCombatUrl" value="/combats/create"/>
	        	<form:form modelAttribute="combat" action="${createCombatUrl }" method="POST">
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
					
					<div class="header_02">Select Characters And Monsters</div>
					<select id='players' multiple='multiple'>
					  <optgroup label='Characters'>
					    <c:forEach items="${campaign.playerCharacters }" var="character">
						    <option value='${character.id }'>${character.name }</option>
					    </c:forEach>
					  </optgroup>
					  <optgroup label='Monsters'>
					    <c:forEach items="${campaign.monsters }" var="monster">
						    <option value='${monster.id }'>${monster.name }</option>
					    </c:forEach>
					  </optgroup>
					</select>
					
	        	</form:form>
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
				
				$("#optgroup-selection-Monsters li.ms-elem-selection").each(function() {
					var id = $(this).attr("id").split("-")[0]
					$(this).before($("<input />")
						.attr("id", id + "-initiative")
						.attr("type","text")
						.attr("name", "monsterData["+id+"].initative")
						.css("display","none")
						.css("float","right")
						.css("width","60px")
						.css("margin-top","0px")
						.css("margin-right","5px"))
					$(this).before($("<input />")
						.attr("id", id + "-hiddencheck")
						.attr("type","checkbox")
						.attr("name", "monsterData["+id+"].hidden")
						.css("display","none")
						.css("float","right")
						.css("margin-right","5px")
						.css("margin-top","2px"))
					
				})
				
				$("#optgroup-selection-Characters li.ms-elem-selection").each(function() {
					var id = $(this).attr("id").split("-")[0]
					$(this).before($("<input />")
						.attr("id", id + "-initiative")
						.attr("type","text")
						.attr("name", "characterData["+id+"].initative")
						.css("display","none")
						.css("float","right")
						.css("width","60px")
						.css("margin-top","0px")
						.css("margin-right","5px"))
									
				})
				
				<c:if test="${not empty show_content}">
			   		$("#central_panel > .dynamic").addClass("hidden");
					$("#${show_content}").removeClass("hidden");
			   	</c:if>
			})
		</script>
	</body>
</html>
</jsp:root>