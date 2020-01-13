/**
 * 
 */
 $(document).ready(function() {
  	$('#projectName').blur(function(event) {
  		var name = document.getElementById('projectName').value;
  		var className = 'Project';
  		$.get("checkNameExistence?name="+name+"&className="+className, function(responseText) {
  			if(responseText != ""){
  			$("#pnError").text(responseText);
  			 $('#projectName').val('');
			 $( "#projectName" ).focus();
  			}
  		});
  	});
  });

 function eraseMsg(){
		document.getElementById('messagetext').innerHTML = "";
	}
 
function projectValidator(){
	var fv=true;
	
	var projectNameReg = /^[a-zA-Z]+[\d ]*[\w]*$/;
	
	var proName=document.getElementById('projectName').value;
	var type=document.getElementById('proType').value;
	var status = document.getElementsByName("status");
	
	if (!proName.trim().match(projectNameReg)) {
		document.getElementById('pnError').innerHTML = "Project Name cannot have only numbers or special characters";
		fv = false;
	}
	if (proName == "" || proName == null) {
		document.getElementById('pnError').innerHTML = "Project Name cannot be empty";
		fv = false;
	}
	if (type == "" || type == null || type == 0) {
		document.getElementById('typeError').innerHTML = "Please select Project Type";
		fv = false;
	}
	if (status[0].checked == false && status[1].checked == false) {
		document.getElementById('statusError').innerHTML = "Please select Project Status:";
		fv = false;
	}
	return fv;
}


function checkProName() {
	document.getElementById('pnError').innerHTML = "";
}
function checkProType() {
	document.getElementById('typeError').innerHTML = "";
}
function checkProType() {
	document.getElementById('statusError').innerHTML = "";
}



