<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:sec="http://www.springframework.org/security/tags"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<html>
<head>
<title>Lobby</title>
</head>
<body>
	<sec:authentication property="principal" var="user"/>
	<div class="container main">
		<div class="row">
			<div class="hidden-xs col-sm-3 content-block">
				<div class="cbp-spmenu" id="accordion">
					<div class="">
						<div class="">
							<h5>
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseRecent"> <spring:message code="lobby.recentlyvisited"/> <span
									id="collapseRecentIcon"
									class="pull-right glyphicon glyphicon-chevron-down"></span>
								</a>
							</h5>
						</div>
						<div id="collapseRecent" class="collapse in">
							<div class="scroll_list_150 nice_list">
								<c:forEach items="${recentItems }" var="recentItem">
				           			<c:url value="${recentItem.url }" var="recentItemUrl"></c:url>
				               		<a href="${recentItemUrl }">${recentItem.title }</a>
				                </c:forEach>
							</div>
						</div>
					</div>
					<div class="">
						<div class="">
							<h5>
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseFriends"><spring:message code="lobby.friends"/> <span
									id="collapseFriendsIcon"
									class="pull-right glyphicon glyphicon-chevron-down"></span>
								</a>
							</h5>
						</div>
						<div id="collapseFriends" class="collapse in">
							<div class="scroll_list_150 nice_list">
								<ul class="">
									
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-7">
				<!--  -->
				<div class="row">
					<div class="col-xs-12 content-block center ">
						<h3><spring:message code="lobby.message.title" arguments="${user.name }"/></h3>
						<spring:message code="lobby.message"/>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 content-block center bottom" id="messageHolder">
						<h3><spring:message code="lobby.messages"/></h3>
						
					</div>
				</div>
			</div>
			<div class="col-sm-2 hidden-xs content-block">
				ADS HERE
			</div>
		</div>
	</div>
	
	<div message-id="template" class="hidden message panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"></h3>
  		</div>
  		<div class="panel-body">
  		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#messageHolder').tooltip({
			    selector: '.tooltipable'
			})
			
			$('#messageHolder').on("click", "button:not(.delete_message)", function() {
				window.location = $(this).attr('action')
			})
			
			$("#messageHolder").on("click", ".delete_message", function() {
				var messageDiv = $(this).parents(".message");
				var url = $(this).attr("action")
				
				$.ajax({
					url : url,
					type : "DELETE",
					beforeSend : beforePost
				}).done(function() {
					messageDiv.remove();
				})
			});
		})
	
		function processMessages(messages) {
			var toDelete = $(".message").not("[message-id=template]")
			for(var i=0;i  &lt; messages.length;i++) {
				var message = messages[i]
				if ($(".message[message-id=" + message.id +"]").size() == 0) {
					var messageDiv = $(".message[message-id=template]").clone();
					messageDiv.attr('message-id', message.id);
					messageDiv.removeClass("hidden")
					if(message.type == "InviteMessageVO") {
						drawMessageInvite(messageDiv, message)
					} else if (message.type == "RequestJoinMessageVO") {
						drawMessageRequestJoin(messageDiv, message)
					} else if (message.type == "AcceptRequestJoinMessageVO") {
						drawMessageAcceptRequestJoin(messageDiv, message)
					} else if (message.type == "InviteClaimCharacterMessageVO") {
						drawMessageInviteCharacter(messageDiv, message)
					}
					$("#messageHolder").append(messageDiv)
				} else {
					toDelete.not("[message-id=" + message.id +"]")
				}
			}
			toDelete.remove();
		}
		
		<c:url var="inviteUrl" value="/campaigns/[id]/join?messageId=[message_id]"/>
		<c:url var="deleteUrl" value="/messages/[id]"/>
		function drawMessageInvite(messageDiv, message) {
			var url = "${inviteUrl}".replace("[id]",message.campaign_id).replace("[message_id]", message.id)
			var deleteUrl = "${deleteUrl}".replace("[id]", message.id)
			var msg = '<spring:message code="lobby.messages.invite.title"/>'.replace("[from]", message.from.name).replace('[campaign]', message.campaign_name)  
			messageDiv.find(".panel-title").append(msg)
			var panelBody = messageDiv.find(".panel-body")
			$("<button/>").attr("action", deleteUrl).attr('title','<spring:message code="lobby.messages.delete"/>').addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-remove").addClass("delete_message").appendTo(panelBody)
				
			$("<button/>").attr("action", url).attr('title','<spring:message code="lobby.messages.request.accept"/>').addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-ok").appendTo(panelBody)
				
			$("<p/>")
				.append('<spring:message code="lobby.messages.invite"/>')
				.appendTo(panelBody)
		}
		
		
		<c:url var="acceptRequestUrl" value="/campaigns/[id]/accept/[message_id]"/>
		function drawMessageRequestJoin(messageDiv, message) {
			var url = "${acceptRequestUrl}".replace("[id]",message.campaign_id).replace("[message_id]", message.id)
			
			var title = $("<a/>").attr("href",url).append( message.from.name + " request")
			var msg = '<spring:message code="lobby.messages.requestaccess.title"/>'.replace("[from]", message.from.name).replace('[campaign]', message.campaign_name)
			messageDiv.find(".panel-title").append(msg)
			var panelBody = messageDiv.find(".panel-body")
			$("<button/>").attr("action", deleteUrl).attr('title','<spring:message code="lobby.messages.delete"/>').appendTo(panelBody).addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-remove").addClass("delete_message")
				
			$("<button/>").attr("action", url).attr('title','<spring:message code="lobby.messages.request.accept"/>').appendTo(panelBody).addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-ok")
			
			
			$("<p/>")
				.append('<spring:message code="lobby.messages.requestaccess"/>'.replace("[from]", message.from.name).replace('[campaign]', message.campaign_name))
				.appendTo(panelBody)
			
		}
		
		<c:url var="showCampaignUrl" value="/campaigns/[id]/show"/>
		function drawMessageAcceptRequestJoin(messageDiv, message) {
			var url = "${showCampaignUrl}".replace("[id]",message.campaign_id)
			var deleteUrl = "${deleteUrl}".replace("[id]", message.id)
			
			var msg = '<spring:message code="lobby.messages.request.accepted.title"/>'.replace("[from]", message.from.name).replace('[campaign]', message.campaign_name)
			messageDiv.find(".panel-title").append(msg)
			var panelBody = messageDiv.find(".panel-body")
			$("<button/>").attr("action", deleteUrl).attr('title','<spring:message code="lobby.messages.delete"/>').appendTo(panelBody).addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-remove").addClass("delete_message")
				
			$("<button/>").attr("action", url).attr('title','<spring:message code="lobby.messages.campaign.open"/>').appendTo(panelBody).addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
				.addClass("glyphicon-share-alt")
			
			$("<p/>")
				.append('<spring:message code="lobby.messages.request.accepted"/>'.replace("[from]", message.from.name).replace('[campaign]', message.campaign_name))
				.appendTo(panelBody)
				
			
		}
		
		<c:url var="inviteUrl" value="/characters/[id]/show?messageId=[message_id]"/>
		function drawMessageInviteCharacter(messageDiv, message) {
			var url = "${inviteUrl}".replace("[id]",message.character_id).replace("[message_id]", message.id)
			var deleteUrl = "${deleteUrl}".replace("[id]", message.id)
			var title = $("<a/>").attr("href",url).append("Claim your character and join " + message.from.name + "'s campaign")
			$("<p/>")
				.append("Claim the character " + message.characterName + " on the " +  message.campaign_name + " campaign ")
				
			
		}
		
	</script>
	
	
</body>
</html>
</jsp:root>