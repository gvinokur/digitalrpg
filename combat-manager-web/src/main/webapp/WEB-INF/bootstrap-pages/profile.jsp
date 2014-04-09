<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:sec="http://www.springframework.org/security/tags" version="2.0">


	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />
	<html>
<head>
<title>Profile</title>
</head>
<body>
	<div class="container main">
		<div class="row">
			<sec:authentication property="principal" var="user"/>
			<div class="col-xs-12 col-sm-10">
				<div class="row">
					<div class="col-xs-12 content-block right">
						<c:url var="url" value="/profile"/>
						<div class="row">
							<div class="col-xs-10">
								<form:form name="f" action="${url}" method="put" role="form">
									
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<div class="form-group row">
										<label class="col-sm-2 control-label">Name</label>
									    <div class="col-sm-10">
									      <p class="form-control-static">${user.name }</p>
									    </div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">Email</label>
									    <div class="col-sm-10">
									      <p class="form-control-static">${user.email }</p>
									    </div>
									</div>
								</form:form>
							</div>
							<div class="col-xs-2">
								<img alt="${user.name }" src="http://www.gravatar.com/avatar/${user.md5Hash }" class="img-responsive img-rounded"/>
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