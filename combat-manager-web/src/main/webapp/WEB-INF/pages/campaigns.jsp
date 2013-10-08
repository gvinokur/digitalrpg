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
           					<li class="gm"><a id="campaign_${campaign.id }">${campaign.name }</a></li>
           				</c:when>
           				<c:otherwise>
           					<li class="player"><a id="campaign_${campaign.id }">${campaign.name }</a></li>
           				</c:otherwise>
           			</c:choose>
           		</c:forEach>
                   
               </ul>
            
            <div class="margin_bottom_20">&#160;</div>
            
            
           	<input type="button" value="New Campaign" id="create_campaign_button" class="small_button"/>
            
            <div class="margin_bottom_20 horizontal_divider">&#160;</div> 
			<div class="margin_bottom_20">&#160;</div>

            <div class="header_01">Search Campaign</div>     
            <input type="text" id="search_field" name="campaign" />
            <input type="button" value="Search" id="search_button" class="small_button" />
            
            <div class="margin_bottom_20">&#160;</div>
        </div> <!-- end of left side bar -->
        
        <div id="central_panel" class="templatemo_content margin_right_10">
        
        	<div id="campaign_info" class="content_section">
           	  <div class="header_02">Campaign info</div>
                <p><span>View and Manage Campaigns.</span> Green campaigns are the ones you are GM of, black ones are the ones you are participating in.</p>
               
                <p><span>Search Campaigns.</span> Search for public campaigns and join them.</p>
               
                <p><span>Create Campaign.</span> Create a new Campaign and invite you friends to play.</p>
               <div class="margin_bottom_20">&#160;</div>
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
                <div class="margin_bottom_20">&#160;</div>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div id="campaign_search_result" class="content_section hidden">
            	<div class="header_02">Search Result for <span id="search_param"></span></div>
            	<div class="margin_bottom_20">&#160;</div>
                <div class="cleaner">&#160;</div>
            </div>
            
            <div class="margin_bottom_20 horizontal_divider">&#160;</div> 
            
            
        	<div class="margin_bottom_40">&#160;</div>
        </div> <!-- end of content -->
        
        <div class="templatemo_side_bar">
       		
            <div class="header_01">Latest News</div>
            
<!--             <div class="latest_news border_bottom"> -->
<!--                 <div class="header_03"><a href="#">Aenean a bibendum augue</a></div> -->
<!--                 <p>Fusce egestas feugiat turpis, ac ultrices turpis vestibulum at.</p> -->
<!-- 			</div> -->
                        
<!--             <div class="margin_bottom_10">&#160;</div> -->
            
<!-- 			<div class="latest_news"> -->
<!--                 <div class="header_03"><a href="#">Sed nec enim magna</a></div> -->
<!--                 <p>Proin lectus orci, iaculis at facilisis sed, sodales a neque.</p> -->
<!-- 			</div> -->
            
<!--           	<div class="margin_bottom_20 horizontal_divider">&#160;</div>  -->
<!-- 			<div class="margin_bottom_20">&#160;</div> -->
            
<!--             <div class="header_01">Sample Video</div> -->

<!--             <div class="latest_news"> -->
<!--             	<div class="image_wrapper_02"><span></span><a href="#"><img src="images/templatemo_image_03.jpg" alt="video" /></a></div> -->
<!--                 <p>Donec venenatis tellus non massa blandit vitae volutpat urna fringilla. Aenean ante lorem, vestibulum eu lacinia.</p> -->
<!-- 	     	 </div> -->
                        
<!--           <div class="margin_bottom_10">&#160;</div> -->
        </div> <!-- end of right side bar -->
   	
   
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
			//TODO: Replace button text and disable it
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
					//TODO: Restore button text and enable
				})
			} 
		})
	})
</script> 	
   
</body>
</html>

</jsp:root>