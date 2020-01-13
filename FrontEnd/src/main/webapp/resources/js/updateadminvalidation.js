/**
 * 
 */
function checkGroupselection() {
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
	
	var emailreg = /^[\w]+[\w-\.]*[\w]+@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	var email = document.getElementById("emailId").value;
	var status = document.getElementsByName("status");
	/*var type = document.getElementById("userTypeId").value;*/
	
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
	/*if (type == "" || name == null) {
		document.getElementById('typeError').innerHTML = "Please select valid User Type";
		fv = false;
	}*/
	
	return fv;
}
$(document).ready(
			function() {
				$('#organization').change(
						function() {
							$.getJSON('adminList', {
								orgId : $(this).val()
							}, function(data) {
								var html = '';	
								var len = data.length;
								for (var i = 0; i < len; i++) {
									html += '<option value="' + data[i].id + '">'
											+ data[i].userName + '</option>';
								}
								html += '</option>';
								$('#adminName').html(html);
							});
						});
			});
	
	
	$(document).ready(function() {
		$('#editAdmin').click(function(event) {
			var id = document.getElementById('adminName').value;
			$.post("getAdminAjax?id=" + id, function(data, status) {
				$('#updateAdminForm').html(data);
			});
		});
	});	

function checkEmail() {
	document.getElementById('mailError').innerHTML = "";
}
function checkStatus() {
	document.getElementById('statusError').innerHTML = "";
}
/*function checkType() {
	document.getElementById('typeError').innerHTML = "";
}*/
function checkAssignGroup() {
	document.getElementById('asGrpError').innerHTML = "";
}
function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}
function checkOrgDropdown() {
	document.getElementById('orgdropError').innerHTML = "";
}
function checkAdminDropdown() {
	document.getElementById('admindropError').innerHTML = "";
}


function validateDropdown(){
	var fv=true;
	var orgdropdown = document.getElementById("organization").value;
	var admindropdown =  $("#adminName :selected").length;
	
	if (orgdropdown == 0) {
		document.getElementById('orgdropError').innerHTML = "Please select Organization";
		fv = false;
	}
	if (admindropdown == 0) {
		document.getElementById('admindropError').innerHTML = "Please select Admin";
		fv = false;
	}
	return fv;
}

