/**
 * 
 */

//$(document).ready(
//		function() {
//			$('#organization').change(
//					function() {
//						$.getJSON('getProjectList', {
//							orgId : $(this).val()
//						}, function(data) {
//							var html = '';
//							var len = data.length;
//							for (var i = 0; i < len; i++) {
//								html += '<option value="' + data[i].id + '">'
//										+ data[i].name + '</option>';
//							}
//							html += '</option>';
//							$('#projectNname').html(html);
//						});
//					});
//		});

$(document).ready(function() {
	$('#projectNname').change(function(event) {
		var id = document.getElementById('projectNname').value;
		$.post("getProjectAjax?id=" + id, function(data, status) {
			$('#updateProjectForm').html(data);
		});
	});
});

function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}

/**
 * Validation function for update project
 * @returns
 */
function updateProjectValidator() {       //Not used because project name is readonly in update
	var fv = true;
	var projectNamereg = /^[a-zA-Z]+[\d ]*[\w ]*$/;
	var projectName = document.getElementById('pName').value;
	if (!projectName.trim().match(projectNamereg)) {
		document.getElementById('pnError').innerHTML = "Project Name cannot have only numbers or special characters";
		fv = false;
	}
	if (projectName == "" || projectName == null) {
		document.getElementById('pnError').innerHTML = "Project Name cannot be empty";
		fv = false;
	}

	return fv;
}

function checkProjectName() {
	document.getElementById('pnError').innerHTML = "";
}

