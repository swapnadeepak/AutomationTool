<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title>${appTitle}|Results</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">

td{
display:inline-block;
}

</style>
<script type="text/javascript">
	window.onload = function() {
		var msg = "${disable}";
		if (msg == 'true') {
			document.getElementById("view").disabled = true;
			document.getElementById("download").disabled = true;
			document.getElementById("log").disabled = true;
		}
	}
	function eraseMsg() {
		document.getElementById('messagetext').innerHTML = "";
	}
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file="header.jsp"%>

		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<c:forEach items="${menu}" var="menuMap">
						<li class="treeview"><a href="#"> <i
								class="fa fa-dashboard"></i> <span><b>${menuMap.key}</b></span>
								<span class="pull-right-container"> <i
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
				<form method="post" name="form1"
					onsubmit="return fileSelectValidate();">
					<div class="box" style="height: 200px">
						<br>
						<table>
							<tr>
								<td style="width: 200px;"><label
									style="padding-left: 20px; padding-top: 10px">Select a
										result folder <span style="color: red">*</span> :
								</label></td>
								<td><select id="folderId"
									style="width: 200px; padding-left: 5px; padding-right: 5px" onchange="checkFolder()">
										<option value="0" selected="selected" hidden="true"
											disabled="disabled"> -- Select --</option>
										<c:forEach items="${resultMap}" var="result">
											<option value="${result.value}">${result.key}</option>
										</c:forEach>
								</select></td>
								<td><span id="folderError"
										style="color: red; font-size: 13px;padding-left: 350px;"></span></td>
							</tr>
							<tr>
								<td style="width: 200px;"><label style="padding-left: 20px; padding-top: 10px">Select
										a result file <span style="color: red">*</span> :
								</label></td>
								<td><select id="fileId" name="fileLocation"
									required="required" style="width: 360px;padding-left: 5px;"  onchange="checkFile()">
										<option value="0" selected="selected" hidden="true"
											disabled="disabled"> -- Select --</option>
								</select></td>
								<td><span id="fileError"
									style="color: red; font-size: 13px;padding-left: 190px;"></span></td>
							</tr>
							<tr>
								<td colspan="3"><input type="submit" id="view"
									value="View Result"
									onclick="javascript: form.action='display';return fileSelectValidate();"
									style="margin-left: 594px; margin-top: 16px;" /></td>
								<td><input type="submit" id="download"
									value="Download Result File"
									onclick="javascript: form.action='save';"
									style="width: 153px; margin-left: 10px;margin-top: 16px;" /></td>
								<td><input type="submit" id="log" value="Download Logs"
									onclick="javascript: form.action='logs';"
									style="width: 114px; margin-left: 10px;margin-top: 16px;" /></td>
							</tr>
							<tr>
								<td colspan="3">
									<c:if test="${message.length() != 0}">
									<h5 id="messagetext" style="color: green">${message}</h5> </c:if>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</section>

			<!-- Main content -->
			<section class="content"></section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script type="text/javascript" src="resources/js/dropdown.js"></script>
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