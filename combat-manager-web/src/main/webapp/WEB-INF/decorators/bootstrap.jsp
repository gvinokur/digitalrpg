<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns:page="http://www.opensymphony.com/sitemesh/page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:sec="http://www.springframework.org/security/tags"
	xmlns:tags="urn:jsptagdir:/WEB-INF/tags" version="2.0">

	<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	<jsp:output omit-xml-declaration="true" />
	<jsp:output doctype-root-element="HTML"
		doctype-system="about:legacy-compat" />

	<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Digital RPG - <decorator:title /></title>
<c:url var="faviconUrl" value="/img/favicon.ico" />
<link rel="icon" type="image/x-icon" href="${faviconUrl}" />

<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet" />

<!-- Bootstrap -->
<c:url var="url" value="/css/bootstrap.min.css" />
<link href="${url}" rel="stylesheet">
	<!--  --></link>
<c:url var="url" value="/css/bootstrap-theme.min.css" />
<link href="${url}" rel="stylesheet">
	<!--  --></link>
<c:url var="url" value="/css/extra.css" />
<link href="${url}" rel="stylesheet">
	<!--  --></link>
<c:url var="url" value="/css/tagmanager.css" />
<link href="${url}" rel="stylesheet">
	<!--  --></link>

<link
	href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css"
	rel="stylesheet" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<c:url var="url" value="/js/jquery.min.js" />
<script src="${url}"><!-- --></script>
<!-- 	<c:url var="url" value="/js/css3-mediaqueries.js" /> -->
<!-- 	<script src="${url}"> -->
<!-- -->
<!-- 	</script>  -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<c:url var="url" value="/js/bootstrap.min.js" />
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/jqBootstrapValidation.js" />
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/notify-combined.min.js" />
<script src="${url}"><!-- --></script>
<c:url var="url" value="/js/notify-bootstrap.js" />
<script src="${url}"><!-- --></script>
<c:url var="jQueryBlockUIUrl" value="/js/jquery.blockUI.js" />
<script src='${jQueryBlockUIUrl}'><!-- --></script>
<c:url var="url" value="/js/tagmanager.js" />
<script src="${url}"><!-- --></script>


<script
	src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"><!-- --></script>

&lt;!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and
media queries --&gt; &lt;!-- WARNING: Respond.js doesn't work if you
view the page via file:// --&gt; &lt;!--[if lt IE 9]&gt;
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script
	src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
&lt;![endif]--&gt;

<decorator:head />

	</head>

	<body>

		<!-- Make user available for all pages if exists -->
		<sec:authentication property="principal" var="user" />
		<sec:authorize access="isAuthenticated()" var="isAuthenticated">
			<c:url var="logoutUrl" value="/logout" />
			<form:form id="logout" action="${logoutUrl}" method="post">
				<!--  -->
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form:form>
		</sec:authorize>

		<nav class="cbp-spmenu hideable-menu cbp-spmenu-vertical cbp-spmenu-left"
			id="cbp-spmenu-s1">
			<h3>Menu</h3>
			<c:url var="homeUrl" value="/home" />
			<c:url var="lobbyUrl" value="/lobby" />
			<c:url var="campaignsUrl" value="/campaigns" />
			<c:url var="pcsUrl" value="/characters" />
			<c:url var="combatsUrl" value="/combats" />
			<c:if test="${pageContext.request.userPrincipal !=null }">
				<a href="${homeUrl}" class="${fn:startsWith(pageContext.request.servletPath,homeUrl)?'active':' '}"><spring:message code="main.home"/></a>
				<a href="${lobbyUrl }" class="${fn:startsWith(pageContext.request.servletPath,lobbyUrl)?'active':' '}"><spring:message code="main.lobby"/> <span id="unread-messages"
					class="badge hidden"></span></a>
				<a href="${campaignsUrl }" class="${fn:startsWith(pageContext.request.servletPath,campaignsUrl)?'active':' '}"><spring:message code="main.campaigns"/></a>
				<a href="${pcsUrl }" class="${fn:startsWith(pageContext.request.servletPath,pcsUrl)?'active':' '}"><spring:message code="main.characters"/></a>
				<a href="${combatsUrl }" class="${fn:startsWith(pageContext.request.servletPath,combatsUrl)?'active':' '}"><spring:message code="main.combats"/></a>
			</c:if>
			<decorator:getProperty property="page.local_submenu"/>
			<c:if test="${isAuthenticated }">
				<a class="logout-link"><spring:message code="main.logout"/></a>
			</c:if>
			<c:if test="${!isAuthenticated }">
				<c:url var="loginBarUrl" value="/login" />
				<c:url var="registerBarUrl" value="/register" />
				<li>
					<p class="navbar-text">
						<strong><spring:message code="main.welcome"/></strong>
					</p>
				</li>
				<li><a href="${loginBarUrl }"><spring:message code="main.login"/></a></li>
				<li><a href="${registerBarUrl }"><spring:message code="main.register"/></a></li>
			</c:if>
		</nav>

		<div class="main-container">
			<div class="visible-xs">
				<div class="navbar navbar-inverse small-banner">
					<div class="navbar-inner">
						<button id="left-menu-button" type="button"
							class="pull-left navbar-toggle" data-toggle="collapse"
							data-target="#menu">
							<span class="icon-bar">
								<!--  -->
							</span> <span class="icon-bar">
								<!--  -->
							</span> <span class="icon-bar">
								<!--  -->
							</span>
						</button>
					</div>
				</div>
			</div>
			<div class="hidden-xs container banner-wrapper">
				<div class="row">
					<div class="col-md-12 banner">
						<!--  -->
					</div>
				</div>
			</div>

			<!-- Static navbar -->
			<div
				class="navbar navbar-default navbar-fixed menu-wrapper hidden-xs"
				role="navigation">
				<div class="container menu">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target=".navbar-collapse">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
					</div>
					<div class="navbar-collapse collapse">
						<ul class="nav navbar-nav nav-pills">
							<c:url var="homeUrl" value="/home" />
							<c:url var="lobbyUrl" value="/lobby" />
							<c:url var="campaignsUrl" value="/campaigns" />
							<c:url var="pcsUrl" value="/characters" />
							<c:url var="combatsUrl" value="/combats" />
							<c:if test="${pageContext.request.userPrincipal !=null }">
								<li
									class="${fn:startsWith(pageContext.request.servletPath,homeUrl)?'active':' '}"><a
									href="${homeUrl}"><spring:message code="main.home"/></a></li>
								<li
									class="${fn:startsWith(pageContext.request.servletPath,lobbyUrl)?'active':' '}"
									id="lobby"><a href="${lobbyUrl }"><spring:message code="main.lobby"/> <span
										id="unread-messages" class="badge hidden"></span></a></li>
								<li
									class="${fn:startsWith(pageContext.request.servletPath,campaignsUrl)?'active':' '}"><a
									href="${campaignsUrl }"><spring:message code="main.campaigns"/></a></li>
								<li
									class="${fn:startsWith(pageContext.request.servletPath,pcsUrl)?'active':''}"><a
									href="${pcsUrl }"><spring:message code="main.characters"/></a></li>
								<li
									class="${fn:startsWith(pageContext.request.servletPath,combatsUrl)?'active':''}"><a
									href="${combatsUrl }"><spring:message code="main.combats"/></a></li>
							</c:if>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<c:if test="${isAuthenticated }">
								<li class="hidden-sm">
									<p class="navbar-text">
										<spring:message code="main.welcome.username" arguments="${user.username }"/> 
									</p>
								</li>
								<li><c:set var="url" value="/profile" /><a href="${url }"><spring:message code="main.profile"/></a>
								</li>
								<li><a class="logout-link"><spring:message code="main.logout"/></a></li>
							</c:if>
							<c:if test="${!isAuthenticated }">
								<c:url var="loginBarUrl" value="/login" />
								<c:url var="registerBarUrl" value="/register" />
								<li>
									<p class="navbar-text">
										<strong><spring:message code="main.welcome"/></strong>
									</p>
								</li>
								<li><a href="${loginBarUrl }"><spring:message code="main.login"/></a></li>
								<li><a href="${registerBarUrl }"><spring:message code="main.register"/></a></li>
							</c:if>

						</ul>
					</div>
					<!--/.nav-collapse -->
				</div>
			</div>


			<decorator:body />


		</div>





		<script type="text/javascript">
		$(document).ready(function(){
			$(".logout-link").click(function(){
				$("#logout").submit();
			})
			
			$("#left-menu-button").click(function() {
				$(this).toggleClass('active');
				$('body').toggleClass('cbp-spmenu-push-toright');
				$('#cbp-spmenu-s1').toggleClass('cbp-spmenu-open');
			})
			
			pollMessages();
		});
		
		function pollMessages() {
			<c:set var="url" value="/messages"/>
			$.ajax("${url}", {ifModified : true } ).done(function(result, status){
				if(status == 'success') {
					if(result.length > 0) {
						$("#unread-messages").removeClass("hidden").text(result.length)
					} else {
						$("#unread-messages").addClass("hidden").text("0")
					}
					if(typeof processMessages != 'undefined') {
						processMessages(result)
					}
				}
			}).always(function(){
				setTimeout(pollMessages,10000);
			})
		}
		
		
		function beforePost(xhr, settings) {
	        if (!csrfSafeMethod(settings.type) &amp;&amp; sameOrigin(settings.url)) {
	            // Send the token to same-origin, relative URLs only.
	            // Send the token only if the method warrants CSRF protection
	            // Using the CSRFToken value acquired earlier
	            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	        }
	    }
	    
	    function csrfSafeMethod(method) {
		    // these HTTP methods do not require CSRF protection
		    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
		}
		function sameOrigin(url) {
		    // test that a given url is a same-origin URL
		    // url could be relative or scheme relative or absolute
		    var host = document.location.host; // host + port
		    var protocol = document.location.protocol;
		    var sr_origin = '//' + host;
		    var origin = protocol + sr_origin;
		    // Allow absolute or scheme relative URLs to same origin
		    return (url == origin || url.slice(0, origin.length + 1) == origin + '/') ||
		        (url == sr_origin || url.slice(0, sr_origin.length + 1) == sr_origin + '/') ||
		        // or any other URL that isn't scheme relative or absolute i.e relative.
		        !(/^(\/\/|http:|https:).*/.test(url));
		}
		</script>

	</body>

</jsp:root>