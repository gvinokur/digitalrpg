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


	<div class="templatemo_content_centered">

		<div class="content_section">
			<div class="header_02">Register</div>
			<c:url value="/register" var="registerUrl" />
			<form:form modelAttribute="user" id="registrationForm"
				action="${registerUrl}" method="post">
				<fieldset>
					<form:errors path="*" cssClass="registration_error" element="div" />

					<div class="margin_bottom_20">&#160;</div>
					<div class="register_label">
						<label for="username">Username</label>
					</div>
					<input type="text" id="username" name="username" autocomplete="off"
						class="field" username="true" />

					<div class="margin_bottom_20">&#160;</div>
					<div class="register_label">
						<label for="email">E-Mail</label>
					</div>
					<input type="text" id="email" name="email" class="field"
						email="true" />

					<div class="margin_bottom_20">&#160;</div>
					<div class="register_label">
						<label for="password">Password</label>
					</div>
					<input type="password" id="password" autocomplete="off"
						name="password" class="field" required="true" />

					<div class="margin_bottom_20">&#160;</div>
					<div class="register_label">
						<label for="confirmPassword">Confirm Password</label>
					</div>
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="field" confirmPassword="true" />

					<div class="margin_bottom_20">&#160;</div>

					<div id="reCaptcha">&#160;</div>
					<div class="margin_bottom_20">&#160;</div>
					<input type="submit" value="Register" class="small_button"></input>

				</fieldset>
			</form:form>

			<div class="cleaner">&#160;</div>
		</div>

		<div class="margin_bottom_40">&#160;</div>
	</div>
	<!-- end of content -->
	<script type="text/javascript"
		src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js">&#160;</script>
	<script>
	$(document).ready(function() {
		Recaptcha.create("6LfGTOgSAAAAABuwtdtoRSYDcRv-yxLse_KAbCw3",
		    "reCaptcha",
		    {
		      theme: "blackglass",
		      callback: Recaptcha.focus_response_field
		    }
		  );
		
		
		jQuery.validator.addMethod("equalsField", function(value, element, params) {
			return this.optional(element) || value == $("#" + params[0]).val();
		}, jQuery.validator.format("Make sure the value is the same as {1}"));
		
		jQuery.validator.addMethod("uniqueName", function(value, element, params) {
			if (this.optional(element)) {
				return true;
			} else {
				<c:url value="/register/check-username/" var="checkUrl" />
				var url = "${checkUrl}" + value;
				var valid = false;
				jQuery.ajax({
					url: url,
					async: false				
				}).done(function(result) {
					valid = result.available;
				})
				return valid;
			}
		}, jQuery.validator.format("Name attribute must be unique"));
		 
		jQuery.validator.addClassRules({
		  confirmPassword: {
		    required: true,
		    equalsField: "password"
		  },
		  email: {
		      required: true,
		      email: true
		    }
		});
		
		$("#registrationForm").validate({
			rules: {
				email: {
			      required: true,
			      email: true
			    },
			    confirmPassword: {
				    required: true,
				    equalsField: [ "password", "Password" ]
				  },
				username: {
					required: true,
					uniqueName: true
				}
			}
		});
	})
</script>

</body>
	</html>

</jsp:root>