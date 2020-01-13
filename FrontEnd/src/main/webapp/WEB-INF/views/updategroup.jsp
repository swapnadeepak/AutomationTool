<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type"
	content="application/x-www-form-urlencoded; charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Update Group</title>
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
<link
	href="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.min.css"
	rel="stylesheet" />
<link
	href="https://cdn.rawgit.com/davidstutz/bootstrap-multiselect/master/dist/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css" />
	  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />
		<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<style type="text/css">
input, select, textarea {
	width: 40%;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
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
				<h1>Update Group</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<div class="box" style="height: 472px; overflow-y: auto;">
							<div>
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td style="padding-right: 8px; width: 225px;">Select
											Group :</td>
										<td>
										<select name="groupNname" id="groupNname"
										onclick="eraseMsg();" 
											style="width: 280px; margin-left: 20px;" onmousedown="if(this.options.length>10){this.size=10;}"
											onchange='this.size=0;' onblur="this.size=0;">
												<option value="" selected="selected" hidden="true">
      														Select
    													</option>
												<c:forEach items="${groupList}" var="group">
													<option value="${group.id}"> ${group.name}</option>
												</c:forEach>
										</select>
										</td>
										<td><div id="bCurrError" style="color: red"></div></td>
									</tr>
								</table>
								<div
									style="border-style: none; border-width: 1px; border-color: lightgray">
									<c:if test="${message.length() != 0}">
										<h5 id="messagetext" style="color: green"><b>${message}</b></h5>
									</c:if>
								</div>
								<div id="updateGroupTableDiv"
									style="border-style: solid; border-width: 1px; border-color: lightgray">
									<form action="processUpdateGroup" method="post" name="groupform"
										id="updateGroupForm" onsubmit="return updateGroupValidator();">
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
		<%@ include file="footer.jsp"%>

	</div>
	<!-- ./wrapper -->

	<script type="text/javascript"
		src="resources/js/updategrpvalidation.js"></script>


	<script type="text/javascript"
		src="//cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.min.js"></script>
	<script
		src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>

	<script
		src="https://cdn.rawgit.com/davidstutz/bootstrap-multiselect/master/dist/js/bootstrap-multiselect.js"
		type="text/javascript"></script>

	<!-- Bootstrap 3.3.6 -->
	<script src="resources/js/bootstrap.min.js"></script>
	  <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js">

	<script src="resources/js/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="resources/js/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="resources/js/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="resources/js/app.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="resources/js/demo.js"></script>
	<script src="resources/js/multiselect.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			// make code pretty
			window.prettyPrint && prettyPrint();
			$('.multiselect').multiselect();
		});
	</script>
</body>
</html>