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
	<div class="container main">
		<div class="row">
			<div class="xs-hidden col-sm-3 content-block">
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
											<option value="${characterType }" selected="${createCharacterVO.characterType == characterType ? 'selected' : '' }">${characterType }</option>
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
								<h3>
									<c:if test="${character.character.owner.name == user.username || character.campaign.gameMaster.name == user.username }">
										<c:url var="url" value="/characters/${character.id }/edit"></c:url>
										<a href="${url }" class="tooltipable pull-right fa fa-pencil-square-o fa-lg" title="Edit Character" data-placement="top righ"><!--  --></a>
									</c:if>
									${character.character.name }
								</h3>
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
										<label class="form-text" for="bio">External Bio</label> 
										<p class="form-control-static">
											<a href="${character.character.webBioUrl }" target="_new">${character.character.webBioUrl }</a>
										</p>
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

</body>
</html>
</jsp:root>