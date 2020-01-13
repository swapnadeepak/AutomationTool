<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Upload Master Plan</title>
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
<script type="text/javascript">
	$(document).ready(function() {
		var msg = "${message}";
		if (msg.length != 0) {
			document.getElementById("box").style.display = "none";
		}

	});
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
			<section class="content-header" style="padding-bottom: 2px;">
				<form action=upload method="post" enctype="multipart/form-data">
					<div class="box" id="box" style="height: 472px; overflow-y: auto;">
						<table>
							<tr>
								<td colspan="3"><font style="font-size: 17px;">
										<b>Please select a Master Plan to Upload</b></font></td>
							</tr>
							<tr><td>
							<br>
							</td></tr>
							<tr bordercolor="black">
								<th style="padding-right: 8px; width: 100px;">Select a
									file:</th>
								<td><input type="file" name="file" accept=".xls,.xlsx"
									required onchange="checkfile(this);"
									style="width: 200px; margin-left: 20px;" /></td>
								<td><input type="submit" value="Upload"
									style="width: 150px; margin-left: 20px;"></td>
								<td><a href="#" data-toggle="popover"
									data-placement="right"
									data-content='Sample Masterplan file is available. click 
								<a href="masterPage">here</a> to download.'><span style="padding-left: 8px"><img
										src="resources/images/icon.png" height="20px" width="20px" title="Click here for Sample Master Plan"></span></a></td>
								<td><span id="result" style="display: none"></span></td>
							</tr>
							<tr>
								<td><c:if test="${message.length() != 0}">
										<h5 style="color: green">
											<b>${message}</b>
										</h5>
									</c:if></td>
							</tr>
						</table>
					</div>
				</form>
				<div id="mydiv" style="width: 450px;">
					<font color="green" size="3px"><b>${uploadResult}</b></font>
				</div>
			</section>

		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<script type="text/javascript" src="resources/js/dropdown.js"></script>
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
	<script type="text/javascript">
		function checkfile(sender) {
			var validExts = new Array(".xlsx", ".xls");
			var fileExt = sender.value;
			fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
			if (validExts.indexOf(fileExt) < 0) {
				document.getElementById("result").innerHTML = "<font color=red size=2px>"
						+ " Invalid file selected. Only .xlsx format allowed.</font>";
				document.getElementById("result").style.display = "inline";
				return false;
			} else
				return true;
		}
	</script>
	<script type="text/javascript">
		setTimeout(function() {
			$('#mydiv').fadeOut('fast');
	<%session.setAttribute("uploadResult", "");%>
		}, 30000);
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('[data-toggle="popover"]').popover({
				html : true
			});
		});
	</script>
</body>
</html>