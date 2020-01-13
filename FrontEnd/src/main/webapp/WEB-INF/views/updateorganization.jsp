<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Update Organization</title>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="resources/css/_all-skins.min.css">
<style type="text/css">
input, select, textarea {
	width: 40%;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#orgName').change(function(event) {
			var name = document.getElementById('orgName').value;
			$.post("getOrgAjax?orgName=" + name, function(data, status) {
				$('#updateForm').html(data);
			});
		});
	});
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file = "header.jsp" %>

	<%@ include file = "header_aside.jsp"  %>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" style="overflow-x: auto;">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Update Organization</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<div class="box" style="height: 472px; overflow-y: auto;">
							<div>
								<table class="table table-bordered table-striped">
									<tr>
										<td style="padding-right: 8px; width: 225px;">Select
											Organization :</td>
										<td><select name="orgName" id="orgName"
											style="width: 200px; margin-left: 20px;" onmousedown="if(this.options.length>5){this.size=5;}" onchange='this.size=0;eraseMsg();' onblur="this.size=0;">
												<option value="0" selected="selected" hidden="true">
      														  -- Select  --
    													</option>
												<c:forEach items="${orgList}" var="org">
													<c:if test="${org.id != 1}">
													<option value="${org.name}">
      														  ${org.name}
    													</option>
    													</c:if>
												</c:forEach>
										</select></td>
										<td><span id="typeError" style="color: red"></span></td>
									</tr>
								</table>
								<div
									style="border-style: none; border-width: 1px; border-color: lightgray">
									<c:if test="${message.length() != 0}">
										<h5 style="color: green" id="messagetext"><b>${message}</b></h5>
									</c:if>
								</div>
								<div id="updateOrgTableDiv"
									style="border-style: solid; border-width: 1px; border-color: lightgray">
									<form action="updateOrganization" method="post" name="orgform"
										id="updateForm" onsubmit="return orgValidator()">
										<!-- Dynamically generated table will appear here -->
									</form>
								</div>
							</div>
						</div>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
	<%@ include file = "footer.jsp" %>
	</div>
	<!-- ./wrapper -->
	<script type="text/javascript"
		src="resources/js/orgvalidation.js"></script>
	
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