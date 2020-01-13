<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle}|Create Master Plan</title>
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
<style>
.inputfield {
	border-style: hidden;
	background-color: transparent;
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
				<h1>Master Sheet</h1>
			</section>
			<!-- Main content -->
			<form action="masterplan" method="post" id="myform">
				<div align="right" style="padding-right: 20px">
					<input type="button"
						class="btn btn-default pull-right fa fa-trash-o" id="deleterow"
						value='Delete Row' style="font-weight: bolder;" /> <input
						type="button" class="btn btn-default pull-right fa fa-trash-o"
						id="addrow" value="Add Row" style="font-weight: bolder;" />
				</div>
				<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box" style="height: 380px; overflow-y: auto;">
								<!-- /.box-header -->
								<div class="box-body">
									<div style="text-align: right; padding-right: 15px;">
										<font color="red" size="2px">*fields are editable</font>
									</div>
									<table id="tab_logic"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Sl No</th>
												<th>Business Requirement</th>
												<th>Description</th>
												<th>Impacted BRs</th>
												<th>Include</th>
												<th>Modules</th>
												<th>Include</th>
												<th>Test Case</th>
												<th>Include</th>
												<th>Test Case Id</th>
												<th>Criticality</th>
												<th>Iterations</th>
											</tr>
										</thead>
										<tbody>
											<tr id='addr1'></tr>
										</tbody>
									</table>


								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
							<div align="center">
								<input type="submit" value="create">
							</div>
						</div>
						<!-- /.col -->

					</div>
					<!-- /.row -->
				</section>
			</form>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<script type="text/javascript" src="js/validation.js"></script>
<!-- 	<!-- jQuery 2.2.3 -->
	<script src="js/jquery-2.2.3.min.js"></script> -->
	<!-- Bootstrap 3.3.6 -->
	<script src="js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="js/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="js/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="js/app.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="js/demo.js"></script>
	<!-- page script -->
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var i = 1;
							$("#addrow")
									.click(
											function() {
												$('#addr' + i)
														.html(
																'<td><input type="text" name="slno'+i+'" value="'+i+'" class="inputfield"></td>'
																		+ '<td><input type="text" name="businessrequirement'+i+'" placeholder="BusinessRequirement" class="inputfield"></td>'
																		+ '<td><input type="text" name="description'+i+'" placeholder="Description" class="inputfield"></td>'
																		+ '<td><input type="text" name="impactedbr'+i+'" placeholder="Impacted BR" class="inputfield"></td>'
																		+ '<td><select name="includeimpactedbrs'+i+'"><option value="Yes">Yes</option><option value="No">No</option></select></td>'
																		+ '<td><input type="text" name="modules'+i+'" placeholder="Modules" class="inputfield"></td>'
																		+ '<td><select name="includeModule'+i+'"><option value="Yes">Yes</option><option value="No">No</option></select></td>'
																		+ '<td><input type="text" name="testcases'+i+'" placeholder="testcase" class="inputfield" required></td>'
																		+ '<td><select name="includetestcase'+i+'"><option value="Yes">Yes</option><option value="No">No</option></select></td>'
																		+ '<td><input type="text" name="testcaseid'+i+'" placeholder="Test Case Id" class="inputfield"></td>'
																		+ '<td><input type="text" name="criticallity'+i+'" placeholder="Criticallity" class="inputfield"></td>'
																		+ '<td><input type="text" name="iterations'
																		+ i
																		+ '" placeholder="Iterations" class="inputfield" onkeyup="return onlyNos(this,event);" required></td>');

												$('#tab_logic').append(
														'<tr id="addr'
																+ (i + 1)
																+ '"></tr>');
												i++;
											});
							$("#deleterow").click(function() {
								if (i > 1) {
									$("#addr" + (i - 1)).html('');
									i--;
								}
							});
							$("#myform").submit(
									function() {
										$('<input />').attr('type', 'hidden')
												.attr('name', "length").attr(
														'value', i).appendTo(
														'#myform');
										return true;
									});
						});
	</script>
</body>
</html>