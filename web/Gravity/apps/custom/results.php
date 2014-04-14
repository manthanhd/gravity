<html>
	<head>
		<title>Results</title>
		<?php
		require "helpers/scripts.php";
		?>
		<script src="js/amcharts/amcharts.js" type="text/javascript"></script>
		<script src="js/amcharts/serial.js" type="text/javascript"></script>
		<script type="text/javascript"><?php echo "var tokenid = \"" . $_GET['tokenid'] . "\";"; ?>
			/*var cpuArray = [];
			 var hcpuArray = [];
			 var ttcpuArray = [];
			 var memArray = [];*/
			$(document).ready(function(){
				$(function () {
						$("[rel='tooltip']").tooltip();
				});
				/*var cpuUsageButton = $('#showCpuUsage');
				var cpuvisualdiv = $('#cpuvisual');
				var memUsageButton = $('#showMemoryUsage');
				var memvisualdiv = $('#memvisual');
				var diskUsageButton = $('#showDiskUsage');
				var diskvisualdiv = $('#diskvisual');
				
				cpuUsageButton.click(function(){
					hideAll();
					cpuvisual.show();
					cpuUsageButton.addClass("active");
				});
				
				memUsageButton.click(function(){
					hideAll();
					memvisual.show();
					memUsageButton.addClass("active");
				});
				
				diskUsageButton.click(function(){
					hideAll();
					diskvisual.show();
					diskUsageButton.addClass("active");
				});
				
				function hideAll(){
					cpuvisualdiv.hide();
					cpuUsageButton.removeClass("active");
					memvisualdiv.hide();
					memUsageButton.removeClass("active");
					diskvisualdiv.hide();
					diskUsageButton.removeClass("active");
				}*/
				/*$('ul li').click(function(){
					$('ul li').removeClass('active');
					$(this).addClass('active');
					$('div[tag]').hide();
					var count = $()
					$('div[tag]:nth-child(' + )').hide();
				});*/
			});
			displayCpuData();
			displayMemData();
			displayDiskData();
			
			function displayDiskData() {
				$.get("statService.php?app_name=wc&tokenid=" + tokenid + "&mode=disk_general", function(data) {
					// We have retrieved all objects. Now need to parse it to json
					var infoarray = JSON.parse(data);
					var processedObjects = 0;
					console.log("Processed objects: " + processedObjects);

					// Using the parsed object array infoarray, now we draw the actual graph
					var diskchart = new AmCharts.AmSerialChart();
					diskchart.dataProvider = infoarray;
					//alert(infoarray.length);
					diskchart.categoryField = "timestamp";
					diskchart.title = "Disk usage across nodes over time";

					// Creating a new graph object for master
					var hdgraph = new AmCharts.AmGraph();
					hdgraph.valueField = "heisenbergDisk";
					hdgraph.type = "line";
					//hcgraph.fillAlphas = 0.5;
					hdgraph.bullet = "round";
					hdgraph.title = "Heisenberg (Master)";
					diskchart.addGraph(hdgraph);

					var ttdgraph = new AmCharts.AmGraph();
					ttdgraph.valueField = "titaniumDisk";
					ttdgraph.type = "line";
					//ttcgraph.fillAlphas = 0.5;
					ttdgraph.bullet = "round";
					ttdgraph.title = "Titanium (Slave)";
					diskchart.addGraph(ttdgraph);

					var tdgraph = new AmCharts.AmGraph();
					tdgraph.valueField = "tungstenDisk";
					tdgraph.type = "line";
					//tcgraph.fillAlphas = 0.5;
					tdgraph.bullet = "round";
					tdgraph.title = "Tungsten (Slave)";
					diskchart.addGraph(tdgraph);

					var sdgraph = new AmCharts.AmGraph();
					sdgraph.valueField = "siliconDisk";
					sdgraph.type = "line";
					//scgraph.fillAlphas = 0.5;
					sdgraph.bullet = "round";
					sdgraph.title = "Silicon (Slave)";
					diskchart.addGraph(sdgraph);

					// Adjustment for displaying the labels 90 degrees...
					var categoryAxis = diskchart.categoryAxis;
					categoryAxis.autoGridCount = false;
					categoryAxis.gridCount = infoarray.length / 4;
					categoryAxis.gridPosition = "start";
					categoryAxis.labelRotation = 90;

					var legend = new AmCharts.AmLegend();
					diskchart.addLegend(legend, "disklegenddiv");

					// value
				    var valueAxis = new AmCharts.ValueAxis();
				    //valueAxis.dashLength = 5;
				    valueAxis.title = "Disk Usage (%)";
				    valueAxis.axisAlpha = 0;
				    diskchart.addValueAxis(valueAxis);
				    
					diskchart.write('diskchartdiv');
				});
			}

			function displayMemData() {
				$.get("statService.php?app_name=wc&tokenid=" + tokenid + "&mode=mem", function(data) {
					// We have retrieved all objects. Now need to parse it to json
					var infoarray = JSON.parse(data);
					var processedObjects = 0;
					console.log("Processed objects: " + processedObjects);

					// Using the parsed object array infoarray, now we draw the actual graph
					var memchart = new AmCharts.AmSerialChart();
					memchart.dataProvider = infoarray;
					//alert(infoarray.length);
					memchart.categoryField = "timestamp";
					memchart.title = "Memory usage across nodes over time";

					// Creating a new graph object for master
					var hmgraph = new AmCharts.AmGraph();
					hmgraph.valueField = "heisenbergMem";
					hmgraph.type = "line";
					//hcgraph.fillAlphas = 0.5;
					hmgraph.bullet = "round";
					hmgraph.title = "Heisenberg (Master)";
					memchart.addGraph(hmgraph);

					var ttmgraph = new AmCharts.AmGraph();
					ttmgraph.valueField = "titaniumMem";
					ttmgraph.type = "line";
					//ttcgraph.fillAlphas = 0.5;
					ttmgraph.bullet = "round";
					ttmgraph.title = "Titanium (Slave)";
					memchart.addGraph(ttmgraph);

					var tmgraph = new AmCharts.AmGraph();
					tmgraph.valueField = "tungstenMem";
					tmgraph.type = "line";
					//tcgraph.fillAlphas = 0.5;
					tmgraph.bullet = "round";
					tmgraph.title = "Tungsten (Slave)";
					memchart.addGraph(tmgraph);

					var smgraph = new AmCharts.AmGraph();
					smgraph.valueField = "siliconMem";
					smgraph.type = "line";
					//scgraph.fillAlphas = 0.5;
					smgraph.bullet = "round";
					smgraph.title = "Silicon (Slave)";
					memchart.addGraph(smgraph);

					// Adjustment for displaying the labels 90 degrees...
					var categoryAxis = memchart.categoryAxis;
					categoryAxis.autoGridCount = false;
					categoryAxis.gridCount = infoarray.length / 4;
					categoryAxis.gridPosition = "start";
					categoryAxis.labelRotation = 90;

					var legend = new AmCharts.AmLegend();
					memchart.addLegend(legend, "memlegenddiv");
					// value
				    var valueAxis = new AmCharts.ValueAxis();
				    //valueAxis.dashLength = 5;
				    valueAxis.title = "Memory Usage (%)";
				    valueAxis.axisAlpha = 0;
				    memchart.addValueAxis(valueAxis);
				    
					memchart.write('memchartdiv');
				});
			}

			function displayCpuData() {
				$.get("statService.php?app_name=wc&tokenid=" + tokenid + "&mode=cpu", function(data) {
					// We have retrieved all objects. Now need to parse it to json
					var infoarray = JSON.parse(data);
					var processedObjects = 0;
					console.log("Processed objects: " + processedObjects);

					// Using the parsed object array infoarray, now we draw the actual graph
					var cpuchart = new AmCharts.AmSerialChart();
					cpuchart.dataProvider = infoarray;
					//alert(infoarray.length);
					cpuchart.categoryField = "timestamp";
					cpuchart.title = "CPU usage across nodes over time";

					// Creating a new graph object for master
					var hcgraph = new AmCharts.AmGraph();
					hcgraph.valueField = "heisenbergCpu";
					hcgraph.type = "line";
					//hcgraph.fillAlphas = 0.5;
					hcgraph.bullet = "round";
					hcgraph.title = "Heisenberg (Master)";
					cpuchart.addGraph(hcgraph);

					var ttcgraph = new AmCharts.AmGraph();
					ttcgraph.valueField = "titaniumCpu";
					ttcgraph.type = "line";
					//ttcgraph.fillAlphas = 0.5;
					ttcgraph.bullet = "round";
					ttcgraph.title = "Titanium (Slave)";
					cpuchart.addGraph(ttcgraph);

					var tcgraph = new AmCharts.AmGraph();
					tcgraph.valueField = "tungstenCpu";
					tcgraph.type = "line";
					//tcgraph.fillAlphas = 0.5;
					tcgraph.bullet = "round";
					tcgraph.title = "Tungsten (Slave)";
					cpuchart.addGraph(tcgraph);

					var scgraph = new AmCharts.AmGraph();
					scgraph.valueField = "siliconCpu";
					scgraph.type = "line";
					//scgraph.fillAlphas = 0.5;
					scgraph.bullet = "round";
					scgraph.title = "Silicon (Slave)";
					cpuchart.addGraph(scgraph);

					// Adjustment for displaying the labels 90 degrees...
					var categoryAxis = cpuchart.categoryAxis;
					categoryAxis.autoGridCount = false;
					categoryAxis.gridCount = infoarray.length / 4;
					categoryAxis.gridPosition = "start";
					categoryAxis.labelRotation = 90;

					var legend = new AmCharts.AmLegend();
					cpuchart.addLegend(legend, "cpulegenddiv");

				    // value
				    var valueAxis = new AmCharts.ValueAxis();
				    //valueAxis.dashLength = 5;
				    valueAxis.title = "Cpu Usage (%)";
				    valueAxis.axisAlpha = 0;
				    cpuchart.addValueAxis(valueAxis);
				    
					cpuchart.write('cpuchartdiv');
				});
			}
		</script>
	</head>
	<body class="container">
		<?php
		require "helpers/header.php";
		require "helpers/WCRequest.php";

		$invalidToken = FALSE;
		$expired = FALSE;
		$request = NULL;
		if (!empty($_GET) && isset($_GET['tokenid'])) {
			if (WCRequest::isTokenValid($_GET['tokenid']) == FALSE) {
				$invalidToken = TRUE;
				echo "invalid";
			} else {
				# Token is valid
				$request = WCRequest::get($_GET['tokenid']);
			}
		} else {
			header("Location: /Gravity/apps/wc/index.php");
		}
		?>

		<div class="row">
			<div class="text-center">
				<form action="getResult.php" method="get">
					<hr/>
					<h2>Result for token <?php echo $request -> tokenid; ?></h2>
					<hr/>
					<p>
						<?php
						#echo "<center><table>";
						#echo "<tr><td>Submitted: <td>$request->requested_on</tr><tr><td>Started: <td>$request->started_on</tr><tr><td>Completed: <td>$request->completed_on</tr>";
						#echo "</table></center>";
						$time = explode(' ', $request -> requested_on);
						list($rhours, $rminutes, $rseconds) = explode(':', $time[1]);
						$rTimestamp = mktime($rhours, $rminutes, $rseconds);
						# echo "rTimestamp: $rTimestamp<br/>";

						$time = explode(' ', $request -> started_on);
						list($shours, $sminutes, $sseconds) = explode(':', $time[1]);
						$sTimestamp = mktime($shours, $sminutes, $sseconds);
						# echo "sTimestamp: $sTimestamp<br/>";

						$time = explode(' ', $request -> completed_on);
						list($chours, $cminutes, $cseconds) = explode(':', $time[1]);
						$cTimestamp = mktime($chours, $cminutes, $cseconds);
						# echo "cTimestamp: $cTimestamp<br/>";

						$executionTimeSeconds = $cTimestamp - $sTimestamp;
						$executionTimeMinutes = ($executionTimeSeconds / 60) % 60;
						$executionTimeSeconds = round($executionTimeSeconds / 60);
						$executionTimeHours = round($executionTimeSeconds / 3600);
						$executionTimeSeconds = round($executionTimeSeconds / 3600);

						$totalTimeSeconds = $cTimestamp - $rTimestamp;
						$totalTimeMinutes = ($totalTimeSeconds / 60) % 60;
						$totalTimeSeconds = round($totalTimeSeconds / 60);
						$totalTimeHours = round($totalTimeSeconds / 3600);
						$totalTimeSeconds = round($totalTimeSeconds / 3600);

						echo "<h4>";
						echo "Execution time: " . $executionTimeHours . "h $executionTimeMinutes" . "m $executionTimeSeconds" . "s<br/>";
						echo "Total time: " . $totalTimeHours . "h $totalTimeMinutes" . "m $totalTimeSeconds" . "s<br/>";
						echo "</h4>";
						?>	
					</p>
					<?php
					if (file_exists($request -> output_file)) {
						echo "<button id='downloadResultButton' class='btn btn-primary btn-lg' type='submit'>Download Result</button>\n";
						echo "<input type='hidden' name='tokenid' value='" . $request -> tokenid . "'/>";
					} else {
						echo "<h3>Output does not exist. Your token may have expired.</h3>";
					}
					?>
					<!--
						<button id="downloadResultButton" class="btn btn-primary btn-lg" type="submit">Download Result</button>
						<input type="hidden" name="tokenid" value="<?php echo $request->tokenid;?>"/>
					-->
				</form>
		<?php
				if ($invalidToken == FALSE && $expired == FALSE) {
					echo "<a href='getLog.php?tokenid=" . $_GET['tokenid'] . "'>View log</a>";
			?>
					<!--<ul class="nav nav-pills">
					  <li class="active" id="showCpuUsage"><a href="#">Cpu Usage</a></li>
					  <li id="showMemoryUsage"><a href="#">Memory usage</a></li>
					  <li id="showDiskUsage"><a href="#">Disk usage</a></li>
					</ul>-->
					<h2>Statistics</h2>
					
					<hr/>
					<h3>Execution times</h3>
					<hr/>
						<div class="progress">
						<?php
							$totalTime = $request->prepTime + $request->hdfsInTime + $request->mapreduceTime + $request->hdfsOutTime + $request->cleanupTime;
							$prepPercent = ($request->prepTime / $totalTime) * 100;
							echo '<div class="progress-bar" style="width: ' .  round($prepPercent) . '%;background-image: none;background-color: yellow;" data-toggle="tooltip" data-placement="top" title="Prep time ' . $request->prepTime . ' second(s)"></div>';
							$hdfsInPercent = ($request->hdfsInTime / $totalTime) * 100;
							echo '<div class="progress-bar" style="width: ' .  round($hdfsInPercent) . '%;background-image: none;background-color: orange;" data-toggle="tooltip" data-placement="top" title="HDFS Input time ' . $request->hdfsInTime . ' second(s)"></div>';
							$mapreducePercent = ($request->mapreduceTime / $totalTime) * 100;
							echo '<div class="progress-bar" style="width: ' .  round($mapreducePercent) . '%;background-image: none;background-color: green;" data-toggle="tooltip" data-placement="top" title="Mapreduce time ' . $request->mapreduceTime . ' second(s)"></div>';
							$hdfsOutPercent = ($request->hdfsOutTime / $totalTime) * 100;
							echo '<div class="progress-bar" style="width: ' .  round($hdfsOutPercent) . '%;background-image: none;background-color: orange;" data-toggle="tooltip" data-placement="top" title="HDFS Output time ' . $request->hdfsOutTime . ' second(s)"></div>';
							$cleanupPercent = ($request->cleanupTime / $totalTime) * 100;
							echo '<div class="progress-bar" style="width: ' .  round($cleanupPercent) . '%;background-image: none;background-color: yellow;" data-toggle="tooltip" data-placement="top" title="Cleanup time ' . $request->cleanupTime . ' second(s)"></div>';
						?>
						</div>
					<hr/>
					<h3>Cpu usage over time</h3>
					<hr/>
					<div class="col-md-2"></div>
					<div tag='chart' id='cpuvisual' class='row'>
						<div id='cpuchartdiv' style='float:left; width: 640px; height: 400px;'></div>
						<div id='cpulegenddiv' style='width: 200px; height: 200px;'></div>
					</div>
					
					<hr/>
					<h3>Memory use over time</h3>
					<hr/>
					<div class="col-md-2"></div>
					<div tag='chart' id='memvisual' class='row'>
						<div id='memchartdiv' style='float:left; width: 640px; height: 400px;'></div>
						<div id='memlegenddiv' style='width: 200px; height: 200px;'></div>
					</div>
					
					<hr/>
					<h3>Disk use over time</h3>
					<hr/>
					<div class="col-md-2"></div>
					<div tag='chart' id='diskvisual' class='row'>
						<div id='diskchartdiv' style='float:left; width: 640px; height: 400px;'></div>
						<div id='disklegenddiv' style='width: 200px; height: 200px;'></div>
					</div>
			<?
			}
				?>
			</div>
		</div>
	</body>
</html>
