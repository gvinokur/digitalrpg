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
<title>Login</title>
</head>
<body>

	
	<div class="container main">
		<c:url value="/login" var="loginUrl" />
		<form:form name="f" action="${loginUrl}" method="post" role="form"
			class="form-signin">
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
			
			<h2 class="form-signin-heading"><spring:message code="login.title"/></h2>
			<div class="form-group">
				<spring:message code="login.username" var="usernameMsg"/>
				<label class="sr-only" for="username">${usernameMsg }</label> 
				<input
					type="text" class="form-control" id="username" name="username"
					placeholder="${usernameMsg }"/>
			</div>
			<div class="form-group">
			<spring:message code="login.password" var="passwordMsg"/>
				<label class="sr-only" for="password">${passwordMsg }</label> 
				<input
					type="password" class="form-control" id="password" name="password"
					placeholder="${passwordMsg }"/>
			</div>
			<div class="checkbox">
				<label> <input type="checkbox"
					name="remember-me"/> <spring:message code="login.rememberme"/>
				</label>
			</div>
			<button type="submit" class="btn btn-lg btn-primary btn-block"><spring:message code="main.login"/></button>

			<c:url value="/reset-password" var="resetPasswordUrl" />
			<c:url value="/register" var="registerUrl" />
			<div class="row">
				<div class="col-md-6"><a href="${resetPasswordUrl}" role="button" class="btn btn-default btn-block"><spring:message code="login.resetpassword"/></a></div>
				<div class="col-md-6"><a href="${registerUrl}" role="button" class="btn btn-default btn-block"><spring:message code="main.register"/></a></div>
			</div>
		</form:form>
	</div>

</body>
</html>

</jsp:root>