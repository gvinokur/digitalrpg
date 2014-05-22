<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">AC</label>
			<p class="col-xs-5 form-control-static">${character.ac }</p>
		</div> 
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">HP</label>
			<p class="col-xs-5 form-control-static">${character.hp }</p>
		</div>
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">REF</label>
			<p class="col-xs-5 form-control-static">${character.ref }</p>
		</div>
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">FORT</label>
			<p class="col-xs-5 form-control-static">${character.fort }</p>
		</div>
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">WILL</label>
			<p class="col-xs-5 form-control-static">${character.will }</p>
		</div>
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">CMB</label>
			<p class="col-xs-5 form-control-static">${character.cmb }</p>
		</div>
		<div class="form-group">
			<label class="col-xs-5 control-label" for="pathfinderData">CMD</label>
			<p class="col-xs-5 form-control-static">${character.cmd }</p>
		</div>

		
	</div>
</jsp:root>