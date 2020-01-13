<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>${appTitle} | Add Project</title>
<!-- Tell the browser to be responsive to screen width -->
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
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<style type="text/css">
input, select, textarea {
	width: 40%;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file = "header.jsp" %>
	<%@ include file = "header_aside.jsp"  %>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<h1>Create Project</h1>
			</section>


			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
					<form action="save_project" method="post" id="projectform" onsubmit="return projectValidator();">
						<div class="box" style="height: 472px; overflow-y: auto;">
								<!-- /.box-header -->
								<!-- 	<div class="box-body" style="height:480px;"> -->
								<table id="example1" class="table table-bordered table-striped">
								<tr>
										<td colspan="3"><c:if test="${message.length() != 0}">
												<h5 id="messagetext" style="color: green"><b>${message}</b></h5>
											</c:if></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Project
											Name <span style="color: red">*</span> :</td>
										<td><input type="text" name="name" id="projectName" 
											style="width: 200px; margin-left: 20px;"  oninput="checkProName()" onkeydown="eraseMsg()"></td>
										<td><div id="pnError" style="color: red;font-size: 13px"></div></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Project
											Type <span style="color: red">*</span> :</td>
										<td><select name=type id="proType"
											style="width: 200px; margin-left: 20px;"  onkeydown="checkProType()" onmousedown="if(this.options.length>5){this.size=5;}" onchange='this.size=0;' onblur="this.size=0;">
												<c:forEach items="${projectTypeList}" var="projectType">
													<option value="${projectType}">
      														  ${projectType}
    													</option>
												</c:forEach>
										</select></td>
										<td><div id="typeError" style="color: red;font-size: 13px"></div></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Project Status <span style="color: red">*</span> :</td>
										<td style="padding-left: 20px"><input type="radio" name="status" value="active"
											checked style="width: 30px;"
											style="width: 200px; margin-left:20px;"  onclick="checkProType()"> Active<br>
											<input type="radio" name="status" value="inactive"
											style="width: 30px;" style="width: 200px; margin-left:20px;"  onclick="checkProType()">
											In Active<br></td>
											<td><div id="statusError" style="color: red;font-size: 13px"></div></td>
									</tr>

								</table>
								<div align="center">
							<input type="submit" value="Create Project" onclick="return projectValidator();" style="width: 150px;">
						</div>
								<!-- </div> -->
								<!-- /.box-body -->
							</div>
							
							<!-- /.box -->
					</form>
				
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
		</div>
		<!-- /.content-wrapper -->
		<%@ include file = "footer.jsp" %>

	</div>
	<!-- ./wrapper -->
	<!-- jQuery 2.2.3 -->
	<script type="text/javascript" src="resources/js/projectvalidation.js"></script>

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
</body>
</html>