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
		$("#messages").on("click", ".delete_message", function() {
			var url = $(this).attr("deleteUrl")
			$.ajax({
				url : url,
				type : "DELETE",
				headers: {
					"X-CSRF-TOKEN": "${_csrf.token}"
				}
			})
		});
	})
	
	var timeout
	
	<c:url var="newsUrl" value="/messages"/>
	function reloadNews() {
		var url = "${newsUrl}"
		$.ajax(url).done(function(result){
			$("#messages > :not(.header_02)").remove();
			for(i=0; i &lt; result.length; i++) {
				drawMessage(result[i]);
			}
		}).always(function(){
			timeout = setTimeout(reloadNews,5000)
		}) 
	}
	
	function drawMessage(message) {
		if(message.type == "InviteMessageVO") {
			drawMessageInvite(message)
		} else if (message.type == "RequestJoinVO") {
			drawMessageRequestJoin(message)
		}
	}
	<c:url var="inviteUrl" value="/campaigns/[id]/join?messageId=[message_id]"/>
	<c:url var="deleteUrl" value="/messages/[id]"/>
	function drawMessageInvite(message) {
		var newDiv = $("<div/>")
		var url = "${inviteUrl}".replace("[id]",message.campaign_id).replace("[message_id]", message.id)
		var deleteUrl = "${deleteUrl}".replace("[id]", message.id)
		newDiv.addClass("latest_news")
		newDiv.addClass("border_bottom")
		var title = $("<a/>").attr("href",url).append("Join " + message.from.name + "'s campaign")
		var deleteIcon = $("<span/>").attr("deleteUrl",deleteUrl).attr("title", "Delete Message").addClass("delete_message").addClass("delete_icon")
		newDiv.append($("<div/>").addClass("header_03").append(title).append(deleteIcon))
		$("<p/>")
			.append("Join one of your characters to the campaign " +  message.campaign_name + " ")
			.appendTo(newDiv)
		newDiv.appendTo($("#messages"))
	}
	
	function drawMessageRequestJoin(message) {
	}
</script>
	<div class="templatemo_content margin_top_15">

		<div id="messages" class="content_section">
	        <div class="header_02">Messages</div>

		</div>
	</div>
	<!-- end of right side bar -->


</jsp:root>