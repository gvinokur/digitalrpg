<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />

<script>
	$(document).ready(function(){
		reloadNews();
	})
	
	<c:url var="newsUrl" value="/messages"/>
	function reloadNews() {
		var url = "${newsUrl}"
		$.ajax(url).done(function(result){
			$("#messages > :not(.header_01)").remove();
			for(i=0; i &lt; result.length; i++) {
				drawMessage(result[i]);
			}
		}).always(function(){
			setTimeout(reloadNews,5000)
		}) 
	}
	
	function drawMessage(message) {
		if(message.type == "InviteMessageVO") {
			drawMessageInvite(message)
		} else if (message.type == "RequestJoinVO") {
			drawMessageRequestJoin(message)
		}
	}
	<c:url var="inviteUrl" value="/campaigns?show_form=join&amp;campaign_id=[id]&amp;message_id=[message_id]"/>
	function drawMessageInvite(message) {
		var newDiv = $("<div/>")
		var url = "${inviteUrl}".replace("[id]",message.campaign_id).replace("[message_id]", message.id)
		newDiv.addClass("latest_news")
		newDiv.addClass("border_bottom")
		var title = $("<a/>").attr("href",url).append("Join " + message.from.name + "'s campaign")
		newDiv.append($("<div/>").addClass("header03").append(title))
		$("<p/>")
			.append("Join one of your characters to the campaign " +  message.campaign_name + " ")
			.appendTo(newDiv)
		newDiv.appendTo($("#messages"))
	}
	
	function drawMessageRequestJoin(message) {
	}
</script>
	<div id="messages" class="templatemo_side_bar">

		<div class="header_01">Messages</div>

	</div>
	<!-- end of right side bar -->


</jsp:root>