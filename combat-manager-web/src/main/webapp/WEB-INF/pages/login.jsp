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
    
    	
        <div class="templatemo_content_centered">
            
            <div class="content_section">
           	  <div class="header_02">Login</div>
           	  <c:url value="/login" var="loginUrl"/>
              <form:form name="f" action="${loginUrl}" method="post">
		        <fieldset>
		            <c:if test="${param.error != null}">
		                <div class="alert alert-error">
		                    Invalid username and password.
		                </div>
		            </c:if>
		            <c:if test="${param.logout != null}">
		                <div class="alert alert-success">
		                    You have been logged out.
		                </div>
		            </c:if>
		            
		            <div class="margin_bottom_20">&#160;</div>
		            <div class="login_label">
		            	<label for="username" >Username</label>
		            </div>
		            <input type="text" id="username" name="username" class="field"/>
		            
		            <div class="margin_bottom_20">&#160;</div>
		            <div class="login_label">
		            	<label for="password">Password</label>
		            </div>
		            <input type="password" id="password" name="password" class="field"/>
		            <input type="submit" value="Log In" class="small_button"></input>
		            
		            <c:url value="/reset-password" var="resetPasswordUrl"/>
		            <c:url value="/register" var="registerUrl"/>
		            <div class="reset_link"><a href="${resetPasswordUrl}">Reset Password</a></div>
		            <div class="register_link"><a href="${registerUrl}">Register</a></div>
		        </fieldset>
		    	</form:form>
               
                <div class="cleaner">&#160;</div>              
            </div>
            
        	<div class="margin_bottom_40">&#160;</div>
        </div> <!-- end of content -->
        
        
    	
</body>
</html>

</jsp:root>