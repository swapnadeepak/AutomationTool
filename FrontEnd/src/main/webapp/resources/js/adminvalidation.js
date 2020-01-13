/**
 * 
 */
$(document).ready(
		function() {
			$('#emailId').blur(
					function(event) {
						var mailId = document.getElementById('emailId').value;
						var className = 'UserDetail';
						$.get("checkEmailExistence?emailId=" + mailId
								+ "&className=" + className, function(
								responseText) {
							if (responseText.trim() != "") {
								$("#mailError").text(responseText);
								$('#emailId').val('');
								$("#emailId").focus();
							}
						});
					});
		});

$(document).ready(function() {    // Needed for multi-select
	window.prettyPrint && prettyPrint();
	$('.multiselect').multiselect();
});

function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}

function selectGrp(){
	 $('#multiselect_to_1 option').prop('selected', true);
}

function adminValidator() {
	var fv = true;

	var firstNamereg = /^[a-zA-Z ]+$/;
	var lastNamereg = /^[a-zA-Z]*[ \'\-]{0,1}[a-zA-Z]+[ ]*$/;
	/*var emailreg = /\S+@\S+\.\S+/;*/
var emailreg = /^[\w]+[\w-\.]*[\w]+@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	
	var fName = document.adminForm.firstName.value;
	var lName = document.adminForm.lastName.value;
	var email = document.adminForm.emailId.value;
	var pwd = document.adminForm.password.value;
	var status = document.getElementsByName("status");
	var type = document.adminForm.userType.value;
	var org = document.adminForm.organization.value;
	var asGrp = $("#multiselect_to_1 :selected").length;
	
	if (!fName.trim().match(firstNamereg)) {
		document.getElementById('fnError').innerHTML = "First Name cannot have numbers or special characters";
		fv = false;
	}
	if (fName == "" || fName == null) {
		document.getElementById('fnError').innerHTML = "First Name cannot be empty";
		fv = false;
	}
	if (!lName.trim().match(lastNamereg)) {
		document.getElementById('lnError').innerHTML = "Not a valid Last Name";
		fv = false;
	}
	if (lName == "" || lName == null) {
		document.getElementById('lnError').innerHTML = "Last Name cannot be empty";
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
	if (pwd == "" || pwd == null) {
		document.getElementById('pwdError').innerHTML = "Password cannot be empty";
		fv = false;
	}
	if (status[0].checked == false && status[1].checked == false) {
		document.getElementById('statusError').innerHTML = "Please select a Status";
		fv = false;
	}
	if (type == "" || name == null) {
		document.getElementById('typeError').innerHTML = "Please select an User Type";
		fv = false;
	}
	if (org == "" || org == null || org == 0) {
		document.getElementById('orgError').innerHTML = "Please select an Organization";
		fv = false;
	}
	if (asGrp == 0) {
		document.getElementById('asGrpError').innerHTML = "Please select atleast one Group";
		fv = false;
		}
	return fv;
}

function checkFirstName() {
	document.getElementById('fnError').innerHTML = "";
}

function checkLastType() {
	document.getElementById('lnError').innerHTML = "";
}
function checkEmail() {
	document.getElementById('mailError').innerHTML = "";
}
function checkPwd() {
	document.getElementById('pwdError').innerHTML = "";
}
function checkStatus() {
	document.getElementById('statusError').innerHTML = "";
}
function checkType() {
	document.getElementById('typeError').innerHTML = "";
}
function checkOrg() {
	document.getElementById('orgError').innerHTML = "";
}
function checkProject() {
	document.getElementById('projectError').innerHTML = "";
}
function checkAssignGroup(){
	document.getElementById('asGrpError').innerHTML = "";
}

