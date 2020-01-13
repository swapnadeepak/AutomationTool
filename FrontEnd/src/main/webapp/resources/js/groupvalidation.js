/**
 * 
 */
$(document).ready(
		function() {
			$('#groupName').blur(
					function(event) {
						var name = document.getElementById('groupName').value;
						var className = 'Group';
						$.get("checkNameExistence?name=" + name + "&className="
								+ className, function(responseText, status) {
							if (responseText.trim() != "") {
								$("#gnError").text(responseText);
								$('#groupName').val('');
								$("#groupName").focus();
							}
						});
					});
		});

$(document).ready(function() {
	window.prettyPrint && prettyPrint();
	$('.multiselect').multiselect();
});

function eraseMsg() {
	document.getElementById('messagetext').innerHTML = "";
}

function selectRole(){
	 $('#multiselect_to_1 option').prop('selected', true);
}


function groupValidator() {
	var fv = true;
	var groupNamereg = /^[a-zA-Z]+[\d ]*[\w ]*$/;
	var grpName = document.getElementById('groupName').value;
	var link = document.getElementsByName("type");
	var asRole = $("#multiselect_to_1 :selected").length;
	
	if (!grpName.trim().match(groupNamereg)) {
		document.getElementById('gnError').innerHTML = "Group Name cannot have only numbers or special characters";
		fv = false;
	}
	if (grpName == "" || grpName == null) {
		document.getElementById('gnError').innerHTML = "Group Name cannot be empty";
		fv = false;
	}
	if (link[0].checked == false && link[1].checked == false) {
		document.getElementById('linkError').innerHTML = "Please select Group type";
		fv = false;
	}
	if (asRole == 0) {
		document.getElementById('asRoleError').innerHTML = "Please select atleast one Role";
		fv = false;
		}
	return fv;
}
function checkGrpName() {
	document.getElementById('gnError').innerHTML = "";
}
function checkLink() {
	document.getElementById('linkError').innerHTML = "";
}
function checkRole() {
	document.getElementById('asRoleError').innerHTML = "";
}