<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<div class="form-group">
		<label class="form-text" for="pathfinderData">Character Attributes</label> 
		<div class="row">
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_ac" name="pathfinder.ac" placeholder="AC" value="${createCharacterVO.pathfinder.ac }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_hp" name="pathfinder.hp" placeholder="HP" value="${createCharacterVO.pathfinder.hp }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_ref" name="pathfinder.ref" placeholder="REF" value="${createCharacterVO.pathfinder.ref }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_fort" name="pathfinder.fort" placeholder="FORT" value="${createCharacterVO.pathfinder.fort }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_will" name="pathfinder.will" placeholder="WILL" value="${createCharacterVO.pathfinder.will }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_cmb" name="pathfinder.cmb" placeholder="CMB" value="${createCharacterVO.pathfinder.cmb }"></input>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-2">
				<input type="number" min="0" class="form-control" id="pathfinder_cmd" name="pathfinder.cmd" placeholder="CMD" value="${createCharacterVO.pathfinder.cmd }"></input>
			</div>
		</div>
		
	</div>
</jsp:root>