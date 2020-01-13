<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Run plan</title>
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
				<h1>Run plan</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<form action="createRunPlan" id="runplanform" method="post">
							<input type="hidden" name="length"
								value="${masterPlanList.size()}">
							<div class="box" id="box"
								style="height: 472px; overflow-y: auto;">
								<!-- /.box-header -->
								<!-- 	<div class="box-body" style="height:480px;"> -->
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>SL No</th>
											<th>Business Requirement</th>
											<!-- <th>Description</th> -->
											<!-- <th>Impacted BRs</th> -->
											<th>Include</th>
											<th>Modules</th>
											<th>Include</th>
											<th>Test Case</th>
											<th>Test Case Id</th>
											<th>Include</th>
											<th>Iterations</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="data" items="${masterPlanList}">
											<tr>
												<td><input type="text" name="id${data.getIndex()}"
													value='${data.getId()}' readonly size="5"
													style="border-style: hidden; background-color: transparent;"></td>
												<td><c:if test="${data.getBusinessRequirement() != ''}">
														<input type="text"
															name="businessRequirement${data.getIndex()}"
															value='${data.getBusinessRequirement()}' readonly
															size="16"
															style="border-style: hidden; background-color: transparent;"
															data-toggle="tooltip" title="${data.getDescription()}">
													</c:if>
														<%-- ${data.getDescription()} --%>
													 <input type="hidden" name="description${data.getIndex()}"
													value='${data.getDescription()}' readonly size="16"
													style="border-style: hidden; background-color: transparent;">
													<input type="hidden" value='${data.getImpactedBRs()}'
													readonly size="16"
													style="border-style: hidden; background-color: transparent;">
												</td>
												<td><c:if
														test='${data.getIncludeImpactedBRs().length() != 0}'>
														<select name='includeImpactedBRs${data.getIndex()}'>
															<option value="Yes"
																${data.getIncludeImpactedBRs().equals("Yes") ? "selected='selected'" : ""}>Yes</option>
															<option value="No"
																${data.getIncludeImpactedBRs().equals("No") ? "selected='selected'" : ""}>No</option>
														</select>
													</c:if></td>
												<td><input name="modules${data.getIndex()}" type="text"
													value='${data.getModules()}' readonly size="16"
													style="border-style: hidden; background-color: transparent;"></td>
												<td><c:if
														test='${data.getModulesInclude().length() != 0}'>
														<select name='modulesInclude${data.getIndex()}'>
															<option value="Yes"
																${data.getModulesInclude().equals("Yes") ? "selected='selected'" : ""}>Yes</option>
															<option value="No"
																${data.getModulesInclude().equals("No") ? "selected='selected'" : ""}>No</option>
														</select>
													</c:if></td>
												<td>${data.getTestCases()}<input
													name="testCases${data.getIndex()}" type="hidden"
													value='${data.getTestCases()}' readonly size="16"
													style="border-style: hidden; background-color: transparent;">
												</td>
												<td>
													<input name="testCaseId${data.getIndex()}" type="text"
												value='${data.getTestCaseId()}' readonly style="border-style: hidden; background-color: transparent;">
												</td>
												
												<td><c:if
														test='${data.getTestCasesInclude().length() != 0}'>
														<select name='testCasesInclude${data.getIndex()}'>
															<option value="Yes"
																${data.getTestCasesInclude().equals("Yes") ? "selected='selected'" : ""}>Yes</option>
															<option value="No"
																${data.getTestCasesInclude().equals("No") ? "selected='selected'" : ""}>No</option>
														</select>
													</c:if></td>
												<td align="right"><input type="text"
													id="repeatablity${data.getIndex()}"
													name="repeatablity${data.getIndex()}"
													value="${data.repeatablity}" size="10"
													style="border-style: hidden; background-color: transparent;"
													onkeyup="return onlyNos(this,event);"
													onchange="return notempty(this);"></td>
											</tr>
											<input type="hidden" name="criticality${data.getIndex()}"
												value="${data.getCriticality()}">
											
										
										</c:forEach>
									</tbody>
								</table>

								<!-- </div> -->
								<!-- /.box-body -->
							</div>

							<div align="center">
								<input type="submit" id="submit" value="Create Run Plan"
									style="width: 150px; border-top-width: 6px">
							</div>
							<!-- /.box -->
							<c:if test="${message.length() != 0}">
								<h5 style="color: green">
									<b>${message}</b>
								</h5>
							</c:if>
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