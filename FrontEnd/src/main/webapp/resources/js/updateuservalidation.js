/**
 * 
 */
function checkselection() {
	var fv = true;
	var chks = document.getElementsByName('assignedGroup');
	var hasChecked = false;
	if (chks.length > 0) {
		for (var i = 0; i < chks.length; i++) {
			if (chks[i].checked) {
				hasChecked = true;
				break;
			}
		}
		if (hasChecked == false) {
			document.getElementById('asGrpError').innerHTML = "Please assign atleast one Group";
			fv = false;
		}
	}

	var proChks = document.getElementsByName('project');
	var hasProChecked = false;
	if (proChks.length > 0) {
		for (var i = 0; i < proChks.length; i++) {
			if (proChks[i].checked) {
				hasProChecked = true;
				break;
			}
		}
		if (hasProChecked == false) {
			document.getElementById('projectError').innerHTML = "Please select atlest one project";
			fv = false;
		}
	}
	return fv;
}

/*$(document).ready(
		function() {
			$('#organizationDropDown').change(
					function() {
						$.getJSON('userList', {
							orgId : $(this).val()
						}, function(data) {
							var html = '';
							var len = data.length;
							for (var i = 0; i < len; i++) {
								html += '<option value="' + data[i].id + '">'
										+ data[i].userName + '</option>';
							}
							html += '</option>';
							$('#userName').html(html);
						});
					});
		});
*/
$(document).ready(function() {
	$('#userName').change(function(event) {
		var id = document.getElementById('userName').value;
		$.post("getUserAjax?id=" + id, function(data, status) {
			$('#updateUserForm').html(data);
		});
	});
});

/*$(document).ready(
		function() {
			$('#organization').change(
					function() {
						$.getJSON('projectList', {
							orgName : $(this).val()
						}, function(data) {
							var html = '';
							var len = data.length;
							for (var i = 0; i < len; i++) {
								html += '<option  value="' + data[i] + '">'
										+ data[i] + '</option>';
							}
							html += '</option>';
							$('#project').html(html);
						});
					});
		});*/

function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}

function userValidatorForUpdate() {
	var fv = true;
	var emailreg = /^[\w]+[\w-\.]*[\w]+@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	var email = document.getElementById("userEmail").value;
	var status = document.getElementsByName("status");
	/*var type = document.getElementById("userType").value;*/

	if (!email.match(emailreg)) {
		document.getElementById('mailError').innerHTML = "Please enter a valid e-mail ID";
		fv = false;
	}
	if (email == "" || email == null) {
		document.getElementById('mailError').innerHTML = "e-mail ID cannot be empty";
		fv = false;
	}
	if (status[0].checked == false && status[1].checked == false) {
		document.getElementById('statusError').innerHTML = "Please select Status";
		fv = false;
	}
/*	if (type == "" || name == null) {
		document.getElementById('typeError').innerHTML = "Please select valid user type";
		fv = false;
	}*/
	return fv;
}
function checkEmail() {
	document.getElementById('mailError').innerHTML = "";
}
function checkStatus() {
	document.getElementById('statusError').innerHTML = "";
}
function checkProject() {
	document.getElementById('projectError').innerHTML = "";
}
function checkType() {
	document.getElementById('typeError').innerHTML = "";
}
function checkAssignGroup() {
	document.getElementById('asGrpError').innerHTML = "";
}