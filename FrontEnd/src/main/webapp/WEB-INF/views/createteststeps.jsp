<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h1>Test steps</h1>
			</section>
			<!-- Main content -->
			<div align="right" style="padding-right: 20px">
				<input type="button"
					class="btn btn-default pull-right fa fa-trash-o" id="deleterow"
					value='Delete Row' style="font-weight: bolder;" /> <input
					type="button" class="btn btn-default pull-right fa fa-trash-o"
					id="addrow" value="Add Row" style="font-weight: bolder;" />
			</div>
			<section class="content">
				<form action="teststeps" method="post" id="myform">
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
												<th>Testcase ID</th>
												<th>Business Requirement</th>
												<th>Module</th>
												<th>Testcase</th>
												<th>Sl No</th>
												<th>Keyword</th>
												<th>Object Name</th>
												<th>Object value</th>
												<th>Class file Name</th>
												<th>Description</th>
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
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<div align="center">
						<input type="submit" value="create">
					</div>
				</form>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->

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
																'<td><input type="text" name="tcid'+i+'" placeholder="Testcase Id" class="inputfield" required></td>'
																		+ '<td><input type="text" name="businessrequirement'+i+'" placeholder="BusinessRequirement" class="inputfield" required></td>'
																		+ '<td><input type="text" name="module'+i+'" placeholder="Module" class="inputfield" required></td>'
																		+ '<td><input type="text" name="testcase'+i+'" placeholder="Testcase" class="inputfield" required></td>'
																		+ '<td><input type="text" name="slno'+i+'" value="#'+i+'" class="inputfield"></td>'
																		+ '<td><select name="keyword'+i+'"><option value="">--select--</option><c:forEach var="type" items="${keywords}"><option value="${type}">${type}</option></c:forEach></select></td>'
																		+ '<td><input type="text" name="object'+i+'" placeholder="Object Name" class="inputfield" required></td>'
																		+ '<td><input type="text" name="testobjectdata'+i+'" placeholder="Object value" class="inputfield" required></td>'
																		+ '<td><select name="classfile'+i+'"><option value="">--select--</option><c:forEach var="type" items="${classfile}"><option value="${type}">${type}</option></c:forEach></select></td>'
																		+ '<td><input type="text" name="description'+i+'" placeholder="Description" class="inputfield"></td>');

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