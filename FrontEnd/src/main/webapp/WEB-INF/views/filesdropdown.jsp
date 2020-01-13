<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Execute</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
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
<script type="text/javascript">
window.onload = function() {
	var msg ="${disable}";
	if(msg == 'true'){
		document.getElementById("submit").disabled = true;
	}
}
function eraseMsg(){
	document.getElementById('messagetext').innerHTML = "";
}

</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@ include file = "header.jsp" %>
			<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
			<c:forEach items="${menu}" var="menuMap">
			<li class="treeview">
			<a href="#"> <i class="fa fa-dashboard"></i>
				 <span><b>${menuMap.key}</b></span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu">
					<c:forEach items="${menuMap.value}" var="role">
					<li><a href="${role.name}"><i class="fa fa-circle-o"></i>${role.displayName}</a></li>
							</c:forEach>
				</ul>
				</c:forEach>
				<!-- <li class="treeview"><a href="getuploadPage"> <i
							class="fa fa-edit"></i> <span>Upload Master Plan</span>
					</a> -->
		</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- /.Left side column. contains the logo and sidebar -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<div class="col-xs-12">
				<form action="generate" method="post" name="form1"
					onsubmit="return selectrunplan()">
					<div class="box" style="height: 130px;">
					<table style="margin-top: 10px">
						<!-- <tr>
							<td colspan="2"><font style="font-size: 20px;">
									Please select a run plan to execute</font></td>
						</tr> -->
						<tr>
							<td style="padding-right: 3px;padding-left:5px; width: 120px; display: inline-block;"><b>Select Run Plan :</b></td>
							<td><select name="fileLocation" style="width: 320px;margin-left:20px;" onclick="eraseMsg()" onmousedown="if(this.options.length>5){this.size=6;}"
											onchange='this.size=0;' onblur="this.size=0;">
									<c:if test="${dropDownMessage != null}">
									<option value="" selected="selected" hidden="true">${dropDownMessage}</option>
									</c:if>
									<c:if test="${dropDownMessage == null}">
									<option value="" selected="selected", hidden="true">-- Select --</option>
									</c:if>
									<c:forEach var="type" items="${filesList}">
										<option value="${type.value}">${type.key}</option>
									</c:forEach>
							</select></td>
							<td style="display: inline-block;"><input type="submit" id="submit" value="View Run Plan" style="width: 135px;margin-left:20px;"></td>
							<td><span id="result" style="display: none"></span></td>
						</tr>
						<tr>
						<td colspan="3">
						<c:if test="${message != null}">
							<h5 id="messagetext" style="color: green">${message}</h5>
						</c:if>
						</td>
						</tr>
					</table>
					</div>
				</form>
				</div>
			</section>

			<!-- Main content -->
			<section class="content"></section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file = "footer.jsp" %>

	</div>
	<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
	<script type="text/javascript" src="resources/js/dropdown.js"></script>

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