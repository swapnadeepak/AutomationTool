<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Update Admin</title>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file="header.jsp"%>

		<%@ include file="header_aside.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Update Admin</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<div class="box" style="height: 472px; overflow-y: auto;">
							<div>
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td style="padding-right: 8px; width: 225px;">Select
											Organization :</td>
										<td><select name="organization" id="organization"
											style="width: 200px; margin-left: 20px;" onclick="checkOrgDropdown();"
											onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">
												<option value="0" hidden="true" disabled="disabled" selected="selected">
      														-- Select --
    													</option>
												<c:forEach items="${orgList}" var="org">

													<option value="${org.id}">
      														  ${org.name}
    													</option>

												</c:forEach>
										</select></td>
										<td><div id="orgdropError" style="color: red"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Select
											Admin :</td>
										<td><select name="name" id="adminName"
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">
												<option value="0" hidden="true" disabled="disabled">
      														-- Select -- 
    													</option>
										</select></td>
										<td><div id="admindropError" style="color: red"></div></td>
									</tr>
									<tr>
										<td colspan="3" align="center"><input type="button"
											value="Edit" id="editAdmin" style="width: 10%" onmousedown="checkAdminDropdown();"
											onclick="return validateDropdown();eraseMsg();"></td>
									</tr>
								</table>
								<div
									style="border-style: none; border-width: 1px; border-color: lightgray">
									<c:if test="${message.length() != 0}">
										<h5 style="color: green" id="messagetext">
											<b>${message}</b>
										</h5>
									</c:if>
								</div>
								<div id="updateOrgTableDiv"
									style="border-style: solid; border-width: 1px; border-color: lightgray">
									<form action="processUpdateAdmin" method="post"
										name="updateadminform" id="updateAdminForm"
										onsubmit="return checkGroupselection();return updateAdminValidator();">
										<!-- Dynamically generated table will appear here -->
									</form>
								</div>
							</div>
						</div>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>

	</div>
	<!-- ./wrapper -->
	<script type="text/javascript"
		src="resources/js/updateadminvalidation.js"></script>
	<!-- jQuery 2.2.3 -->
	<!-- ./wrapper -->

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