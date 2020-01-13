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
				<form action="generate" method="post" name="form1"
					onsubmit="return selectrunplan()">
					<table>
						<tr>
							<th style="display: inline-block;padding-right: 20px">Select other Run Plan:</th>
							<td><select name="fileLocation" onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">
									<option value="" selected="selected" , hidden="true">-- Select --</option>
									<c:forEach var="type" items="${runplans}">
										<option value="${type.value}">${type.key}</option>
									</c:forEach>
							</select></td>
							<td style="padding-left: 20px;display: inline-block;"><input type="submit" value="View Run Plan"></td>
							<td><span id="result" style="display: none"></span></td>
						</tr>
					
					</table>
				</form>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<c:if test="${executiondata.size()!=0}">
							<label>Execution Plan:</label>
							<font style="font-size: 16px; padding-left: 15px; color: green">
								${executiondata.get(0).getFileName()}</font>
						</c:if>
						<!-- /.box-header -->
						<form action="executeplan" method="post">
							<div class="box" style="height: 400px; overflow-y: auto;">
								<c:if test="${executiondata.size()!=0}">
									<input type="hidden" name="fileLocation"
										value="${executiondata.get(0).getFileLocation()}"></input>
									<table id="example1" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>SL No</th>
												<th>Business Requirement</th>
												<th>Modules</th>
												<th>Test Cases</th>
												<th>Iterations</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="data" items="${executiondata}">
												<tr>
													<td>${data.getId()}</td>
													<td>${data.getBR()}</td>
													<td>${data.getModule()}</td>
													<td>${data.getTestCase()}</td>
													<td>${data.getRepeatablity()}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</c:if>
								<c:if test="${executiondata.size()==0}">
									<h2>No Run plan available to Execute in this file</h2>
								</c:if>
							</div>
							<!-- /.box-body -->
							<div align="right" style="padding-right: 20px">
							<span style="padding-left: 50px"><a href="#" class="btn btn-info btn-sm" role="button" onclick="history.back()"><b>Back</b></a></span>
							</div>
							<div align="center">
								<c:if test='${newnode.equals("Yes")}'>
									<div style="padding-left: 10px; padding-top: 0px">
										<font color=Red size=2px>Latest version available:
											${version}</font>
									</div>
								</c:if>
								<span style="padding-right: 5px">Node server running</span> <input type="checkbox" name="startbutton"
									onclick="showButton()"><a href="#myModal" role="button"
									data-toggle="modal"><font color="blue"><span style="padding-left: 5px;padding-right: 5px">  How to	
										start? </span> </font></a><a href="download"><font color="blue">
										Download</font></a>
								<div id="div1" style="display: none">
									<input type="submit" value="Start Execution">
								</div>
							</div>
							
						</form>
						<br>
					</div>	
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel"
							style="text-align: center;">How to start node server on the	client machine</h4>
					</div>
					<div class="modal-body">
						<p>
							<font color="red" size="2px">*Pre-requisite: Java version > 1.7 should be available on the client machine.</font><a
								href="http://www.oracle.com/technetwork/java/javase/downloads/index.html"
								target="_blank"><font color="blue" size="2px"><span style="padding-left: 5px">Get Now</span></font></a>
						</p>
						<p>
							<strong>a) If you have not downloaded the NodeserverBundle.zip</strong>
						</p>
						<p style="padding-left: 15px;">
							1. Download the NodeserverBundle.zip from the below Download link.<br> 
							2. Please wait till	the file dowload is complete.<br>
							3. Extract the downloaded NodeserverBundle.zip on to a desired location.<br>
							4. Go to the extracted location.<br> 
							6. Double click on NodeServer.bat<br> 
							7. Command prompt opens and Nodeserver is started.<br> 
							8. Please do not close the command prompt until Test execution is complete.<br>
						</p>
						<p>
							<strong>b) If you have already downloaded and extracted the NodeserverBundle.zip</strong>
						</p>
						<p style="padding-left: 15px;">
							1. Go to the previously extracted location.<br> 2. Double
							click on NodeServer.bat<br> 3. Command prompt opens and Node
							server is started.<br> 4. Please do not close the command
							prompt until Test execution is complete.
						</p>
					</div>
					<div class="modal-footer">
						<a href="download"><font color="blue">Click here to Download</font></a>
					</div>
				</div>
			</div>
		</div>
		<!-- /Modal -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->
	<script type="text/javascript" src="resources/js/validation.js"></script>
	<!-- jQuery 2.2.3 -->
	<script type="text/javascript" src="resources/js/dropdown.js"></script>
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