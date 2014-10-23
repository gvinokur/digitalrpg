<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<html>
<head>
<title>Register</title>
</head>
<body>

	<div class="container">
		<c:url value="/register" var="registerUrl" />
		<form:form modelAttribute="user" id="registrationForm"
			action="${registerUrl}" method="post" class="form-register">
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<c:url var="url" value="/register/heading"/>
			<c:import url="${url }"/>
			<c:if test="${form_message!=null}">
				<div class="alert alert-success">${form_message }</div>
			</c:if>
			<c:if test="${error_message!=null}">
				<div class="alert alert-danger">${error_message }</div>
			</c:if>
			 <c:if test="${param.error != null}">
			 	<div class="alert alert-danger alert-short"><spring:message code="login.error.invalid"/></div>
	 		</c:if>
	 		<h2 class="form-register-heading"><spring:message code="main.register"/></h2>
			<div class="form-group control-group">
				<spring:message code="login.username" var="usernameMsg"/>
				<spring:message code="register.username.taken" var="usernameErrorMsg"/>
				<label class="sr-only" for="username"></label> 
				<div class="controls">
					<c:url value="/register/check-username/" var="checkUrl" />
					<input 
						type="text" class="form-control" id="username" name="username" autocomplete="off"
						placeholder="${usernameMsg }" required="true"
						data-validation-callback-callback="validateUsername"
						data-validation-callback-message="${usernameErrorMsg }"
						value="${user.username }"/>
				</div>
			</div>
			<div class="form-group control-group">
				<spring:message code="register.email" var="emailMsg"/>
				<label class="sr-only" for="email">${emailMsg }</label>
				<div class="controls">
					<input
					type="email" class="form-control" id="email" name="email" autocomplete="off"
					placeholder="${emailMsg }" required="true" value="${user.email }"/>
				</div> 
			</div>
			<div class="form-group control-group">
				<spring:message code="register.email.confirm" var="confirmEmailMsg"/>
				<spring:message code="register.email.confirm.invalid" var="confirmEmailErrorMsg"/>
				<label class="sr-only" for="confirmEmail">${confirmEmailMsg }</label>
				<div class="controls"> 
					<input
						type="email" class="form-control" id="confirmEmail" name="confirmEmail" autocomplete="off"
						placeholder="${confirmEmailMsg }"
						data-validation-matches-match="email"
						data-validation-matches-message="${confirmEmailErrorMsg }"
						value="${user.email }"/>
				</div>
			</div>
			<div class="form-group control-group">
				<spring:message code="login.password" var="passwordMsg"/>
				<label class="sr-only" for="password">${passwordMsg }</label>
				<div class="controls"> 
					<input
						type="password" class="form-control" id="password" name="password"
						placeholder="${passwordMsg }" autocomplete="off" required="true"/>
				</div>
			</div>
			<div class="form-group control-group">
				<spring:message code="register.password.confirm" var="confirmPasswordMsg"/>
				<spring:message code="register.password.confirm.invalid" var="confirmPasswordErrorMsg"/>
				<label class="sr-only" for="confirmPassword">${confirmPasswordMsg }</label>
				<div class="controls"> 
					<input
						type="password" class="form-control" id="confirmPassword" name="confirmPassword"
						placeholder="${confirmPasswordMsg }" autocomplete="off"
						data-validation-matches-match="password"
						data-validation-matches-message="${confirmPasswordErrorMsg }"/>
				</div>
			</div>
			<div class="center" id="reCaptcha">&#160;</div>
			<button type="submit" class="btn btn-lg btn-primary btn-block"><spring:message code="main.register"/></button>
		</form:form>
	</div>

	<!-- end of content -->
	<script type="text/javascript"
		src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js">&#160;</script>
	<script>
	function validateUsername($element, value, callback) {
		<c:url value="/register/check-username/" var="checkUrl" />
		var url = "${checkUrl}" + value;
		var valid = false;
		jQuery.ajax({
			url: url,
			async: false				
		}).done(function(result) {
			valid = result.available;
		})
		callback({
		      value: value,
		      valid: valid
		    })
	}
	
	$(document).ready(function() {
		Recaptcha.create("6LfGTOgSAAAAABuwtdtoRSYDcRv-yxLse_KAbCw3",
		    "reCaptcha",
		    {
		      theme: "blackglass",
		      callback: Recaptcha.focus_response_field
		    }
		  );
		
		$("input").jqBootstrapValidation(); 
		
	})
	
	
	</script>

</body>
	</html>

</jsp:root>