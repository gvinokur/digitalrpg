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
<title>Characters</title>
</head>
<body>
	<sec:authentication property="principal" var="user"/>
	<content tag="local_submenu">
	<h4>My Characters</h4>
	<c:forEach items="${characters}" var="mycharacter">
								<c:url value="/characters/${mycharacter.id }/show" var="url"></c:url>
								<a class="campaign_list_item"
									id="character_${mycharacter.id }" href="${url }">${mycharacter.character.name }</a>
							
						</c:forEach>
	</content>
	
	<div class="container main">
		<div class="row">
			<div class="hidden-xs col-sm-3 content-block">
				<h4>My Characters</h4>
				
				<div class="scroll_list_150 nice_list">
					<ul class="campaigns">
						<c:forEach items="${characters}" var="mycharacter">
								<c:url value="/characters/${mycharacter.id }/show" var="url"></c:url>
								<li><a class="campaign_list_item"
									id="character_${mycharacter.id }" href="${url }">${mycharacter.character.name }</a></li>
							
						</c:forEach>
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
				<div class="row">
				 	<c:if test="${createCharacterVO != null }">
						<div id="character_create"
								class="col-xs-12 content-block center dynamic ${show_content == 'create_character'? '' : 'hidden' }">
							<h3>Create Character</h3>
							<c:url value="/characters" var="createCharacterUrl"></c:url>
		            		<form:form action="${createCharacterUrl }" modelAttribute="createCharacterVO" id="create-character" method="POST">
		            			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            			<input type="hidden" name="campaignId" value="${campaign.id }"/>
		            			<input type="hidden" name="combatId" value="${combatId }"/>
		            			<input type="hidden" name="id" value="${createCharacterVO.id }"/>
		            			<spring:bind path="name">
								<div class="form-group ${status.error? 'has-error' :'' }">
									<label class="control-label" for="name">Name
										<form:errors  path="name" />
									</label> 
									<input
										type="text" class="form-control" id="name" name="name" required="true" value="${createCharacterVO.name }"/>
								</div>
								</spring:bind>
								
								<div class="form-group">
									<label class="control-label" for="characterType">Type</label> 
									<select name="characterType" class="form-control">
										<c:forEach items="${characterTypes }" var="characterType">
											<c:choose>
												<c:when test="${createCharacterVO.characterType == characterType}">
													<option value="${characterType }" selected="selected"> ${characterType }</option>
												</c:when>
												<c:otherwise>
													<option value="${characterType }"> ${characterType }</option>
												</c:otherwise>
											</c:choose>
											
										</c:forEach>
									</select>
								</div>
								
								<spring:bind path="pictureUrl">
								<div class="form-group ${status.error? 'has-error' :'' }">
								
									<label class="form-text" for="pictureUrl">Picture URL</label> 
									<input type="url" class="form-control" rows="4" id="pictureUrl" name="pictureUrl" value="${createCharacterVO.pictureUrl }"></input>
									<form:errors element="div" path="pictureUrl"/>
								</div>
								</spring:bind>
								
								<spring:bind path="bio">
								<div class="form-group ${status.error? 'has-error' :'' }">
								
									<label class="form-text" for="bio">Bio</label> 
									<textarea class="form-control" rows="4" id="bio" name="bio">${createCharacterVO.bio }</textarea>
									<form:errors element="div" path="bio"/>
								</div>
								</spring:bind>
								
								<div class="checkbox">
									<label> <input type="checkbox"
										name="isBioPublic" checked="checked"/> Make Bio public
									</label>
								</div>							
								
								<spring:bind path="webBio">
								<div class="form-group ${status.error? 'has-error' :'' }">
								
									<label class="form-text" for="webBio">External Bio URL</label> 
									<input type="url" class="form-control" rows="4" id="webBio" name="webBio" value="${createCharacterVO.webBio }"></input>
									<form:errors element="div" path="webBio"/>
								</div>
								</spring:bind>
								
								<div class="checkbox">
									<label> <input type="checkbox"
										name="isWebBioPublic" checked="checked"/> Make Web Bio URL public
									</label>
								</div>	
								
								<spring:bind path="notes">
								<div class="form-group ${status.error? 'has-error' :'' }">
								
									<label class="form-text" for="notes">Additional Notes</label> 
									<textarea class="form-control" rows="4" id="notes" name="notes">${createCharacterVO.notes }</textarea>
									<form:errors element="div" path="notes"/>
								</div>
								</spring:bind>
								
								<spring:bind path="additionalResources">
								<div class="form-group ${status.error? 'has-error' :'' }">
								
									<label class="form-text" for="additionalResources">Additional External Resources</label> 
									<input type="url" class="form-control" id="additionalResources_tags" name="additionalResources_tags" placeholder="Wiki's, PRD's, SRD's or other's URLs"></input>
									<input type="hidden" id="additionalResources" name="additionalResources" ></input>
									<form:errors element="div" path="additionalResources"/>
								</div>
								</spring:bind>
								
								<script type="text/javascript">
								$(document).ready(function() {
									$("#additionalResources_tags").tagsManager({
										prefilled: "${createCharacterVO.additionalResourcesString }",
										output: "#additionalResources",
										validator: validateURL
									});
									
									$('.dropdown-toggle').dropdown()
								})
								
								function validateURL(textval) {
								  var urlregex = new RegExp(
								        "^(http:\/\/|https:\/\/|ftp:\/\/|www.){1}([0-9A-Za-z]+\.)");
								  return urlregex.test(textval);
								}
								</script>
								
								<c:choose>
									<c:when test="${campaign.system == 'Pathfinder' }">
										<jsp:include page="systems/pathfinder/characters/input.jsp"/>
									</c:when>
								</c:choose>
								
								<input type="submit" value="${empty createCharacterVO.id ? 'Create' : 'Edit'}" class="btn btn-lg btn-default btn-block"></input>
		            		</form:form>
						</div>
					</c:if>
					<div id="view_character"
								class="col-xs-12 content-block center dynamic ${show_content == 'view_character'? '' : 'hidden' }">
						<div class="row">
							<div class="col-xs-8 left-pane">
								<p>
									<c:if test="${character.character.owner.name == user.username || character.campaign.gameMaster.name == user.username }">
										
										<div class="dropdown pull-right">
											<a data-toggle="dropdown" data-target="#" href="#" class="tooltipable fa fa-cogs fa-lg" title="Options" data-placement="top righ"><!--  --></a>
											<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
												<li>
													<c:url var="url" value="/characters/${character.id }/edit"></c:url>
													<a class="" href="${url }"><i class="fa fa-pencil-square-o fa-fw"><!--  --></i>&#160; Edit Character</a>
												</li>
    											<li>
    												<a href="#" data-toggle="modal" data-target="#send-character"><i class="fa fa-hand-o-right fa-fw"><!--  --></i>&#160; Send Character</a>
    											</li>
    											<c:if test="${character.campaign.gameMaster.name == user.username }">
    											<li>
    												<a href="#" data-toggle="modal" data-target="#confirm-take"><i class="fa fa-hand-o-left fa-fw"><!--  --></i>&#160; Take Character</a>
    											</li>
    											</c:if>
    											<li>
    												<c:url var="url" value="/characters/${character.id }/clone"></c:url>
    												<a href="${url }"><i class="fa fa-users fa-fw"><!--  --></i>&#160; Clone Character</a>
    											</li>
    											<li>
    												<a href="#" data-toggle="modal" data-target="#confirm-delete"><i class="fa fa-trash-o fa-fw"><!--  --></i>&#160; Delete Character</a>
    											</li>
    										</ul>
										</div>
										
									</c:if>
									<h3 class="overflown tooltipable">
										${character.character.name }
									</h3>
								</p>
								<div class="form-group">
									<label class="form-text" for="bio">Campaign</label> 
									<p class="form-control-static">
										<c:url var="url" value="/campaigns/${character.campaign.id }/show"></c:url>
										<a href="${url }">
										${character.campaign.name }
										</a>
									</p>
								</div>
								<c:if test="${character.character.publicBio == true || character.character.owner.name == user.username || character.campaign.gameMaster.name == user.username }">
									<div class="form-group">
										<label class="form-text" for="bio">Bio</label> 
										<p class="form-control-static">
											${character.character.bio }
										</p>
									</div>
								</c:if>
								<c:if test="${character.character.publicWebBio == true || character.character.owner.name == user.username || character.campaign.gameMaster.name == user.username }">
									<div class="form-group">
										<label class="form-text" for="bio">
											<a href="${character.character.webBioUrl }" target="_new">External Bio</a>
										</label>
									</div>
								</c:if>
								<div class="form-group">
									<label class="form-text" for="notes">Notes</label> 
									<p class="form-control-static">
										${character.character.notes }
									</p>
								</div>
								<div class="form-group">
									<label class="form-text" for="notes">Other Resources</label>
									<c:forEach var="link" items="${character.character.links }">
										<p class="form-control-static">
											<a href="${link }" target="_new">${link }</a>
										</p>
									</c:forEach>
								</div>
							</div>
							<div class="col-xs-4 right-pane">
								<c:if test="${character.character.pictureUrl != '' }">
									<img alt="${character.character.name} Image" src="${character.character.pictureUrl}" 
										class="img-responsive img-rounded" />
								</c:if>
								<h4>Character Attributes</h4>
								<jsp:include page="systems/pathfinder/characters/data.jsp"/>
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
	        <h4 class="modal-title">Delete Character?</h4>
	      </div>
	      <div class="modal-body">
	        <p>This will delete this character for ever, are you sure?</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <c:url var="url" value="/characters/${character.id }/delete"></c:url>
	        <a href="${url }" class="btn btn-primary">Delete</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="confirm-take">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title">Take Character?</h4>
	      </div>
	      <div class="modal-body">
	        <p>This will assign the character to you, removing current owner's ability to use it.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <c:url var="url" value="/characters/${character.id }/take"></c:url>
	        <a href="${url }" class="btn btn-primary">Take</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="send-character">
	  <div class="modal-dialog">
	  	<c:url var="url" value="/characters/${character.id }/send"></c:url>
	  	<form:form action="${url }" modelAttribute="sendCharacter"  method="POST">
	  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
		        <h4 class="modal-title">Send Character?</h4>
		      </div>
		      <div class="modal-body">
		        <p>Assign this character to a different member of the campaign.</p>
		        <select name="userToUsername" class="form-control">
		        	<c:if test="${user.username != character.campaign.gameMaster.name }">
		        		<option value="${character.campaign.gameMaster.name }">${character.campaign.gameMaster.name }</option>
		        	</c:if>
		        	<c:forEach items="${character.campaign.members }" var="member">
		        		<c:if test="${user.username != member.name &amp;&amp; member.name != character.character.owner.name }">
		        			<option value="${member.name }">${member.name }</option>
		        		</c:if>
		        	</c:forEach>
		        </select>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <input type="submit" class="btn btn-primary" value="Send"/>
		      </div>
		    </div><!-- /.modal-content -->
	    </form:form>
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
</body>
</html>
</jsp:root>