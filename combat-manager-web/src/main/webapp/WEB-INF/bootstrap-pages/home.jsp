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
<title>Home</title>
</head>
<body>
	<div class="container main">
		<div class="row">
			<div class="col-xs-5 col-md-3 content-block">
				<c:url var="lobbyUrl" value="/lobby" />
				<c:url var="campaignsUrl" value="/campaigns/public" />
				<h4>
					<a href="${lobbyUrl }">My Lobby</a>
				</h4>
				<h4>
					<a href="${campaignUrl }">Featured Campaigns</a>
				</h4>
				<ul>
					<c:forEach items="${campaigns }" var="campaign">
						<li><a href="${campaignsUrl + '/' + campaign.id }">${campaign.name }</a></li>
					</c:forEach>
				</ul>

				<div class="divider">&#160;</div>

				<h4>Search Campaign</h4>
				<div class="row search">
					<div class="col-xs-12">
						<input class="form-control" type="text" id="search_field"
							name="campaign" placeholder="Enter keywords" />
					</div>
				</div>
				<div class="row search">
					<div class="col-xs-5 col-xs-offset-7">
						<input type="button" value="Search" id="search_button"
							class="btn btn-default btn-block" />
					</div>
				</div>


			</div>
			<div class="col-md-6"><!--  --></div>
			<div class="col-md-3"><!--  --></div>
		</div>
	</div>
</body>
	</html>

</jsp:root>