<html>
	<head>
		<title>Page for testing d3.js</title>
		<?php
		require "helpers/scripts.php";
		?>
		<script src="js/amcharts/amcharts.js" type="text/javascript"></script>
		<script src="js/amcharts/serial.js" type="text/javascript"></script>
		<script type="text/javascript">
			var chartData = [{
				"country" : "USA",
				"visits" : 4252
			}, {
				"country" : "China",
				"visits" : 1882
			}, {
				"country" : "Japan",
				"visits" : 1809
			}, {
				"country" : "Germany",
				"visits" : 1322
			}, {
				"country" : "UK",
				"visits" : 1122
			}, {
				"country" : "France",
				"visits" : 1114
			}, {
				"country" : "India",
				"visits" : 984
			}, {
				"country" : "Spain",
				"visits" : 711
			}, {
				"country" : "Netherlands",
				"visits" : 665
			}, {
				"country" : "Russia",
				"visits" : 580
			}, {
				"country" : "South Korea",
				"visits" : 443
			}, {
				"country" : "Canada",
				"visits" : 441
			}, {
				"country" : "Brazil",
				"visits" : 395
			}, {
				"country" : "Italy",
				"visits" : 386
			}, {
				"country" : "Australia",
				"visits" : 384
			}, {
				"country" : "Taiwan",
				"visits" : 338
			}, {
				"country" : "Poland",
				"visits" : 328
			}];
			var chartData2 = [{
				"country" : "India",
				"visits" : 10
			}, {
				"country" : "Italy",
				"visits" : 100
			}, {
				"country" : "Australia",
				"visits" : 4
			}, {
				"country" : "Taiwan",
				"visits" : 20
			}, {
				"country" : "Poland",
				"visits" : 30
			}];

			AmCharts.ready(function() {
				/*
				 var chart = new AmCharts.AmSerialChart();
				 chart.dataProvider = chartData;
				 chart.categoryField = "country";

				 // Creating a new graph object
				 var graph = new AmCharts.AmGraph(AmCharts.themes.chalk);
				 graph.valueField = "visits";
				 graph.type = "line";
				 //graph.fillAlphas = 0.5;
				 graph.bullet = "round";
				 chart.addGraph(graph);

				 var graph2 = new AmCharts.AmGraph();

				 graph2.valueField = "visits";
				 graph2.type = "line";
				 //graph.fillAlphas = 0.5;
				 graph2.bullet = "square";
				 chart.addGraph(graph2);

				 // Adjustment for displaying the labels 90 degrees...
				 var categoryAxis = chart.categoryAxis;
				 categoryAxis.autoGridCount = false;
				 categoryAxis.gridCount = chartData.length;
				 categoryAxis.gridPosition = "start";
				 categoryAxis.labelRotation = 90;

				 chart.write('chartdiv');
				 */
		</script>
	</head>

	<body>
		<div id="chartdiv" style="width: 640px; height: 400px;">

		</div>
	</body>
</html>