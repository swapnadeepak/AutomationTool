<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>${appTitle} | Welcome </title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<link rel="shortcut icon" href="resources/images/index.png"
	type="image/png" />
<!-- Tell the browser to be responsive to screen width -->
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="resources/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="resources/css/_all-skins.min.css">
<!-- ./wrapper -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="resources/js/bootstrap.min.js"></script>

<script src="resources/js/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="resources/js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="resources/js/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="resources/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="resources/js/demo.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini" onload="display();">
	<div class="wrapper">
		<%-- <header class="main-header"> <!-- Logo --> <a href="#"
			class="logo" style="padding-top: 0px"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"><img src="resources/images/index.png"
				alt="logo" /></span> <!-- logo for regular state and mobile devices --> <span
			class="logo-lg"><img
				src="resources/images/estuate-inc-logo.png" alt="logo" /></span>
		</a> <!-- Header Navbar: style can be found in header.less --> <nav
			class="navbar navbar-static-top"> <!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span> <span
			class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
		</a>
		<div class="navbar-custom-menu" style="margin-right: 10px;">
			<ul class="nav navbar-nav navbar-center">
				<li style="color: white; font-family: Arial Bold Italic">${appHeader}</li>
			</ul>
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"
					style="padding-bottom: 31px;"> <img
						src="resources/images/avatar5.png" class="user-image"
						alt="User Image"> <!--<span class="hidden-xs">User</span>-->
				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="resources/images/avatar5.png" class="img-circle"
							alt="User Image">
							<p>
								<b>${userName}</b>
							</p></li>
						<!-- Menu Body -->
						<!-- Menu Footer-->
						<li class="user-footer">
							<div style="text-align: center;">
								<a href="logout" class="btn btn-default btn-flat">Log ooout</a>
							</div>
						</li>
					</ul></li>
			</ul>
		</div>
		</nav> </header> --%>
		<%@ include file="header.jsp"%>
		<%@ include file="header_aside.jsp"%>
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<h1 style="color: green">
				<b> Welcome,</b><span style="color: black;"><font> <%=session.getAttribute("userName")%></font></span>
			</h1>
			</section>
		</div>
		<%-- <div>
			<c:if test="${message.length() != 0}">
				<h5 id="messagetext" style="color: green">
					<b>${message}</b>
				</h5>
			</c:if>

		</div> --%>

		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>