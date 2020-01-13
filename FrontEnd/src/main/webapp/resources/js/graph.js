https://www.gstatic.com/charts/loader.js
google.charts.load('current', {
	'packages' : [ 'corechart' ]
});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	var pass = parseInt(document.form2.pass.value);
	var fail = parseInt(document.form2.fail.value);
	var norun = parseInt(document.form2.noRun.value);
	var data = google.visualization.arrayToDataTable([
			[ 'Test Case', 'success' ], [ 'Pass', pass ], [ 'Fail', fail ],
			[ 'No Run', norun ], ]);
	var options = {
		colors : [ '#2faf3c', '#ff0000', '#708090' ],
		is3D : true,
	};

	var chart = new google.visualization.PieChart(document
			.getElementById('piechart'));

	chart.draw(data, options);
}