<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(
		function() {
			$('#emailId').blur(
					function(event) {
						var mailId = document.getElementById('emailId').value;
						var className = 'Organization';
						var id = ${organization.id};
						$.get("checkEmailOnUpdate?emailId=" + mailId
								+ "&className=" + className+"&id="+id, function(
								responseText) {
							if (responseText != "") {
								$("#mailError").text(responseText);
								$('#emailId').val('');
								$("#emailId").focus();
							}
						});
					});
		});
</script>
<table id="example1" class="table table-bordered table-striped">
	<tr>
		<td><input type="hidden" name="id" id="id"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${organization.id}"/>"></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Organization Name:</td>
		<td><input type="text" name="name" id="orgName"
			style="width: 200px; margin-left: 20px;background-color: #e5dee4;"
			value="<c:out value="${organization.name}"/>" readonly="readonly"></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Organization Type <span style="color: red">*</span> :</td>
		<td><select name="orgType" onkeyup="checkOrgType()"
			style="width: 200px; margin-left: 20px;">
				<c:forEach items="${orgTypeList}" var="orgType">
					<c:if test="${orgType eq organization.orgType}">
						<option value="${organization.orgType}" selected="selected"> ${organization.orgType} </option>
					</c:if>
					<c:if test="${orgType != organization.orgType}">
						<option value="${orgType}"> ${orgType}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="typeError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Organization
			Status <span style="color: red">*</span> :</td>
		<td><c:if test="${organization.orgStatus eq 'active'}">
				<input type="radio" name="orgStatus" checked="checked"
					value="active" onclick="checkStatus()"
					style="width: 50px; margin-left: 0px;"> Active<br>
				<input type="radio" name="orgStatus" value="inactive"
					onclick="checkStatus()" style="width: 50px; margin-left: 0px;"> In Active
										</c:if> <c:if test="${organization.orgStatus eq 'inactive'}">
				<input type="radio" name="orgStatus" value="active"
					onclick="checkStatus()" style="width: 50px; margin-left: 0px;"> Active<br>
				<input type="radio" name="orgStatus" value="inactive"
					onclick="checkStatus()" checked="checked"
					style="width: 50px; margin-left: 0px;"> In Active
										</c:if></td>
		<td><span id="statusError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Organization
			Address <span style="color: red">*</span> :</td>
		<td><textarea name="orgAddress"
				placeholder="Enter organization address here..."
				onkeyup="checkOrgAddress()" style="width: 200px; margin-left: 20px;">${organization.orgAddress}</textarea></td>
		<td><span id="addError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Contact Person <span style="color: red">*</span> :</td>
		<td><input type="text" name="contactPerson"
			onkeyup="checkContactPerson()"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${organization.contactPerson}"/>"></td>
		<td><span id="personError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Phone Number <span style="color: red">*</span> :</td>
		<td><input type="text" name="contactNumber" maxlength="10"
			onkeyup="checkContactNum()" style="width: 200px; margin-left: 20px;"
			value="<c:out value="${organization.contactNumber}"/>"></td>
		<td><span id="phoneError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Email Id <span style="color: red">*</span> :</td>
		<td><input type="email" id="emailId" name="contactEmailId"
			oninput="checkEmail()" style="width: 200px; margin-left: 20px;"
			value="<c:out value="${organization.contactEmailId}"/>"></td>
		<td><span id="mailError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Billing Address <span style="color: red">*</span> :</td>
		<td><textarea name="billingAddress"
				placeholder="Enter billing address here..."
				onkeyup="checkBillAddress()"
				style="width: 200px; margin-left: 20px;">${organization.billingAddress}</textarea></td>
		<td><span id="bAddressError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Billing Rate <span style="color: red">*</span> :</td>
		<td><select name=billingRate onkeyup="checkBillRate()"
			style="width: 200px; margin-left: 20px;"
			onmousedown="if(this.options.length>5){this.size=5;}"
			onchange='this.size=0;' onblur="this.size=0;">

				<c:forEach items="${billingRateList}" var="billingRate">
					<c:if test="${billingRate eq organization.billingRate}">
						<option value="${organization.billingRate}" selected="selected"> ${organization.billingRate} </option>
					</c:if>
					<c:if test="${billingRate != organization.billingRate}">
						<option value="${billingRate}"> ${billingRate}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="bRateError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Billing Period <span style="color: red">*</span> :</td>
		<td><select name=billingPeriod onkeyup="checkBillPeriod()"
			style="width: 200px; margin-left: 20px;"
			onmousedown="if(this.options.length>5){this.size=5;}"
			onchange='this.size=0;' onblur="this.size=0;">
				<c:forEach items="${billingPeriodList}" var="billingPeriod">
					<c:if test="${billingPeriod eq organization.billingPeriod}">
						<option value="${organization.billingPeriod}" selected="selected"> ${organization.billingPeriod} </option>
					</c:if>
					<c:if test="${billingPeriod != organization.billingPeriod}">
						<option value="${billingPeriod}"> ${billingPeriod}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="bPeriodError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Billing Type <span style="color: red">*</span> :</td>
		<td><select name=billingType onkeyup="checkBillType()"
			style="width: 200px; margin-left: 20px;">
				<c:forEach items="${billingTypeList}" var="billingType">
					<c:if test="${billingType eq organization.billingType}">
						<option value="${organization.billingType}" selected="selected"> ${organization.billingType} </option>
					</c:if>
					<c:if test="${billingType != organization.billingType}">
						<option value="${billingType}"> ${billingType}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="bTypeError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Billing Currency <span style="color: red">*</span> :</td>
		<td><select name=billingCurrency onkeyup="checkBillCur()"
			style="width: 200px; margin-left: 20px;"
			onmousedown="if(this.options.length>5){this.size=5;}"
			onchange='this.size=0;' onblur="this.size=0;">
				<c:forEach items="${billingCurrencyList}" var="billingCurrency">
					<c:if test="${billingCurrency eq organization.billingCurrency}">
						<option value="${organization.billingCurrency}"
							selected="selected"> ${organization.billingCurrency} </option>
					</c:if>
					<c:if test="${billingCurrency != organization.billingCurrency}">
						<option value="${billingCurrency}"> ${billingCurrency}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="bCurrError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Total Number Of
			Licences <span style="color: red">*</span> :</td>
		<td><select name=numberOfLicences onkeyup="checkTotalLic()"
			style="width: 200px; margin-left: 20px;"
			onmousedown="if(this.options.length>5){this.size=5;}"
			onchange='this.size=0;' onblur="this.size=0;">
				<c:forEach items="${licenceNumList}" var="totalLicence">
					<c:if test="${totalLicence eq organization.numberOfLicences}">
						<option value="${organization.numberOfLicences}"
							selected="selected"> ${organization.numberOfLicences} </option>
					</c:if>
					<c:if test="${totalLicence != organization.numberOfLicences}">
						<option value="${totalLicence}"> ${totalLicence}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<td><span id="totLicError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Licence Number <span style="color: red">*</span> :</td>
		<td><input type="text" name="licenseNumber"
			onkeyup="checkLicNum()" style="width: 200px; margin-left: 20px;"
			value="<c:out value="${organization.licenseNumber}"/>"></td>
		<td><span id="licNumError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="submit"
			value="Update Organization" style="width: 150px;"
			onclick="return orgValidator();"></td>
	</tr>
</table>
