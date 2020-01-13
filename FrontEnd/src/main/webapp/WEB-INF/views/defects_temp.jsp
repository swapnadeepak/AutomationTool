<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title>${appTitle}|Defects</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="shortcut icon" href="resources/images/index.png"
	type="image/png" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="resources/css/AdminLTE.min.css">
<link rel="stylesheet" href="resources/css/_all-skins.min.css">
<!-- <link rel="stylesheet" href="resources/css/multi_level_dropdown.css"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
window.onload = function() {
	var msg ="${disable}";
	if(msg == 'true'){
		document.getElementById("defect").disabled = true;
	}
}
function eraseMsg(){
	document.getElementById('messagetext').innerHTML = "";
}

function fetchValue(key,value) {
	document.getElementById("placeName").innerHTML = key;
	document.getElementById("fileLocationId").value = value;
}
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
		<!-- 	Content Header (Page header) -->
			<section class="content-header">
				<form action="defects" method="Post" name="form1"
					onsubmit="return selectResult()">
					<div class="box"
						style="height: 200px; background-color: transparent;">
						<table>
							<tr>
								<td style="padding-top: 30px"><label
									style="padding-left: 20px;">Select a defect file :</label></td>
								<td  style="padding-top: 27px">
									<ul class="dropdown" style="width: 200px; margin-left: 20px;">
									<li><button id="placeName" 
										type="button" data-toggle="dropdown"
										style="height: 30px; width: 350px; text-align: left;background-color:transparent;">
										<pre
											style="background-color:transparent;overflow: hidden; width: 335px; height: 17px; border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-right-width: 0px; padding-top: 0px; padding-bottom: 0px;">--  Select --                              <span
												class="caret"></span>
										</pre>

									</button>
									<ul class="dropdown-menu" style="width: 170px">
										<li class="dropdown-submenu"><c:forEach
												items="${defectFiles}" var="result">
												<a class="test" tabindex="-1" href="#">${result.key}<span
													style="margin-left: 50px" class="caret"></span></a>
												<ul class="dropdown-menu">
													<c:forEach items="${result.value}" var="res">
														<li><a tabindex="-1" href="#"
															onclick="javascript:fetchValue('<c:out value="${res.key}"/>','${fn:replace(res.value, '\\', '\\\\')}');">${res.key}</a></li>

													</c:forEach>
												</ul>
											</c:forEach></li>
									</ul></li>
								</ul>
								
									<input type="hidden" id="fileLocationId" name="fileLocation"
									value=" " /> 
								</td>
								<td style="padding-top: 27px; padding-left: 150px"><input
									type="submit" id="defect" value="View Defects"
									style="width: 150px; margin-left: 10px;"></td>
								<td><span id="result" style="display: none"></span></td>
							</tr>
							<tr>
								<td colspan="3"><c:if test="${message != null}">
										<h5 id="messagetext" style="color: green">${message}</h5>
									</c:if></td>
							</tr>
						</table>
					</div>
				</form>
			</section>

			<!-- Main content -->
			<section class="content"></section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<!-- jQuery 2.2.3 -->
	<script type="text/javascript" src="resources/js/dropdown.js"></script>
	
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