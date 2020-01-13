<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>${appTitle} | Add Organization</title>
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
$(document).ready(
		function() {
			$('#orgName').blur(
					function(event) {
						var name = document.getElementById('orgName').value;
						var className = 'Organization';
						$.get("checkNameExistence?name=" + name + "&className="
								+ className, function(responseText) {
							if (responseText != "") {
								$("#orgError").text(responseText);
								$('#orgName').val('');
								$("#orgName").focus();
							}
						});
					});
		});
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@ include file="header.jsp"%>
		<%@ include file="header_aside.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" style="overflow-x: auto;">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Create Organization</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row" class="content-wrapper">
					<div class="col-xs-12">
						<form action=saveOrg method="post" name="orgform"
							onsubmit="return orgValidator()">
							<div class="box" style="height: 472px; overflow-y: auto;">
								<!-- /.box-header -->
								<!-- 	<div class="box-body" style="height:480px;"> -->
								<table id="example1" class="table table-bordered table-striped">
									<tr>
										<td colspan="3"><c:if test="${message.length() != 0}">
												<h4 id="messagetext" style="color: green">
													<b>${message}</b>
												</h4>
											</c:if></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Organization
											Name <span style="color: red">*</span> :</td>
										<td><input type="text" name="name" id="orgName"
											style="width: 200px; margin-left: 20px;"
											autofocus="autofocus" oninput="checkorgName()"
											onkeydown="eraseMsg()"></td>
										<td><span id="orgError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Organization
											Type <span style="color: red">*</span> :</td>
										<td><select name="orgType" onchange="checkOrgType()"
											style="width: 200px; margin-left: 20px;">
												<c:forEach items="${orgTypeList}" var="orgType">
													<option value="${orgType}">
      														  ${orgType}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="typeError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Organization
											Status <span style="color: red">*</span> :</td>
										<td><input type="radio" name="orgStatus" value="active"
											onclick="checkStatus()"
											style="width: 50px; margin-left: 0px;" checked="checked">Active<br>
											<input type="radio" name="orgStatus" value="inactive"
											onclick="checkStatus()"
											style="width: 50px; margin-left: 0px;">In Active<br></td>
										<td><span id="statusError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Organization
											Address <span style="color: red">*</span> :</td>
										<td><textarea name="orgAddress"
												placeholder="Enter organization address here..."
												onkeyup="checkOrgAddress()"
												style="width: 200px; margin-left: 20px;"></textarea></td>
										<td><span id="addError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Contact
											Person <span style="color: red">*</span> :</td>
										<td><input type="text" name="contactPerson"
											onkeyup="checkContactPerson()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><span id="personError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Phone
											Number <span style="color: red">*</span> :</td>
										<td><input type="text" name="contactNumber"
											maxlength="10" onkeyup="checkContactNum()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><span id="phoneError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
									<!-- onkeyup="checkEmail()" -->
										<td style="padding-right: 8px; width: 225px;">Email Id <span style="color: red">*</span> :</td>
										<td><input type="email" id="emailId"
											name="contactEmailId" oninput="checkEmail()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><span id="mailError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Billing
											Address <span style="color: red">*</span> :</td>
										<td><textarea name="billingAddress"
												placeholder="Enter billing address here..."
												onkeyup="checkBillAddress()"
												style="width: 200px; margin-left: 20px;"></textarea></td>
										<td><span id="bAddressError" style="color: red;font-size: 13px"></span></td>
									</tr>


									<tr>
										<td style="padding-right: 8px; width: 225px;">Billing
											Rate <span style="color: red">*</span> :</td>
										<td><select name=billingRate
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=10;}"
											onchange='this.size=0;' onblur="this.size=0;">

												<c:forEach items="${billingRateList}" var="billingRate">
													<option value="${billingRate}">
      														  ${billingRate}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="bRateError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Billing
											Period <span style="color: red">*</span> :</td>
										<td><select name=billingPeriod
											onchange="checkBillPeriod()"
											style="width: 200px; margin-left: 20px;">

												<c:forEach items="${billingPeriodList}" var="billingPeriod">
													<option value="${billingPeriod}">
      														  ${billingPeriod}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="bPeriodError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Billing
											Type <span style="color: red">*</span> :</td>
										<td><select name=billingType
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=10;}"
											onchange='this.size=0;' onblur="this.size=0;">

												<c:forEach items="${billingTypeList}" var="billingType">
													<option value="${billingType}">
      														  ${billingType}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="bTypeError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Billing
											Currency <span style="color: red">*</span> :</td>
										<td><select name=billingCurrency
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">

												<c:forEach items="${billingCurrencyList}"
													var="billingCurrency">
													<option value="${billingCurrency}">
      														  ${billingCurrency}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="bCurrError" style="color: red;font-size: 13px"></span></td>
									</tr>

									<tr>
										<td style="padding-right: 8px; width: 225px;">Total
											Number Of Licences <span style="color: red">*</span> :</td>
										<td><select name=numberOfLicences
											style="width: 200px; margin-left: 20px;"
											onmousedown="if(this.options.length>5){this.size=5;}"
											onchange='this.size=0;' onblur="this.size=0;">
												<c:forEach items="${licenceNumList}" var="totalLicence">
													<option value="${totalLicence}">
      														  ${totalLicence}
    													</option>
												</c:forEach>
										</select></td>
										<td><span id="totLicError" style="color: red;font-size: 13px"></span></td>
									</tr>
									<tr>
										<td style="padding-right: 8px; width: 225px;">Licence
											Number <span style="color: red">*</span> :</td>
										<td><input type="text" name="licenseNumber"
											onkeyup="checkLicNum()"
											style="width: 200px; margin-left: 20px;"></td>
										<td><span id="licNumError" style="color: red;font-size: 13px"></span></td>
									</tr>

								</table>
								<div align="center">
									<input type="submit" value="Create Organization"
										style="width: 150px;" onclick="return orgValidator();">
								</div>
								<!-- </div> -->
								<!-- /.box-body -->
							</div>

							<!-- /.box -->
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
	<script type="text/javascript" src="resources/js/orgvalidation.js"></script>

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