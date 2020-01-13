<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title>${appTitle}| Results</title>
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
				<form action="display" method="post" name="form1"
					onsubmit="return fileSelectValidate();">
					<table>
						<tr>
							<td style="width: 200px;display: inline-block;"><label
								style="padding-left: 20px; padding-top: 10px">Select a
									result folder <span style="color: red">*</span> :</label></td>
							<td style="display: inline-block;"><select id="folderId" style="width: 200px;" onchange="checkFolder()">
									<option value="0" selected="selected" hidden="true"
										disabled="disabled" >--    Select    --</option>
									<c:forEach items="${resultFileList}" var="result">
										<option value="${result.value}">${result.key}</option>
									</c:forEach>
							</select></td>
							<td  style="display: inline-block;"><span id="folderError"
								style="color: red; font-size: 13px; padding-left: 350px;"></span></td>
						</tr>
						<tr>
							<td style="width: 200px;display: inline-block;"><label
								style="padding-left: 20px; padding-top: 10px">Select a
									result file <span style="color: red">*</span> :</label></td>
							<td  style="display: inline-block;"><select id="fileId" name="fileLocation"
								style="width: 360px;" onchange="checkFile()">
									<option value="0" selected="selected" hidden="true"
										disabled="disabled">-- Select --</option>
							</select></td>
							<td  style="display: inline-block;"><span id="fileError"
								style="color: red; font-size: 13px; padding-left: 190px;"></span></td>
						</tr>
						<tr>
							<td  style="display: inline-block;"><input type="submit" value="View Results"
								style="margin-left: 650px;" onclick="return fileSelectValidate();"></td>
						</tr>
					</table>
				</form>
				<c:if test="${UsedRunPlan.size()!=0}">
					<h5 style="padding-left: 5px; color: green">
						${UsedRunPlan.get(0).getFileName()}</h5>
				</c:if>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<c:if test="${result.size()!=0}">
								<div style="text-align: right;margin-right: 15px">
									<c:url value="save" var="url">
  										<c:param name="fileLocation" value="${result.get(0).getFileLocation()}" />
									</c:url>
									<a href="${url}">Download
										result file </a>|
									<%-- <a href="save?fileLocation=${result.get(0).getFileLocation()}">Download
										result file </a>|<a --%>
									 <c:url value="logs" var="url">
  										<c:param name="fileLocation" value="${result.get(0).getFileLocation()}" />
									</c:url>
										<a href="${url}">
										Download log file </a>
									<c:if test="${defectsize>0}">
									|
									 <c:url value="defects" var="url">
  										<c:param name="fileLocation" value="${result.get(0).getFileLocation()}" />
									</c:url>
								<a href="${url}">Log Defects</a>
									</c:if>
								</div>
							</c:if>
							<!-- /.box-header -->
							<div class="box-body" style="height: 125px; overflow-y: auto;">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>SL No</th>
											<th>Business Requirement</th>
											<th>Modules</th>
											<th>Test Case Id</th>
											<th>Test Cases</th>
											<th>Result</th>
											<th>Time Taken</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="data" items="${UsedRunPlan}">
											<tr>
												<td>${data.getId()}</td>
												<td>${data.getBR()}</td>
												<td>${data.getModule()}</td>
												<td>${data.getTestCaseId()}</td>
												<td>${data.getTestCase()}</td>
												<td>${data.getResult()}</td>
												<td>${data.getTimeTaken()}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /.box-body -->
							<br />
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-md-7">
						<!--Status -->
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">Status</h3>
							</div>
							<div class="box-body">
								<div
									style="height: 190px; overflow-y: auto; text-align: center;">
									<table id="example1" class="table table-bordered table-striped">
										<c:forEach var="data" items="${result}">
											<tr>
												<th>${data.getResultkey()}</th>
												<td>${data.getResultvalue()}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col (LEFT) -->
					<div class="col-md-5">
						<!-- Graph -->
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">Graph</h3>
							</div>
							<div class="box-body">
								<div class="chart" style="height: 190px; overflow: hidden;">
									<div id="piechart" style="height: 210px; align: left"></div>
								</div>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col (RIGHT) -->
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<c:if test="${result.size()>0}">
		<form name="form2">
			<input type="hidden" name="pass"
				value="${result.get(8).getResultvalue()}"> <input
				type="hidden" name="fail" value="${result.get(9).getResultvalue()}">
			<input type="hidden" name="noRun"
				value="${result.get(10).getResultvalue()}">
		</form>
	</c:if>

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

	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawChart);

		function drawChart() {
			var pass = parseInt(document.form2.pass.value);
			var fail = parseInt(document.form2.fail.value);
			var norun = parseInt(document.form2.noRun.value);
			var data = google.visualization.arrayToDataTable([
					[ 'Test Case', 'success' ], [ 'Pass', pass ],
					[ 'Fail', fail ], [ 'No Run', norun ], ]);
			var options = {
				colors : [ '#2faf3c', '#ff0000', '#708090' ],
				is3D : true,
			};

			var chart = new google.visualization.PieChart(document
					.getElementById('piechart'));

			chart.draw(data, options);
		}
	</script>

</body>
</html>