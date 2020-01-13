/**
 * 
 * @returns
 */
$(document).ready(
		function() {
			$('#emailId').blur(
					function(event) {
						var mailId = document.getElementById('emailId').value;
						var className = 'Organization';
						$.get("checkEmailExistence?emailId=" + mailId
								+ "&className=" + className, function(
								responseText) {
							if (responseText != "") {
								$("#mailError").text(responseText);
								$('#emailId').val('');
								$("#emailId").focus();
							}
						});
					});
		});
/**
 * 
 * @returns
 */
function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}
/**
 * 
 * @returns
 */
function orgValidator() {
	var fv = true;

	var orgNameReg = /^[a-zA-Z]+[\d ]*[\w]*$/;
	var emailreg = /^[\w]+[\w-\.]*[\w]+@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	var phoneno = /^[\+ \-\d]{10,15}$/;
	var contactPersonReg = /^[a-zA-Z]+[\d ]*[\w]*$/;

	var name = document.orgform.name.value;
	var type = document.orgform.orgType.value;
	var status = document.getElementsByName("orgStatus");
	var address = document.orgform.orgAddress.value;
	var cPerson = document.orgform.contactPerson.value;
	var phoneNumber = document.orgform.contactNumber.value;
	var email = document.orgform.contactEmailId.value;
	var bAddress = document.orgform.billingAddress.value;
	var bRate = document.orgform.billingRate.value;
	var bPeriod = document.orgform.billingPeriod.value;
	var bType = document.orgform.billingType.value;
	var bCurrency = document.orgform.billingCurrency.value;
	var totalLicence = document.orgform.numberOfLicences.value;
	var licenceNum = document.orgform.licenseNumber.value;

	if (!name.trim().match(orgNameReg)) {
		document.getElementById('orgError').innerHTML = "Organization Name cannot have only numbers or special characters";
		fv = false;
	}
	if (name == "" || name == null) {
		document.getElementById('orgError').innerHTML = "Organization Name cannot be empty";
		fv = false;
	}

	if (type == "" || type == null) {
		document.getElementById('typeError').innerHTML = "Please select Organization Type";
		fv = false;
	}

	if (status[0].checked == false && status[1].checked == false) {
		document.getElementById('statusError').innerHTML = "Please select Organization Status";
		fv = false;
	}

	if (address == "" || address == null) {
		document.getElementById('addError').innerHTML = "Organization Address cannot be empty";
		fv = false;
	}

	if (!cPerson.trim().match(contactPersonReg)) {
		document.getElementById('personError').innerHTML = "Contact Person  name cannot have only numbers or special characters";
		fv = false;
	}
	if (cPerson == "" || cPerson == null) {
		document.getElementById('personError').innerHTML = "Contact person name cannot be empty";
		fv = false;
	}

	if (!phoneNumber.match(phoneno)) {
		document.getElementById('phoneError').innerHTML = "Phone Number is not valid";
		fv = false;
	}
	if (phoneNumber == "" || phoneNumber == null) {
		document.getElementById('phoneError').innerHTML = "Phone Number cannot be empty";
		fv = false;
	}
	if (!email.match(emailreg)) {
		document.getElementById('mailError').innerHTML = "Please enter a valid e-mail ID";
		fv = false;
	}
	if (email == "" || email == null) {
		document.getElementById('mailError').innerHTML = "e-mail ID cannot be empty";
		fv = false;
	}
	if (bAddress == "" || bAddress == null) {
		document.getElementById('bAddressError').innerHTML = "Billing Address cannot be empty";
		fv = false;
	}
	if (bRate == "" || bRate == 0) {
		document.getElementById('bRateError').innerHTML = "Please select a Billing Rate";
		fv = false;
	}
	if (bPeriod == "" || bPeriod == 0) {
		document.getElementById('bPeriodError').innerHTML = "Please select Billing Period";
		fv = false;
	}
	if (bType == "" || bType == 0) {
		document.getElementById('bTypeError').innerHTML = "Please select Billing Type";
		fv = false;
	}
	if (bCurrency == "" || bCurrency == null) {
		document.getElementById('bCurrError').innerHTML = "Please select Billing Currency";
		fv = false;
	}
	if (totalLicence == "" || totalLicence == null) {
		document.getElementById('totLicError').innerHTML = "Please select Total Number Of Licences";
		fv = false;
	}
	if (licenceNum == "" || licenceNum == null) {
		document.getElementById('licNumError').innerHTML = "Licence Number cannot be empty";
		fv = false;
	}

	return fv;
}

function checkorgName() {
	document.getElementById('orgError').innerHTML = "";
}

function checkOrgType() {
	document.getElementById('typeError').innerHTML = "";
}

function checkStatus() {
	document.getElementById('statusError').innerHTML = "";
}

function checkOrgAddress() {
	document.getElementById('addError').innerHTML = "";
}

function checkContactPerson() {
	document.getElementById('personError').innerHTML = "";
}

function checkContactNum() {
	document.getElementById('phoneError').innerHTML = "";
}

function checkEmail() {
	document.getElementById('mailError').innerHTML = "";
}

function checkBillAddress() {
	document.getElementById('bAddressError').innerHTML = "";
}

function checkBillRate() {
	document.getElementById('bRateError').innerHTML = "";
}

function checkBillPeriod() {
	document.getElementById('bPeriodError').innerHTML = "";
}

function checkBillType() {
	document.getElementById('bTypeError').innerHTML = "";
}

function checkBillCur() {
	document.getElementById('bCurrError').innerHTML = "";
}

function checkTotalLic() {
	document.getElementById('totLicError').innerHTML = "";
}

function checkLicNum() {
	document.getElementById('licNumError').innerHTML = "";
}