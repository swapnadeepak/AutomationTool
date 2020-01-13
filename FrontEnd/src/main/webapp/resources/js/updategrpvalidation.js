/**
 * 
 */
$(document).ready(function() {
	$('#groupNname').change(function(event) {
		var id = document.getElementById('groupNname').value;
		$.post("getGroupAjax?id=" + id, function(data, status) {
			$('#updateGroupForm').html(data);
		});
	});
});
function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}
function updateGroupValidator() {
	var fv = true;
	var groupNamereg = /^[a-zA-Z]+[\d ]*[\w ]*$/;
	var grpName = document.getElementById('groupName').value;
	var chks = document.getElementsByName('assignedRoles');
	var hasChecked = false;
	
	if (!grpName.trim().match(groupNamereg)) {
		document.getElementById('gnError').innerHTML = "Group Name cannot have numbers or special characters";
		fv = false;
	}
	if (grpName == "" || grpName == null) {
		document.getElementById('gnError').innerHTML = "Group Name cannot be empty";
		fv = false;
	}

	if (chks.length > 0) {
		for (var i = 0; i < chks.length; i++) {
			if (chks[i].checked) {
				hasChecked = true;
				break;
			}
		}
		if (hasChecked == false) {
			document.getElementById('roleError').innerHTML = "Please assign atleast one Role";
			fv = false;
		}
	}
	return fv;
}
function checkGrpName() {
	document.getElementById('gnError').innerHTML = "";
}
function checkRole() {
	document.getElementById('roleError').innerHTML = "";
}
