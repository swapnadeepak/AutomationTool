<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type"
	content="application/x-www-form-urlencoded; charset=UTF-8" />
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>${appTitle} | Add Group</title>
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
<link
	href="https://cdn.rawgit.com/davidstutz/bootstrap-multiselect/master/dist/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
		<%@ include file="header.jsp"%>
		<%@ include file="header_aside.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Create Group</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<!-- For scroll bar -->
						<form action="saveGroup" method="post" name="grpForm" id="grpForm"
							onsubmit="return groupValidator();">
							<div class="box" style="height: 472px; overflow-y: auto;overflow-x: hidden;">
								<!-- /.box-header -->
								<!-- 	<div class="box-body" style="height:480px;"> -->
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td colspan="3"><c:if test="${message.length() != 0}">
												<h5 id="messagetext" style="color: green"><b>${message}</b></h5>
											</c:if></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 200px;">Group Name
											<span style="color: red">*</span> :</td>
										<td><input type="text" name="name" id="groupName"
											autofocus="autofocus" oninput="checkGrpName()" onkeydown="eraseMsg()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><div id="gnError" style="color: red;font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Group Type <span style="color: red">*</span> :</td>
										<td><input type="radio" name="type"  value="link type"
											onclick="checkLink()" style="width: 50px; margin-left: 0px;" checked="checked">
											Link Type<br> 
											
											<input type="radio" name="type"
											value="featured type" onclick="checkLink()"
											style="width: 50px; margin-left: 0px;"> Featured Type<br></td>
										<td><div id="linkError" style="color: red;font-size: 13px"></div></td>
									</tr>

									<tr>
										<td>Available Roles :</td>
										<td align="left" style="padding-left: 180px;">Assigned
											Roles<span style="color: red">*</span>:</td>
									</tr>

								</table>
								<div class="row">
									<div class="col-xs-3">
										<select name="availableRoles" class="multiselect form-control"
											size="8" multiple="multiple" data-right="#multiselect_to_1"
											data-right-all="#right_All_1"
											data-right-selected="#right_Selected_1"
											data-left-all="#left_All_1"
											data-left-selected="#left_Selected_1" style="height: 145px">
											<c:forEach items="${roleList}" var="role">
												<option value="${role.name}">${role.displayName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-xs-1">
										<button type="button" id="right_All_1" class="btn btn-block" onclick="checkRole()">
											<i class="glyphicon glyphicon-forward"></i>
										</button>
										<button type="button" id="right_Selected_1" onclick="checkRole()"
											class="btn btn-block">
											<i class="glyphicon glyphicon-chevron-right"></i>
										</button>
										<button type="button" id="left_Selected_1" onclick="checkRole()"
											class="btn btn-block">
											<i class="glyphicon glyphicon-chevron-left"></i>
										</button>
										<button type="button" id="left_All_1" class="btn btn-block" onclick="checkRole()">
											<i class="glyphicon glyphicon-backward"></i>
										</button>
									</div>
									<div class="col-xs-3">
										<select name="assignedRoles" id="multiselect_to_1"
											class="form-control" size="8" multiple="multiple"
											style="height: 145px"></select>
									</div>
									<div id="asRoleError"
										style="color: red; margin-left: 100px; font-size: 13px;padding-left: 680px;"></div>
								</div>
								<!-- 	<h6 style="color: blue">*Group should contain atleast one Role.</h6> -->
								<div align="center">
									<input type="submit" value="Create Group"
										onclick="selectRole(); return groupValidator();"
										style="width: 150px; margin-top: 50px">
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
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<script type="text/javascript" src="resources/js/groupvalidation.js"></script>

	<script type="text/javascript"
		src="//cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.min.js"></script>

	<script
		src="https://cdn.rawgit.com/davidstutz/bootstrap-multiselect/master/dist/js/bootstrap-multiselect.js"
		type="text/javascript"></script>

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

	<script src="resources/js/multiselect.js"></script>

	
</body>
</html>