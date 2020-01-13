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

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file="header.jsp"%>

		<%@ include file="header_aside.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<form action="defects" method="post" name="form1"
					onsubmit="return fileSelectValidate()">
					<table>
						<tr>
							<td  style="width: 200px;display: inline-block;"><label style="padding-left: 20px; padding-top: 10px">Select
									a result folder<span style="color: red">*</span> :</label></td>
							<td style="display: inline-block;"><select id="folderId" style="width: 200px;" onchange="checkFolder();">
									<option value="0" selected="selected" hidden="true"
										disabled="disabled">--Select--</option>
									<c:forEach items="${defectfiles}" var="result">
										<option value="${result.value}">${result.key}</option>
									</c:forEach>
							</select></td>
							<td><span id="folderError"
								style="color: red; font-size: 13px; padding-left:0px;"></span></td>
						</tr>
						<tr>
							<td  style="width: 200px;display: inline-block;"><label style="padding-left: 20px; padding-top: 10px">Select
									a result file <span style="color: red">*</span>:</label></td>
							<td style="display: inline-block;"><select id="fileId" name="fileLocation"
								style="width: 360px;" onchange="checkFile();">
									<option value="0" selected="selected" hidden="true"
										disabled="disabled">--Select--</option>
							</select></td>
							<td><span id="fileError"
								style="color: red; font-size: 13px; padding-left:0px;"></span></td>
								</tr>
								<tr>
							<td><input type="submit" value="View Defects"
								style="margin-left: 650px" onclick="return fileSelectValidate();"></td>
						</tr>
					</table>
				</form>
				<div style="padding-top: 15px;">
					<c:if test="${defects.size()!=0}">
						<h5 style="padding-left: 15px; color: green">
							${defects.get(0).getFileName()}</h5>
					</c:if>
				</div>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<c:if test="${defects.size()!=0}">
							<form method="post">
								<input type="hidden" name="length" value="${defects.size()}">
								<div class="box">
									<!-- /.box-header -->
									<div class="box-body"
										style="max-height: 380px; overflow: auto;">
										<table id="example1"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th>Sl No</th>
													<th>Test Case Id</th>
													<th>Test Case Name</th>
													<th>Defect ID</th>
													<th>Logged Date</th>
													<th>LogDefect<font color="red">*</font></th>
													<th>Summary<font color="red">*</font></th>
													<th>Description<font color="red">*</font></th>
													<th>Reproducibility<font color="red">*</font></th>
													<th>Severity<font color="red">*</font></th>
													<th>Priority<font color="red">*</font></th>
													<th>Assign To<font color="red">*</font></th>
													<th>Steps to Reproduce<font color="red">*</font></th>
													<th>Additional Information<font color="red">*</font></th>
													<th>Upload File Path<font color="red">*</font></th>
													<th>Defect URL</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="data" items="${defects}">
													<input type="hidden" name="fileLocation"
														value="${defects.get(0).getFileLocation()}">
													<tr>
														<td>${data.getId()}<input type="hidden"
															name="id${data.getIndex()}" value="${data.getId()}"></td>
														<td>${data.getTestCaseid()}<input type="hidden"
															name="testCaseid${data.getIndex()}"
															value="${data.getTestCaseid()}"></td>
														<td>${data.getTestCaseName()}<input type="hidden"
															name="testCaseName${data.getIndex()}"
															value="${data.getTestCaseName()}"></td>
														<td>${data.getDefectID()}<input type="hidden"
															name="defectID${data.getIndex()}"
															value="${data.getDefectID()}"></td>
														<td>${data.getLoggedDate()}<input type="hidden"
															name="loggeddate${data.getIndex()}"
															value="${data.getLoggedDate()}"></td>
														<td><select name='logDefect${data.getIndex()}'>
																<c:forEach var="type" items="${logDefect}">
																	<option value="${type.key}"
																		${type.key.equals(data.getLogDefect()) ? "selected='selected'" : ""}>
																		${type.value}</option>
																</c:forEach>
														</select></td>
														<td><input type="text"
															name="summary${data.getIndex()}"
															value="${data.getSummary()}" size="16"
															style="border-style: hidden; background-color: transparent;"></td>
														<td><input type="text"
															name="description${data.getIndex()}"
															value="${data.getDescription()}" size="16"
															style="border-style: hidden; background-color: transparent;"></td>
														<td><select name='reproducibility${data.getIndex()}'
															style="width: 100px;">
																<c:forEach var="type" items="${reproducibilityTypes}">
																	<option value="${type.key}"
																		${type.key.equals(data.getReproducibility()) ? "selected='selected'" : ""}>
																		${type.value}</option>
																</c:forEach>
														</select></td>
														<td><select name='severity${data.getIndex()}'>
																<c:forEach var="type" items="${severityTypes}">
																	<option value="${type.key}"
																		${type.key.equals(data.getSeverity()) ? "selected='selected'" : ""}>
																		${type.value}</option>
																</c:forEach>
														</select></td>
														<td><select name='priority${data.getIndex()}'>
																<c:forEach var="type" items="${priorityTypes}">
																	<option value="${type.key}"
																		${type.key.equals(data.getPriority()) ? "selected='selected'" : ""}>
																		${type.value}</option>
																</c:forEach>
														</select></td>
														<td><select name='assignTo${data.getIndex()}'>
																<c:forEach var="type" items="${assignToTypes}">
																	<option value="${type.key}"
																		${type.key.equals(data.getAssignTo()) ? "selected='selected'" : ""}>
																		${type.value}</option>
																</c:forEach>
														</select></td>
														<td><input type="text"
															name="stepsToReproduce${data.getIndex()}"
															value="${data.getStepsToReproduce()}" size="10"
															style="border-style: hidden; background-color: transparent;"></td>
														<td><input type="text"
															name="additionalInformation${data.getIndex()}"
															value="${data.getAdditionalInformation()}" size="10"
															style="border-style: hidden; background-color: transparent;"></td>
														<td><input type="text"
															name="uploadFilePath${data.getIndex()}"
															value="${data.getUploadFilePath()}" size="10"
															style="border-style: hidden; background-color: transparent;"></td>
														<td><a href="${data.getDefectUrl()}" target="_blank">${data.getDefectUrl()}</a>
															<input type="hidden" name="defecturl${data.getIndex()}"
															value="${data.getDefectUrl()}"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<!-- /.box-body -->
									<br>
									<div align="center">
										<table>
											<tr>
												<td><input type="submit" value="Save"
													onclick="javascript: form.action='update';"> <input
													type="submit" value="Log Defect"
													onclick="javascript: form.action='data';" /></td>
											</tr>
										</table>
										<br>
										<div>
											<c:if test="${message.length() != 0}">
												<h5 style="color: green">${message}</h5>
											</c:if>

										</div>
									</div>
								</div>
								<!-- /.box -->
							</form>
						</c:if>
						<c:if test="${defects.size()==0}">
							<div class="box">
								<!-- /.box-header -->
								<div class="box-body" style="text-align: center;">
									<font style="font-size: 20px; padding-left: 15px;"> No
										defects to display</font>
								</div>
								<!-- /.box-body -->

							</div>
							<!-- /.box -->
						</c:if>
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

	<script src="resources/js/jquery.slimscroll.min.js"></script>
	<script src="resources/js/fastclick.js"></script>
	<script src="resources/js/app.min.js"></script>
	<script src="resources/js/demo.js"></script>

</body>
</html>