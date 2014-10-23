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
<title>Campaigns</title>
</head>
<body>
	<sec:authentication property="principal" var="user" />

	<content tag="local_submenu">
	<h4>My Campaigns</h4>
	<h5>
		<a data-toggle="collapse" data-parent="#accordion"
			href="#collapsePlayerSmall"> I'm a player in <span
			id="collapsePlayerIcon"
			class="pull-right glyphicon glyphicon-chevron-down"></span>
		</a>
	</h5>
	<div id="collapsePlayerSmall" class="collapse">
		<c:forEach items="${campaigns}" var="campaign">
			<c:if test="${campaign.gameMaster.name != user.name }">
				<c:url value="/campaigns/${campaign.id }/show" var="url"></c:url>
				<a class="campaign_list_item" id="campaign_${campaign.id }"
					href="${url }">${campaign.name }</a>
			</c:if>
		</c:forEach>

	</div>
	<h5>
		<a data-toggle="collapse" data-parent="#accordion"
			href="#collapseGMSmall"> I'm a GM of <span
			class="pull-right glyphicon glyphicon-chevron-down"></span>
		</a>
	</h5>
	<div id="collapseGMSmall" class="collapse">
		<c:forEach items="${campaigns}" var="campaign">
			<c:if test="${campaign.gameMaster.name == user.name }">
				<c:url value="/campaigns/${campaign.id }/show" var="url"></c:url>
				<a class="campaign_list_item" id="campaign_${campaign.id }"
					href="${url }">${campaign.name }</a>
			</c:if>
		</c:forEach>
	</div>
	</content>

	<div class="container main">
		<div class="row">
			<div class="hidden-xs col-sm-3 content-block">
				<div class="cbp-spmenu" id="accordion">
					<h4 class="inverse">My Campaigns</h4>
					<div class="">
						<div class="">
							<h5>
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapsePlayer"> I'm a player in <span
									id="collapsePlayerIcon"
									class="pull-right glyphicon glyphicon-chevron-down"></span>
								</a>
							</h5>
						</div>
						<div id="collapsePlayer" class="collapse">
							<div class="scroll_list_150">
								<c:forEach items="${campaigns}" var="campaign">
									<c:if test="${campaign.gameMaster.name != user.name }">
										<c:url value="/campaigns/${campaign.id }/show" var="url"></c:url>
										<a class="campaign_list_item short" id="campaign_${campaign.id }"
											href="${url }">${campaign.name }</a>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="">
						<div class="">
							<h5>
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseGM"> I'm a GM of <span
									class="pull-right glyphicon glyphicon-chevron-down"></span>
								</a>
							</h5>
						</div>
						<div id="collapseGM" class="collapse">
							<div class="scroll_list_150">
								<c:forEach items="${campaigns}" var="campaign">
									<c:if test="${campaign.gameMaster.name == user.name }">
										<c:url value="/campaigns/${campaign.id }/show" var="url"></c:url>
										<a class="campaign_list_item short" id="campaign_${campaign.id }"
											href="${url }">${campaign.name }</a>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>

				</div>
				<input type="button" value="New Campaign"
					id="create_campaign_button"
					style="margin-top: 20px; margin-bottom: 20px;"
					class="btn btn-default btn-block btn-sm" />


				<div class="horizontal_divider">
					<!--  -->
				</div>
				<div class="cbp-spmenu">
					<h4 class="inverse">Search Campaign</h4>

				</div>
				<div class="row search" style="margin-top: 20px; margin-bottom: 20px;">
					<div class="col-md-9 col-sm-12">
						<input class="form-control" type="text" id="search_field"
							name="campaign" placeholder="Enter keywords" />
					</div>
					<div class="col-md-3 col-sm-3">
						<a role="button" id="search_button" class="btn btn-default"
							data-loading-text="&lt;i class='fa fa-spinner fa-spin'/&gt;">
							<span class="glyphicon glyphicon-search"></span>
						</a>
					</div>
				</div>
				<!-- 					<div class="row search"> -->
				<!-- 						<div class="col-xs-5 col-xs-offset-7"> -->
				<!-- 							<input type="button" id="search_button" -->
				<!-- 								class="btn btn-default btn-block btn-sm glyphicon glyphicon-search" /> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
			</div>
			<script type="text/javascript">
				$(document)
						.ready(
								function() {
									$(".collapse")
										.on('shown.bs.collapse', function() {
											$("a[href=#" + $(this).attr("id") + "] span").removeClass(
													"glyphicon-chevron-down")
												.addClass(
														"glyphicon-chevron-up");
										})
										
									$(".collapse")
										.on('hidden.bs.collapse', function() {
											$("a[href=#" + $(this).attr("id") + "] span").removeClass(
													"glyphicon-chevron-up")
												.addClass(
														"glyphicon-chevron-down");
										})
									
									$("#create_campaign_button").click(function() {
										$(".dynamic").addClass("hidden");
										$("#campaign_create").removeClass("hidden");
										$("#campaign_create").find("[name='id'], [name='description'], [name='name']").val("");
										$("#campaign_create").find("[type='submit']").val("Create");
										$("#campaign_create").find("#systemType").removeClass("hidden");
									})
								})
			</script>
			<div class="col-xs-12 col-sm-7">
				<c:if test="${form_message!=null}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&#215;</button>
						${form_message }
					</div>
				</c:if>
				<c:if test="${warning_message!=null}">
					<div class="alert alert-warning">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&#215;</button>
						${warning_message }
					</div>
				</c:if>
				<c:if test="${error_message!=null}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&#215;</button>
						${error_message }
					</div>
				</c:if>
				<div class="row">
					<div id="campaign_info"
						class="col-xs-12 content-block center dynamic ${show_content == null?'':'hidden' }">
						<h3>Campaign info</h3>
						<p>
							<strong>View and Manage Campaigns.</strong> View and manage the
							campaigns you are GM of, check new combats of the campaigns you
							are playing.
						</p>

						<p>
							<strong>Search Campaigns.</strong> Search for public campaigns
							and join them.
						</p>

						<p>
							<strong>Create Campaign.</strong> Create a new Campaign and
							invite you friends to play.
						</p>
					</div>

					<div id="campaign_create"
						class="col-xs-12 content-block center dynamic ${show_content == 'create_campaign'? '' : 'hidden' }">
						<h3>Create Campaign</h3>
						<c:url value="/campaigns" var="createCampaignUrl"></c:url>
						<form:form action="${createCampaignUrl }"
							modelAttribute="createCampaignVO" id="create-campaign"
							method="POST">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<input type="hidden" name="id" value="${createCampaignVO.id}" />
							<spring:bind path="name">
								<div class="form-group ${status.error? 'has-error' :'' }">
									<label class="control-label" for="name">Name <form:errors
											path="name" />
									</label> <input type="text" class="form-control" id="name" name="name"
										value="${createCampaignVO.name }" />

								</div>
							</spring:bind>

							<div class="form-group">
								<label class="form-text" for="description">Description</label>
								<textarea class="form-control" rows="4" id="description"
									name="description">${createCampaignVO.description }</textarea>
								<form:errors element="div" path="name" />
							</div>

							<div
								class="form-group ${createCampaignVO.id == null ? '' : 'hidden' }"
								id="systemType">
								<label class="form-text" for="system">System</label> <select
									name="systemType" class="form-control">
									<c:forEach items="${systems }" var="system">
										<c:choose>
											<c:when test="${createCampaignVO.systemType == system }">
												<option value="${system }" selected="selected">${system }</option>
											</c:when>
											<c:otherwise>
												<option value="${system }">${system }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>

							<div class="checkbox">
								<label> <input type="checkbox" name="isPublic"
									checked="checked" /> Show campaign in search results
								</label>
							</div>

							<input type="submit"
								value="${createCampaignVO.id == null ? 'Create' : 'Save' }"
								class="btn btn-lg btn-default btn-block"></input>
						</form:form>
					</div>

					<div id="campaign_view"
						class="col-xs-12 content-block center dynamic ${show_content == 'campaign_view'?'':'hidden' }">
						<div class="row">
							<div class="col-xs-12 top-pane">
								<p>
									<c:forEach items="${campaign.members}" var="member">
										<c:if test="${member.name == user.username  }">
											<c:set var="userIsMember" value="true"></c:set>
										</c:if>
									</c:forEach>
								<div class="dropdown pull-right">
									<a data-toggle="dropdown" data-target="#" href="#"
										class="tooltipable fa fa-cogs fa-lg" title="Options"
										data-placement="top righ"> <!--  -->
									</a>
									<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">

										<c:if test="${campaign.gameMaster.name == user.username }">
											<li><c:url var="url"
													value="/campaigns/${campaign.id }/edit"></c:url> <a
												class="" href="${url }"><i
													class="fa fa-pencil-square-o fa-fw">
														<!--  -->
												</i>&#160; Edit Campaign</a></li>
											<li><a class="" href="#" data-toggle="modal"
												data-target="#invite-form-dialog"><i
													class="fa fa-envelope-o fa-fw"> <!--  -->
												</i>&#160; Invite Player</a></li>
										</c:if>
										<c:if
											test="${campaign.gameMaster.name != user.username &amp;&amp; userIsMember != true}">
											<li><a href="#" data-toggle="modal"
												data-target="#request-form-dialog"><i
													class="fa fa-sign-in fa-fw"> <!--  -->
												</i>&#160; Join Campaign</a></li>
										</c:if>
										<c:if
											test="${campaign.gameMaster.name == user.username || userIsMember == true}">
											<li><c:url var="createCharacterUrl"
													value="/characters/create?campaignId=${campaign.id }" /> <a
												href="${createCharacterUrl }"><i
													class="fa fa-user fa-fw"> <!--  -->
												</i>&#160; Create Character</a></li>
										</c:if>
										<c:if test="${campaign.gameMaster.name == user.username }">
											<li><c:url var="createCombatUrl"
													value="/combats/create?campaignId=${campaign.id }" /> <a
												class="" href="${createCombatUrl }"><i
													class="fa fa-bolt fa-fw"> <!--  -->
												</i>&#160; Create Combat</a></li>
										</c:if>

									</ul>
								</div>
								<h3 class="overflown tooltipable">
									${campaign.name }
								</h3>
								</p>
								<c:if test="${campaign.activeCombat !=null }">
									<form class="form-inline">
										<div class="form-group">
											<label class="form-text" for="bio">Current Combat: </label> <label
												class="form-control-static" style="margin-left: 5px">
												<c:url var="url"
													value="/combats/${campaign.activeCombat.id }/show"></c:url>
												<a href="${url }"> ${campaign.activeCombat.name } </a>
											</label>
										</div>
									</form>
								</c:if>


							</div>
							<div class="col-xs-12">
								<div class="tabbable" id="rightBar">
									<div class="tab-content">
										<div id="campaign_description"
											class="scroll_description tab-pane active">
											${campaign.description }</div>
										<div id="campaign_members" class="tab-pane">
											<div class="row" style="height: 185px; margin-bottom: 10px">
												<div class="col-xs-6">
													<div class="cbp-spmenu">
														<div class="">
															<h5 class="inverse">
																<a data-toggle="collapse" data-parent="#rightBar"
																	href="#collapseCharacter" class="short"> My Characters <span
																	class="pull-right glyphicon glyphicon-chevron-up"></span>
																</a>
															</h5>
														</div>
														<div id="collapseCharacter" class="collapse in">
															<div class="scroll_list_150 nice_list">
																<c:forEach var="character" items="${characters }">
																	<c:url var="url"
																		value="/characters/${character.id }/show"></c:url>
																	<a href="${url }" class="short"> <c:if
																				test="${character.character.pictureUrl != '' }">
																				<img alt="${character.character.name} Image"
																					src="${character.character.pictureUrl}"
																					class="img-height-responsive pull-right char-thumbnail"
																					rel="popover" />
																			</c:if>
																			<p class="overflown tooltipable"
																				title="${character.character.name}">
																				${character.character.name}</p>
																	</a>
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
												<div class="col-xs-6">
													<div class="cbp-spmenu">
														<div class="">
															<h5 class="inverse">
																<a data-toggle="collapse" data-parent="#rightBar"
																	href="#collapseOtherCharacter" class="short"> Other Characters <span
																	class="pull-right glyphicon glyphicon-chevron-up"></span>
																</a>
															</h5>
														</div>
														<div id="collapseOtherCharacter" class="collapse in">
															<div class="scroll_list_150 nice_list">
																<c:forEach var="character" items="${otherCharacters }">
																	<c:url var="url"
																		value="/characters/${character.id }/show"></c:url>
																	<a href="${url }" class="short"> <c:if
																				test="${character.character.pictureUrl != '' }">
																				<img alt="${character.character.name} Image"
																					src="${character.character.pictureUrl}"
																					class="img-height-responsive pull-right char-thumbnail"
																					rel="popover" />
																			</c:if>
																			<p class="overflown tooltipable"
																				title="${character.character.name}">
																				${character.character.name}</p>
																	</a>
																</c:forEach>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div id="campaign_messages" class="tab-pane">
											<div class="row" style="height: 185px; margin-bottom: 10px">
												<div class="col-xs-4">
													<div class="cbp-spmenu">
														<div class="">
															<h5 class="inverse">
																<a data-toggle="collapse" data-parent="#rightBar"
																	href="#collapseMembers" class="short"> Members <span
																	class="pull-right glyphicon glyphicon-chevron-up"></span>
																</a>
															</h5>
														</div>
														<div id="collapseMembers" class="collapse in">
															<c:set var="userIsMember" value="false"></c:set>
															<div class="scroll_list_150 ">
																<c:forEach items="${campaign.members}" var="member">
																	<a class="short">${member.name }</a>
																	<c:if test="${member.name == user.username  }">
																		<c:set var="userIsMember" value="true"></c:set>
																	</c:if>
																</c:forEach>
	
															</div>
	
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="">
														<h5>
															<a data-toggle="collapse" data-parent="#rightBar"
																href="#collapsePendingInvites"> Pending Invitations
																<span class="pull-right glyphicon glyphicon-chevron-up"></span>
															</a>
														</h5>
													</div>
													<div id="collapsePendingInvites" class="collapse in">
														<div class="scroll_list_150 nice_list">
															<ul>
																<c:forEach var="invite"
																	items="${campaign.pendingInvitations }">
																	<li data-message-id="${invite.id }"><a
																		style="padding-right: 10px;"
																		class="tooltipable pull-right"
																		title="Resend Invitation" data-placement="top right"
																		data-toggle="modal" data-target="#invite-form-dialog"
																		data-to="${invite.to.name != null ? invite.to.name : invite.toMail }"><i
																			class="fa fa-envelope-o"> <!--  -->
																		</i></a> <a>
																			<p class="overflown tooltipable right_40"
																				title="${invite.to.name != null ? invite.to.name : invite.toMail }">
																				${invite.to.name != null ? invite.to.name : invite.toMail }
																			</p>
																	</a></li>
																</c:forEach>
															</ul>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="">
														<h5>
															<a data-toggle="collapse" data-parent="#rightBar"
																href="#collapsePendingRequests"> Pending Requests <span
																class="pull-right glyphicon glyphicon-chevron-up"></span>
															</a>
														</h5>
													</div>
													<div id="collapsePendingRequests" class="collapse in">
														<div class="scroll_list_150 nice_list">
															<ul>
																<c:forEach var="request"
																	items="${campaign.pendingRequest }">
																	<li data-message-id="${request.id }">
																		<button data-request="${request.id }"
																			style="margin-right: 5px;"
																			class="reject-message tooltipable close glyphicon glyphicon-remove"
																			title="Reject" data-placement="top right">
																			<!--  -->
																		</button>
																		<button data-request="${request.id }"
																			style="margin-right: 5px;"
																			class="accept-request tooltipable close glyphicon glyphicon-ok"
																			title="Accept" data-placement="top right">
																			<!--  -->
																		</button> <a>
																			<p class="overflown tooltipable right_70"
																				title="${request.from.name }">
																				${request.from.name }</p>
																	</a>
																	</li>
																</c:forEach>
															</ul>
															<script type="text/javascript">
																
																
																$(document).ready(function() {
																	$(".reject-message").click(function() {
																		var item = $(this)
																		<c:url var="url" value="/messages/[id]"/>
																		$.ajax("${url}".replace("[id]",item.attr('data-request')),
																			{
																				type : 'DELETE',
																				beforeSend : beforePost
																			})
																			.done(function(){
																				$.notify("Message deleted",{
																          			globalPosition: 'top center',
																          			style : 'bootstrap',
																          			className: 'success'
																          		})
																				item.parent().remove();
																			})
																	});
																	
																	$(".accept-request").click(function() {
																		var item = $(this)
																		<c:url var="url" value="/campaigns/${campaign.id }/accept/[id]"/>
																		$.get("${url}".replace("[id]",item.attr('data-request')))
																		
																			.done(function(){
																				$.notify("Request Accepted",{
																          			globalPosition: 'top center',
																          			style : 'bootstrap',
																          			className: 'success'
																          		})
																          		var name = item.parent().find("p").text();
																				$("<li/>").append($("<a/>").text(name)).appendTo($("#collapseMembers ul"))
																				item.parent().remove();
																			})
																	});
																});
															</script>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div id="campaign_combats" class="tab-pane">
											<div class="row" style="height: 185px; margin-bottom: 10px">
												<div class="col-xs-6">
													<div class="">
														<h5>
															<a data-toggle="collapse" data-parent="#rightBar"
																href="#collapseCombats"> Combats <span
																class="pull-right glyphicon glyphicon-chevron-up"></span>
															</a>
														</h5>
													</div>
													<div id="collapseCombats" class="collapse in">
														<div class="scroll_list_150 nice_list">
															<ul>
																<c:forEach var="combat" items="${campaign.combats }">
																	<c:if
																		test="${(campaign.gameMaster.name == user.username &amp;&amp; combat.state == 'STAGING') || combat.state == 'READY'}">
																		<c:url var="url" value="/combats/${combat.id }/show"></c:url>
																		<li><a href="${url }"> <c:choose>
																					<c:when test="${combat.state == 'STAGING' }">
																						<p class="tooltipable pull-right"
																							style="margin-right: 4px" title="Staging">
																							<i class="fa fa-info-circle"> <!--  -->
																							</i>
																						</p>
																					</c:when>
																				</c:choose>
																				<p class="overflown tooltipable"
																					title="${combat.name}">${combat.name}</p>
																		</a></li>
																	</c:if>
																</c:forEach>
															</ul>
														</div>
													</div>
												</div>
												<div class="col-xs-6">
													<div class="">
														<h5>
															<a data-toggle="collapse" data-parent="#rightBar"
																href="#collapseCombats"> Finished Combats <span
																class="pull-right glyphicon glyphicon-chevron-up"></span>
															</a>
														</h5>
													</div>
													<div id="collapseCombats" class="collapse in">
														<div class="scroll_list_150 nice_list">
															<ul>
																<c:forEach var="combat" items="${campaign.combats }">
																	<c:if
																		test="${combat.state == 'FINISHED'}">
																		<c:url var="url" value="/combats/${combat.id }/show"></c:url>
																		<li><a href="${url }"> 
																				<p class="overflown tooltipable"
																					title="${combat.name}">${combat.name}</p>
																		</a></li>
																	</c:if>
																</c:forEach>
															</ul>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<ul class="nav nav-pills">
										<li class="active"><a href="#campaign_description"
											role="tab" data-toggle="tab">Description</a></li>
										<li><a href="#campaign_members" role="tab"
											data-toggle="tab">Characters</a></li>
										<li><a href="#campaign_combats" role="tab"
											data-toggle="tab">Combats</a></li>
										<c:if
											test="${(campaign.gameMaster.name == pageContext.request.userPrincipal.principal.name) }">
											<li><a href="#campaign_messages" role="tab"
												data-toggle="tab">Messages</a></li>
										</c:if>
									</ul>


								</div>
							</div>
						</div>
					</div>

					<div id="search-result"
						class="dynamic hidden col-xs-12 content-block center">
						<h3>Search Result</h3>
						<script type="text/javascript">
						$(document).ready(function() {
							$("#search_button").click(searchCampaign)
							$("#search_field").keypress(function(e) {
								if(e.which == 13) {
									searchCampaign();
									e.stopPropagation();
							    }
							});
						})
						
						window.onpopstate = function(event) {
							var state = event.state
						  	if(event.state.action == "search") {
						  		$("#search_field").val(event.state.searchValue)
						  		searchCampaign(true);
						  	}
						};
						<c:url var="searchUrl" value="/campaigns/search/"/>
						<c:url var="showCampaignUrl" value="/campaigns/[id]/show"/>
						function searchCampaign(ignoreHistory){
							var searchValue = $("#search_field").val();
							if(!searchValue) return;
							$("#search_button").button('loading')
							$("#search_field").block();
							var url = "${searchUrl}" + searchValue
							if(searchValue.length > 0) {
								if(ignoreHistory != true) {
									history.pushState({action: "search", searchValue : searchValue}, "Search Results", "?search_result")
								}
								$.ajax({
									url: url
								}).success(function(result) {
									$(".search-result").not("[campaign-id=template]").remove();
									for(i= 0; i &lt; result.length; i++) {
										var newResult = $(".search-result[campaign-id=template]").clone().removeClass("hidden");
										newResult.attr("campaign-id", result[i].id);
										newResult.find(".panel-title").append(result[i].name);
										newResult.find(".panel-gm").append(result[i].game_master.name);
										var panelBody = newResult.find(".panel-body")
										var url = "${showCampaignUrl}".replace("[id]",result[i].id)
										$("<a/>").attr("href", url).attr('title','Open Campaign').appendTo(panelBody).addClass("tooltipable").addClass("close").addClass("wide").addClass("glyphicon")
											.addClass("glyphicon-share-alt")
										$("<div/>").addClass("campaign-description-short").append(result[i].description)
											.appendTo(panelBody)
										
										newResult.appendTo($("#search-result"))
									}
									$(".dynamic").addClass("hidden")
									$("#search-result").removeClass("hidden")
								}).always(function(){
									$("#search_button").button('reset')
									$("#search_field").unblock();
								})
							}
						}
						</script>
					</div>

				</div>
			</div>
			<div class="col-sm-2 hidden-xs content-block">
				ADS HERE
				<!-- Ads not shown on the mobile version here. -->
			</div>
		</div>
	</div>

	<!-- Search result template -->
	<div campaign-id="template"
		class="hidden search-result panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title"><!--  --></h3> <small> by <span class="panel-gm"><!--  --></span></small>
		</div>
		<div class="panel-body">
			<!--  -->
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="invite-form-dialog" tabindex="-1"
		role="dialog" aria-labelledby="invite-form-label" aria-hidden="true"
		data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&#215;</button>
					<h4 class="modal-title" id="invite-form-label">Invite Friend
						to campaign</h4>
				</div>
				<div class="modal-body">
					<form:form id="invite-form" class="invite-form">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<p>Send an Invitation to another user and ask him to join your
							campaign</p>
						<div class="form-group">
							<div class="to_email sr-only">
								<label for="email">Username or Email</label>
							</div>
							<input type="text" id="invite_email" name="usernameOrEmail"
								class="form-control" placeholder="Enter username or email" />
						</div>
						<div class="form-group">
							<textarea class="form-control" rows="4" id="invite_message"
								name="message"
								placeholder="Enter the message you want to add to the invitation">
						<!--  -->
					</textarea>
						</div>
					</form:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="send-invite">Send
						Invite</button>
				</div>
			</div>
		</div>
		<script type="text/javascript">
	  	$(document).ready(function(){
	  		
	  		$(".tooltipable").tooltip();
	  		
	  		$(".char-thumbnail").popover({
				content: 'whatever',
			})
	  		
	  		$("#invite-form-dialog").on('show.bs.modal', function (e) {
	  		  var to = $(e.relatedTarget).attr('data-to');
	  		  if(to != null) {
	  			$("#invite_email").attr("readonly", true).val(to);
	  		  } else {
	  		  	$("#invite_email").attr("readonly", false).val("")
	  		  }
	  		  
	  		  $("#invite_message").val("");
	  		  
	  		})
	  		
	  		$("#send-invite").click(function(){
	  			<c:url var="inviteUrl" value="/campaigns/${campaign.id}/invite"/>
	          	$("#invite-form-dialog").block({message :  null});
	          	$.notify('Sending Invitation ...', { 
	          	  style: 'bootstrap',
	          	  className : 'info',
	          	  autoHide: false,
	          	  clickToHide: false,
	          	  globalPosition: 'top center',
	          	  arrowShow: false,
	          	  gap:15
	          	});
	          	var data = $( "#invite-form" ).serialize();
	          	$.post("${inviteUrl}",data)
	          		.done(function(result) {
		          		if(result.valid == true) {
		          			$(".notifyjs-bootstrap-base").trigger('notify-hide');
			          		$("#invite-form-dialog").unblock();
			          		$("#invite-form-dialog").modal("hide")
			          		$.notify("Invitation was sent to user",{
			          			globalPosition: 'top center',
			          			style : 'bootstrap',
			          			className: 'success'
			          		})
			          		
			          		var pendingList = $("#campaign_pending_players ul")
			          		newItem = $("<li/>")
			          		newItem.text(email);
			          		pendingList.append(newItem);
		          		} else {
		          			$(".notifyjs-bootstrap-base").trigger('notify-hide');
			          		$("#invite-form-dialog").unblock();
			          		$("#invite-form").notify(result.message,{
			          			elementPosition: 'bottom center',
			          			style : 'bootstrap',
			          			className: 'error'
			          		})
		          		}
		          	})
		          	.fail(function() {
		          		$(".notifyjs-bootstrap-base").trigger('notify-hide');
		          		$("#invite-form-dialog").unblock();
		          		$("#invite-form").notify("There was an error sending invitation, try again later",{
		          			elementPosition: 'bottom center',
		          			style : 'bootstrap',
		          			className: 'error'
		          		})
		          	});
	  		})
	  	})
	  </script>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="request-form-dialog" tabindex="-1"
		role="dialog" aria-labelledby="request-form-label" aria-hidden="true"
		data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&#215;</button>
					<h4 class="modal-title" id="request-form-label">Request access
						to campaign</h4>
				</div>
				<div class="modal-body">
					<form:form id="request-form" class="request-form">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<p>Send a request to access this campaign</p>
					</form:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="send-request">Send</button>
				</div>
			</div>
		</div>
		<script type="text/javascript">
	  	$(document).ready(function(){
	  		$("#send-request").click(function(){
	  			<c:set var="url" value="/campaigns/${campaign.id}/join/request"/>
	  			$("#request-form-dialog").block({message :  null});
	          	$.notify('Sending Invitation ...', { 
	          	  style: 'bootstrap',
	          	  className : 'info',
	          	  autoHide: false,
	          	  clickToHide: false,
	          	  globalPosition: 'top center',
	          	  arrowShow: false,
	          	  gap:15
	          	});
	  			$.post("${url}", $("#request-form").serialize())
	  				.done(function(result){
	  					$("#request-form-dialog").unblock();
	  					$(".notifyjs-bootstrap-base").trigger('notify-hide');
	  					$("#request-form-dialog").modal("hide")
	  					$.notify("Request Sent, we will let you know when it is accepted",{
		          			globalPosition: 'top center',
		          			style : 'bootstrap',
		          			className: 'success'
		          		})
	  				})
	  				.fail(function(){
	  					$("#request-form-dialog").unblock();
	  					$(".notifyjs-bootstrap-base").trigger('notify-hide');
	  					$("#invite-form").notify("There was an error sending request, try again later",{
		          			elementPosition: 'bottom center',
		          			style : 'bootstrap',
		          			className: 'error'
		          		})
	  				})
	  		});
	  	});
	  </script>
	</div>

	<!-- 
        <div id="central_panel" class="templatemo_multi_content margin_right_10">
        	<div id="form_message" class="templatemo_content form_message margin_bottom_15 ${form_message==null?'hidden':''}">
        		${form_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
        	<div id="error_message" class="templatemo_content error_message margin_bottom_15 ${error_message==null?'hidden':''}">
        		${error_message }
        		<div class="margin_bottom_20">&#160;</div>
        	</div>
	        <div id="campaign_info" class="templatemo_content dynamic ${show_content == null?'':'hidden' }">
	        	
	        	<div  class="content_section">
	           	  <div class="header_02">Campaign info</div>
	                <p><span>View and Manage Campaigns.</span> View and manage the campaigns you are GM of, check new combats of the campaigns you are playing.</p>
	               
	                <p><span>Search Campaigns.</span> Search for public campaigns and join them.</p>
	               
	                <p><span>Create Campaign.</span> Create a new Campaign and invite you friends to play.</p>
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="create_campaign" class="templatemo_content dynamic ${show_content == 'create_campaign'?'':'hidden' }">
	            
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
	        <div id="campaign_search_result" class="templatemo_content dynamic ${show_content == 'campaign_search_result'?'':'hidden' }">
	            <div id="search_result_holder" class="content_section">
	            	<div class="header_02">Search Result for <span id="search_param"></span></div>
	                <div class="cleaner">&#160;</div>
	            </div>
	            <div class="margin_bottom_40">&#160;</div>
	        </div>
	        <div id="campaign_view" campaign_id="${campaign.id }" class="templatemo_multi_content dynamic ${show_content == 'campaign_view'?'':'hidden' }">
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
		            	<p class="border_top">Current Combat: ${campaign.activeCombat.name }</p>
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
	           					<li pc_id="${pc.id}"><a title="Owner ${pc.character.owner.name }">${pc.character.name }</a></li>
	           				</c:forEach>
	           				<c:forEach items="${campaign.nonPlayerCharacters }" var="npc">
	           					<li pc_id="${npc.id}"><a>${npc.character.name }</a></li>
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
	           						<li><a>${invite.toMail }</a></li>
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
	           				<c:forEach items="${campaign.pendingRequest }" var="request">
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
	            <div id="campaign_join" class="templatemo_content dynamic ${show_content == 'campaign_join'?'':'hidden' }"> 
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
	            
	        	
	        </div> -->
	<!-- end of content -->


</body>
	</html>

</jsp:root>