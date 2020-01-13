<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type"
	content="application/x-www-form-urlencoded; charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Home</title>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#projectId').change(function(event) {
			var name = document.getElementById('projectId').value;
			$.get("selectproject?projectName=" + name, function(data, status) {
				$('#masterplanTable').html(data)
				$('#nextBtnId').show();
			});
		});
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
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- /.Left side column. contains the logo and sidebar -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1 style="color: green">
					<b> Welcome,</b><span style="color: black;"><font> <%=session.getAttribute("userName")%></font></span>
				</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<div class="box" style="height: 472px; overflow-y: auto;">
							<!-- /.box-header -->
							<!-- 	<div class="box-body" style="height:480px;"> -->
							<form action="selectProject" method="post">
								<label> Please select the project to proceed..</label>
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td  style="width: 150px">Select
											Project :</td>
										<td style="padding-right: 8px;width: 150px"><select name="projectName" id="projectId">
												<option value="" selected="selected" disabled="disabled"
													hidden="true">Select Project</option>
												<c:forEach items="${projectList}" var="project">
													<option value="${project.name}">${project.name}</option>
												</c:forEach>
										</select></td>
										<td style="padding-left: 20px"><input type="submit" value="Proceed" style="width: 100px;">
										</td>
									</tr>
									
								</table>
											<div id="messagearea">
												<h5 style="color: green">
													<b>${message}</b>
												</h5>
											</div>
											<div>
												<h5 style="color: red">
													<b>${errorMessage}</b>
												</h5>
											</div>
										
							</form>
							<%-- 	<span style="color: green"> <b>Please upload the
										MasterPlan to proceed.</b></span> <a href="getuploadPage">Upload
									Master Plan</a>
							
							<div id="masterplanTable">
								<!--  Dynamic table -->
							</div> --%>
						</div>
					</div>
					<!-- /.box -->
					<!-- /.box -->
					<!-- <div align="center" id="nextBtnId" style="display: none">
							<form action="RunPlan" method="post">
								<input type="submit" value="Next" style="width: 150px;">
							</form>
						</div> -->
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