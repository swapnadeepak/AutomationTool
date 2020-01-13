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
<link rel="stylesheet" type="text/css" href="resources/css/progress.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
						</a> <c:if test="${resultstatus<100}">
								<ul class="treeview-menu">

									<c:forEach items="${menuMap.value}" var="role">
										<li><a href=""><i class="fa fa-circle-o"></i>${role.displayName}</a></li>
									</c:forEach>
								</ul>
							</c:if> <c:if test="${resultstatus==100}">
								<ul class="treeview-menu">

									<c:forEach items="${menuMap.value}" var="role">
										<li><a href="${role.name}"><i class="fa fa-circle-o"></i>${role.displayName}</a></li>
									</c:forEach>
								</ul>
							</c:if>
					</c:forEach>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- /.Left side column. contains the logo and sidebar -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header"></section>

			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box" style="height: 380px;">
							<c:if test="${resultstatus<100}">
							
							<c:if test="${nodeMessage.trim().length() == 0}">
								<meta http-equiv="Refresh" content="5;url=process">  
							</c:if>
							
								<font style="font-size: 15px; padding-left: 10px;">Please
									wait, execution is in progress.</font>
							</c:if>
							<c:if test="${resultstatus==100}">
								<font style="font-size: 15px; padding-left: 10px;">Execution
									Status:</font>
							</c:if>
							<!-- /.box-header -->
							<div class="box-body">
								<div class="progress">
									<span class="progress-val">${resultstatus}%</span> <span
										class="progress-barr"><span class="progress-in"
										style="width: ${resultstatus}%"></span></span>
								</div>
								<c:if test="${resultstatus<100}">
									<c:if test='${nodeStatus.equals("No")}'>
										<div
											style="padding-left: 25px; width: 400px; text-align: center;">
											<font color=red size=3px><strong>${nodeMessage}</strong></font>
										</div>
										<div
												style="padding-left: 25px; width: 400px; text-align: center;">
												<form action="generate" method="post">
													<input type="hidden" value="${runplan}" name="fileLocation">
													<div>
														<input type="submit" value="Continue">
													</div>
												</form>
											</div>
										</c:if>
								
									<c:if test='${stopStatus.equals("No")}'>
										<c:if test='${nodeStatus.equals("Yes")}'>
											<div style="padding-left: 150px">
												<button onclick="stopExecution(); showMessage();">Stop
													Execution</button>
											</div>
										</c:if>
									
										<div
											style="padding-left: 25px; width: 400px; text-align: center;">
											<span id="stop" style="display: none"><font color=red
												size=3px>Execution Terminates after current testcase
													execution completes.</font></span>
										</div>
										
									</c:if>
									<c:if test='${stopStatus.equals("Yes")}'>
										<div
											style="padding-left: 25px; width: 400px; text-align: center;">
											<span id="stop"><font color=red size=3px>Execution
													Terminates after current testcase execution completes.</font></span>
										</div>
									</c:if>
								</c:if>
								<c:if test="${resultstatus==100}">

									<div
										style="padding-left: 25px; width: 400px; text-align: center;">
										<font color=red size=3px>${stopMessage}</font>
									</div>
									<form action="display" method="post">
										<input type="hidden" value="${resultfile}" name="fileLocation">
										<div
											style="padding-left: 25px; width: 400px; text-align: center;">
											<font color=green size=3px><strong>Execution
													complete</strong></font>
										</div>
										<div
											style="padding-left: 25px; width: 400px; text-align: center;">
											<input type="submit" value="Continue">
										</div>
									</form>
								</c:if>
							</div>
							<!-- /.box-body -->
						</div>
						<div></div>
						<!-- /.box -->
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