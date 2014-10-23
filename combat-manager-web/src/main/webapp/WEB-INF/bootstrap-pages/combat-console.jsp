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
<c:url var="url" value="/css/jquery.gridster.css"/>
<link href="${url}" rel="stylesheet"/>
<c:url var="url" value="/css/jquery.combatConsole.css"/>
<link href="${url}" rel="stylesheet"/>
<c:url var="url" value="/css/bootstrap-editable.css" />
<link href="${url}" rel="stylesheet" type="text/css" />
<c:url var="url" value="/css/tabdrop.css" />
<link href="${url}" rel="stylesheet" type="text/css" />

<c:url var="url" value="/js/min/jquery.gridster.min.js"/>
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/jquery.combatConsole.js"/>
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/model/combat.js"/>
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/model/character.js"/>
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/min/bootstrap-editable.min.js" />
<script src='${url}'><!-- --></script>
<c:url var="url" value="/js/bootstrap-tabdrop.js" />
<script src='${url}'><!-- --></script>
<c:url var="url" value="/js/jquery.iconToggle.js"/>
<script src="${url}"><!-- --></script>

</head>
<body>
	<sec:authentication property="principal" var="user"/>
	<div class="container main">
		<!-- Character Block for extra small devices -->
		<div class="row visible-xs-block">
			<div class="col-xs-12"><!--  -->
			</div>
		</div>
		<c:if test="${combat.campaign.gameMaster.name != user.name }">
			<div class="row">
				<div class="col-xs-12">
					<div class="content-block" id="player-data" style="margin-bottom:15px">
						<!--  -->
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			
			<div class="col-xs-12 col-sm-8">
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
				<div id="combat-console" class="content-block">
					<div style='width:100%;text-align:center;'><i class='fa fa-refresh fa-spin fa-5x'><!--  --></i></div>
				</div>
			</div>
			<!-- Character Block for small and larger devices -->
			<div class="hidden-xs col-sm-4">
				<div id="character-data-lg" class="content-block panel">
				</div>
			</div>
		</div>
		<c:url var="baseUrl" value="/"/>
		<c:choose>
			<c:when test="${user.name == combat.campaign.gameMaster.name }">
				<c:set var="mode" value="gm"/>
			</c:when>
			<c:otherwise>
				<c:set var="mode" value="player"/>
			</c:otherwise>
		</c:choose>
		<script type="text/javascript">
			
			$(document).ready(function() {
				var options = new Object();
				options.baseUrl = "${baseUrl}";
				options.systemType = "${combat.campaign.system}";
				options.combatId = ${combat.id};
				options.mode = "${mode}";
				options.csrfHeaderName = "${_csrf.headerName}" 
				options.csrfToken = "${_csrf.token}"
				options.largePanel = "#character-data-lg";
				options.playerPanel = "#player-data";
				//TODO: Improve this
				options.combatSystemData = {
					turns : ${combat.turns}, 
					roundsPerTurn: ${combat.roundsPerTurn}
				}
				options.dialogs = {
					'confirm' : '#confirm-action',
					'addCharacter' : '#add-character',
					'addLog' : '#add-log',
					'viewLogs' : '#view-logs'
				}
				$("#combat-console").combatConsole(options);
			})
		</script>
		
	</div>
	
	<div class="modal fade" id="confirm-action">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title"><!--  --></h4>
	      </div>
	      <div class="modal-body">
	        <p><!--  --></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <a href="#" class="confirm btn btn-primary">Confirm</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="add-character">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title">Add Character</h4>
	      </div>
	      <div class="modal-body">
	        <p><select class="remaining-characters form-control"><!--  --></select></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <a href="#" class="confirm btn btn-primary">Confirm</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="add-log">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title">Add Log Entry</h4>
	      </div>
	      <div class="modal-body">
	        <p><textarea class="log-entry form-control"><!--  --></textarea></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <a href="#" class="confirm btn btn-primary">Confirm</a>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="view-logs">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&#215;</button>
	        <h4 class="modal-title">Combat Log</h4>
	      </div>
	      <div class="modal-body vertical-scroll-max-350">
	        <!--  -->
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</body>
</html>
</jsp:root>