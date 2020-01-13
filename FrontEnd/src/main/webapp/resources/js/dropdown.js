$(document).ready(
		function() {
			$('#folderId').change(
					function() {
						$.getJSON('fileList', {
							folderPath : $(this).val()
						}, function(data) {
							var $select = $('#fileId'); 
							$("#fileId option").remove();
							 $.each(data, function(key, value) {              
								 $select.append('<option value=' + value + '>' + key + '</option>');    
							    });
						});
					});
		});

function fileSelectValidate() {
	var fv = true;
	var folder = document.getElementById('folderId').value;
	var file = document.getElementById('fileId').value;
	
	if (folder == 0 || folder == null) {
		document.getElementById('folderError').innerHTML = "Please select a result folder";
		fv = false;
	}
		
	if (file == 0 || file == null) {
		document.getElementById('fileError').innerHTML = "Please select a result file";
		fv = false;
	}
	return fv;
}

function stopExecution() {
	var xmlhttp;
	var urls = "stop";
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.open("GET", urls, true);
	xmlhttp.send();
}
function showMessage() {
	document.getElementById("stop").style.display = "inline";
}
function checkFolder() {
	document.getElementById('folderError').innerHTML = "";
}
function checkFile() {
	document.getElementById('fileError').innerHTML = "";
}



