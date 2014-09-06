<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:sec="http://www.springframework.org/security/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<html>
<head>
<title>Combats</title>
<c:url var="url" value="/css/jquery.nestable.css"/>
<link href="${url}" rel="stylesheet"/>
<c:url var="url" value="/js/jquery.nestable.js"/>
<script src="${url}"><!-- --></script>
</head>
<body>
	<sec:authentication property="principal" var="user"/>
	<div class="container main">
		<div class="row">
			<div class="hidden-xs col-sm-3 content-block">
				<h4>My Combats</h4>
				
				<div class="scroll_list_150 nice_list">
					<ul class="combats">
						
					</ul>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-7">
				<c:if test="${form_message!=null}">
					<div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
					${form_message }
					</div>
				</c:if>
				<c:if test="${warning_message!=null}">
					<div class="alert alert-warning">
					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
					${warning_message }
					</div>
				</c:if>
				<c:if test="${error_message!=null}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
						${error_message }
					</div>
				</c:if>
				<c:if test="${createCombatVO != null }">
					<div id="combat_create"
							class="content-block dynamic ${show_content == 'combat_create'? '' : 'hidden' }">
						<h3>Create Combat</h3>
						<c:url value="/combats/create" var="createCombatUrl"></c:url>
	            		<form:form action="${createCombatUrl }" modelAttribute="createCombatVO" id="create-combat" method="POST">
	            			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	            			<input type="hidden" name="campaignId" value="${campaign.id }"/>
	            			<input type="hidden" name="id" value="${createCombatVO.id }"/>
	            			<spring:bind path="name">
							<div class="form-group ${status.error? 'has-error' :'' }">
								<label class="control-label" for="name">Name
									<form:errors  path="name" />
								</label> 
								<input
									type="text" class="form-control" id="name" name="name" required="true" value="${createCombatVO.name }"/>
							</div>
							</spring:bind>
							
							<spring:bind path="description">
							<div class="form-group ${status.error? 'has-error' :'' }">
							
								<label class="form-text" for="description">Description</label> 
								<textarea class="form-control" rows="4" id="description" name="description">${createCombatVO.description }</textarea>
								<form:errors element="div" path="description"/>
							</div>
							</spring:bind>
							
							<div class="form-group">
								<input type="hidden" name="players" id="selectedPlayers" value="${createCombatVO.players }"/>
								<div class="row">
									<div class="col-xs-6">
										<label class="form-text">Campaign Characters</label>
										<div class="dd" style="width: 100%" id="character-origin-list">
							            	<ol class="dd-list">
							            		<c:choose>
							            			<c:when test="${empty remainingCharacters}">
							            				<ol class="dd-empty">
							            					<!--  -->
							            				</ol>			
							            			</c:when>
							            			<c:otherwise>
								            			<c:forEach var="character" items="${remainingCharacters}">
									            			<li class="dd-item dd3-item" data-id="${character.id}">
									            				<div class="dd-handle dd3-handle">Drag</div>
									                    		<div class="dd3-content">
									                    			<span class="overflown tooltipable">${character.character.name }
									                    			</span>
									                    			<c:if test="${character.character.characterType == 'NPC' }">
									                    				<!-- GM Char, can be hidden -->
									                    				<span class="pull-right">
									                    					Hidden <input type="checkbox" name="extraInfo[${character.id }].hidden"/>
									                    				</span>
									                    			</c:if>
									                    		</div>
									                		</li>
									            		</c:forEach>
							            			</c:otherwise>
							            		</c:choose>
							            		
							                </ol>
							        	</div>
									</div>
									<div class="col-xs-6">
										<label class="form-text">Selected Characters</label>
										<div class="dd" style="width: 100%" id="character-list">
							            	<c:choose>
							            			<c:when test="${empty currentCharacters}">
							            				<ol class="dd-empty">
							            					<!--  -->
							            				</ol>			
							            			</c:when>
							            			<c:otherwise>
								            			<c:forEach var="character" items="${currentCharacters}">
									            			<li class="dd-item dd3-item" data-id="${character.character.id}">
									            				<div class="dd-handle dd3-handle">Drag</div>
									                    		<div class="dd3-content">
									                    			<span class="overflown tooltipable">${character.character.character.name }
									                    			</span>
									                    			<c:if test="${character.character.character.owner.name == user.name }">
									                    				<!-- GM Char, can be hidden -->
									                    				<span class="pull-right">
									                    					Hidden <input type="checkbox" name="extraInfo[${character.character.id }].hidden"/>
									                    				</span>
									                    			</c:if>
									                    		</div>
									                		</li>
									            		</c:forEach>
							            			</c:otherwise>
							            		</c:choose>
							        	</div>
									</div>
								</div>
							</div>
							
							<script type="text/javascript">
							$(document).ready(function(){
								$('.dd').nestable({
							        group: 1,
							        maxDepth: 1
								});
								
								$('.dd').on('change', function() {
									var players = "";
								    $('.dd#character-list .dd-item').each(function() {
								    	if(players != "") players = players + ",";
								    	players = players + $(this).attr("data-id");
								    })
								    $('#selectedPlayers').val(players);
								});
							})
							</script>
							
							<input type="submit" value="${empty createCombatVO.id ? 'Create' : 'Save'}" class="btn btn-lg btn-default btn-block"></input>
	            		</form:form>
					</div>
				</c:if>
				<div id="combat_view"
							class="col-xs-12 content-block dynamic ${show_content == 'combat_view'? '' : 'hidden' }">
						<!--  -->
						<div class="row">
							<div class="col-xs-8 left-pane ">
								<p>
									<c:if test="${combat.campaign.gameMaster.name == user.username }">
										<div class="dropdown pull-right">
											<a data-toggle="dropdown" data-target="#" href="#" class="tooltipable fa fa-cogs fa-lg" title="Options" data-placement="top righ"><!--  --></a>
											<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
												<c:if test="${combat.state == 'STAGING' }">
													<li>
														<c:url var="url" value="/combats/${combat.id }/edit"></c:url>
														<a class="" href="${url }"><i class="fa fa-pencil-square-o fa-fw"><!--  --></i>&#160; Edit Combat</a>
													</li>
													<li>
														<c:url var="url" value="/combats/${combat.id }/accept"></c:url>
														<a class="" href="${url }"><i class="fa fa-check fa-fw"><!--  --></i>&#160; Mark as Ready</a>
													</li>
												</c:if>
												<c:if test="${combat.state == 'READY' }">
													<li>
														<c:url var="url" value="/combats/${combat.id }/start"></c:url>
														<a class="" href="${url }"><i class="fa fa-play fa-fw"><!--  --></i>&#160; Start Combat</a>
													</li>
												</c:if>
												<c:if test="${combat.state == 'STAGING' || combat.state == 'READY'}">
													<li>
	    												<a href="#" data-toggle="modal" data-target="#confirm-delete"><i class="fa fa-trash-o fa-fw"><!--  --></i>&#160; Delete Combat</a>
	    											</li>
												</c:if>
											</ul>
										</div>
									</c:if>
									<h3 class="overflown tooltipable">${combat.name }</h3>
								</p>
								<div class="form-group">
									<label class="form-text" for="bio">Campaign</label> 
									<p class="form-control-static">
										<c:url var="url" value="/campaigns/${combat.campaign.id }/show"></c:url>
										<a href="${url }">
										${combat.campaign.name }
										</a>
									</p>
								</div>
								<div id="combat_description" class="scroll_description">
									${combat.description }
								</div>
							</div>
							<div class="col-xs-4 right-pane">
								<div class="" id="rightBar">
									<div class="">
										<div class="">
											<h5>
												<a data-toggle="collapse" data-parent="#rightBar"
													href="#collapseMembers"> Combatants <span class="pull-right glyphicon glyphicon-chevron-up"></span>
												</a>
											</h5>
										</div>
										<div id="collapseMembers" class="collapse in">
											<div class="scroll_list_150 nice_list">
												<ul>
													<c:forEach items="${combat.combatCharacters}" var="combatant">
														<c:if test="${combat.campaign.gameMaster.name == user.username || !combatant.hidden}">
															<li><a>${combatant.character.character.name }</a></li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
				</div>
				
			</div>
			<div class="col-sm-2 hidden-xs content-block">
				ADS HERE
					<!-- Ads not shown on the mobile version here. -->
			</div>
			
		</div>
		
		
	</div>
	
	<div class="modal fade" id="confirm-delete">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title">Delete Combat?</h4>
	      </div>
	      <div class="modal-body">
	        <p>This will delete this combat for ever, are you sure?</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <c:url var="url" value="/combats/${combat.id }/delete"></c:url>
	        <a href="${url }" class="btn btn-primary">Delete</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</body>
</html>
</jsp:root>