<html>
	<head>
		<title>Results</title>
		<?php require "helpers/scripts.php";?>
	</head>
	<body class="container">
		<?php
			require "helpers/header.php";
			require "helpers/WCRequest.php";

			$invalidToken = FALSE;
			$expired = FALSE;
			$request = NULL;
			if(!empty($_GET) && isset($_GET['tokenid'])){
				if(WCRequest::isTokenValid($_GET['tokenid']) == FALSE){
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
					<h3><label for="downloadResultButton">Result for token <?php echo $request->tokenid;?></label></h3>
					<p>
						<?php
							#echo "<center><table>";
							#echo "<tr><td>Submitted: <td>$request->requested_on</tr><tr><td>Started: <td>$request->started_on</tr><tr><td>Completed: <td>$request->completed_on</tr>";
							#echo "</table></center>";
							$time = explode(' ',$request->requested_on);
							list($rhours, $rminutes, $rseconds) = explode(':',$time[1]);
							$rTimestamp = mktime($rhours, $rminutes, $rseconds);
							# echo "rTimestamp: $rTimestamp<br/>";

							$time = explode(' ',$request->started_on);
							list($shours, $sminutes, $sseconds) = explode(':',$time[1]);
							$sTimestamp = mktime($shours, $sminutes, $sseconds);
							# echo "sTimestamp: $sTimestamp<br/>";

							$time = explode(' ',$request->completed_on);
							list($chours, $cminutes, $cseconds) = explode(':',$time[1]);
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
						if(file_exists($request->output_file)){
							echo "<button id='downloadResultButton' class='btn btn-primary btn-lg' type='submit'>Download Result</button>\n";
							echo "<input type='hidden' name='tokenid' value='" . $request->tokenid . "'/>";
						} else {
							echo "<h2>Output does not exist. Your token may have expired.</h2>";
						}
						
					?>
					<!--
						<button id="downloadResultButton" class="btn btn-primary btn-lg" type="submit">Download Result</button>
						<input type="hidden" name="tokenid" value="<?php echo $request->tokenid;?>"/>
					-->
				</form>
				<?php
					if($invalidToken == FALSE && $expired == FALSE){
						echo "<a href='getLog.php?tokenid=" . $_GET['tokenid'] . "'>View log</a>";
					}
				?>
			</div>
		</div>
	</body>
</html>
