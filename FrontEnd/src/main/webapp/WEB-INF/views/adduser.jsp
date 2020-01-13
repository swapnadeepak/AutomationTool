<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type"
	content="application/x-www-form-urlencoded; charset=UTF-8" />
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>${appTitle}| Add User</title>
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
<style type="text/css">
input[type="text"], input[type="email"], select, textarea {
	width: 60%;
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
				<h1>Create User</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">

						<form action="saveUser" method="post" name="userForm"
							onsubmit="return userValidator();">
							<div class="box"
								style="height: 472px; overflow-y: auto; overflow-x: hidden">
								<!-- /.box-header -->
								<!-- 	<div class="box-body" style="height:480px;"> -->
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td colspan="3"><c:if test="${message.length() != 0}">
												<h5 id="messagetext" style="color: green">
													<b>${message}</b>
												</h5>
											</c:if></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">First Name
											<span style="color: red">*</span> :
										</td>
										<td><input type="text" name="firstName" id="fname"
											onkeyup="checkFirstName()"
											style="width: 200px; margin-left: 20px;"
											autofocus="autofocus" onkeydown="eraseMsg()"></td>
										<td><div id="fnError" style="color: red; font-size: 13px"></div></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Last Name <span
											style="color: red">*</span> :
										</td>
										<td><input type="text" name="lastName" id="lname"
											onkeyup="checkLastType()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><div id="lnError" style="color: red; font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">User Name :</td>
										<td><input type="text" name="userName"
											onkeyup="checkUserName()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><div id="unError" style="color: red; font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">E-mail Id <span
											style="color: red">*</span> :
										</td>
										<td><input type="email" name="emailId" id="emailId"
											oninput="checkEmail()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><div id="mailError"
												style="color: red; font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Password <span
											style="color: red">*</span> :
										</td>
										<td><input type="password" name="password"
											onkeyup="checkPwd()" style="width: 200px; margin-left: 20px;"></td>
										<td><div id="pwdError"
												style="color: red; font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Status :
										</td>
										<td><input type="radio" name="status" value="active"
											checked onclick="checkStatus()"
											style="width: 50px; margin-left: 0px;"> Active<br>
											<input type="radio" name="status" value="inactive"
											onclick="checkStatus()"
											style="width: 50px; margin-left: 0px;"> In Active<br></td>
										<td><div id="statusError"
												style="color: red; font-size: 13px"></div></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">User Type <span
											style="color: red">*</span> :
										</td>
										<td><%-- <c:forEach items="${typeList}" var="userType">
												<c:if test="${userType.id eq 3}">
													<input type="text"
														value="<c:out value = "${userType.userTypeName}"/>"
														style="width: 200px; margin-left: 20px;"
														onkeyup="checkType()" readonly="readonly">
													<input type="hidden" name="userType"
														value="<c:out value = "${userType.id}"/>">
												</c:if>
											</c:forEach> --%>
											 <input type="text" name="userTypeDisplay"
											value="<c:out value = "${userType.userTypeName}"/>"
											style="width: 200px;margin-left: 20px;background-color: #e5dee4;"
											readonly="readonly">
											<input type="hidden" name="userType"
														value="<c:out value = "${userType.id}"/>">
											</td>
										<!-- <td><div id="typeError"
												style="color: red; font-size: 13px"></div></td> -->
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Organization
											<span style="color: red">*</span> :
										</td>

										<td>
											<%-- <select id="organization" name="organization"
											onclick="checkOrg()"
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">
												<option value="0" selected="selected" hidden="true"
													disabled="disabled">--Select--</option>
												<c:forEach items="${orgList}" var="org">
													<option value="${org.name}">${org.name}</option>
												</c:forEach>
										</select> --%>
										
										 <input type="text" name="organization"
											value="${organization}"
											style="width: 200px; margin-left: 20px;background-color: #e5dee4;"
											readonly="readonly">
										</td>
										<!-- <td><div id="orgError"
												style="color: red; font-size: 13px"></div></td> -->
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Project <span
											style="color: red">*</span> :
										</td>
										<td><select id="project" name="project"
											multiple="multiple" onchange="checkProject()"
											style="width: 200px; margin-left: 20px;">
												<option value="0" selected="selected" hidden="true"
													disabled="disabled">--Select--</option>
												<c:forEach items="${projectList}" var="project">
													<option value="${project}">${project}</option>
												</c:forEach>
										</select></td>
										<td><div id="projectError"
												style="color: red; font-size: 13px"></div></td>
									</tr>
									<tr>
										<td>Available Groups :</td>
										<td align="left" style="padding-left: 150px;">Assigned
											Groups<span style="color: red; size: 5px">*</span> :
										</td>
									</tr>

								</table>

								<div class="row">
									<div class="col-xs-3">
										<select name="availableGroup" class="multiselect form-control"
											size="8" multiple="multiple" data-right="#multiselect_to_1"
											data-right-all="#right_All_1"
											data-right-selected="#right_Selected_1"
											data-left-all="#left_All_1"
											data-left-selected="#left_Selected_1" style="height: 145px">
											<c:forEach items="${availablegroupList}" var="availableGroup">
												<c:if
													test="${availableGroup.id != 1 && availableGroup.id != 2}">
													<option value="${availableGroup.name}">${availableGroup.name}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="col-xs-1">
										<button type="button" id="right_All_1" class="btn btn-block"
											onclick="checkAssignGroup()">
											<i class="glyphicon glyphicon-forward"></i>
										</button>
										<button type="button" id="right_Selected_1"
											onclick="checkAssignGroup()" class="btn btn-block">
											<i class="glyphicon glyphicon-chevron-right"></i>
										</button>
										<button type="button" id="left_Selected_1"
											onclick="checkAssignGroup()" class="btn btn-block">
											<i class="glyphicon glyphicon-chevron-left"></i>
										</button>
										<button type="button" id="left_All_1" class="btn btn-block"
											onclick="checkAssignGroup()">
											<i class="glyphicon glyphicon-backward"></i>
										</button>
									</div>
									<div class="col-xs-3">
										<select name="assignedGroup" id="multiselect_to_1"
											class="form-control" size="8" multiple="multiple"
											style="height: 145px">
											<c:forEach items="${availablegroupList}" var="availableGroup">
												<c:if test="${fn:toLowerCase(availableGroup.name) == 'user group'}">
													<option value="${availableGroup.name}" selected="selected">${availableGroup.name}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div id="asGrpError"
										style="color: red; font-size: 13px; margin-left: 100px; padding-left: 655px;"></div>
								</div>
								<!-- <h6 style="color: blue">*User should assign atleast to one
									group.</h6> -->
								<div align="center">
									<input type="submit" value="Create User"
										onclick="selectGrp(); return userValidator();"
										style="width: 150px; margin-top: 21px;">
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
	<script type="text/javascript" src="resources/js/uservalidation.js"></script>

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