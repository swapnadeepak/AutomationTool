function validate() {
	var email = document.getElementById("emailId");
	if (trim(email.value) == "") {
		document.getElementById("mail").innerHTML = "<h3 align='center'><b>Please enter e-mail ID.</b></h3>";
		document.getElementById("mail").style.display = "inline";
		emailId.focus();
		return false;
	}

	var email = document.form1.emailId;
	var e_mail = document.form1.emailId.value;
	var atpos = e_mail.indexOf("@");
	var dotpos = e_mail.lastIndexOf(".");
	if (email.value == "" || atpos < 1 || dotpos < atpos + 2
			|| dotpos + 2 >= e_mail.length) {
		document.getElementById("mail").innerHTML = "<h3 align='center'><b>Please enter valid e-mail ID</b></h3>";
		document.getElementById("mail").style.display = "inline";
		emailId.focus();
		return false;
	}

	var password = document.getElementById("password");
	if (trim(password.value) == "") {
		document.getElementById("mail").innerHTML = "<h3 align='center'><b>Please enter Password.</b></h3>";
		document.getElementById("mail").style.display = "inline";
		password.focus();
		return false;
	}
}
function trim(str) {
	return str.replace(/^\s+|\s+$/g, '');
}
function showButton() {
	var chboxs = document.getElementsByName("startbutton");
	var vis = "none";
	for (var i = 0; i < chboxs.length; i++) {
		if (chboxs[i].checked) {
			vis = "block";
			break;
		}
	}
	document.getElementById("div1").style.display = vis;
}
