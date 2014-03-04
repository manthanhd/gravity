<html>
	<head>
		<title>Word Counter</title>
		<?php require "helpers/scripts.php"; error_reporting(E_ALL);?>
		<script type="text/javascript">
			window.status = "";
			function callWS(){
				$.post("progressService.php?op=getStatus", {tokenid:value}, function(data, status){
					//alert("Data: " + data + "\nStatus: " + status);
					var d = JSON.parse(data);
					//console.log(d.status);
					if(d.status){
						/*if(d.status == "submitted"){
							$('#statusLabel').text("Status: Submitted");
							if($('#progressDiv').hasClass('progress-striped') == false){
								$('#progressDiv').addClass('progress-striped active');
								$('#processProgress').css("width", 100 + "%");
							}
						} else if(d.status == "started"){
							$('#statusLabel').text("Status: Started");
							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
						} else if(d.status == "completed"){
							$('#statusLabel').text("Status: Completed");
							$('#viewResultsLink').css('visibility','visible');
							$('#processHeading').css('visibility','hidden');
							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
						} else if(d.status == "initHadoop"){
							$('#statusLabel').text("Status: Initiated Cluster");
              if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
						} else if(d.status == "hdfs"){
							$('#statusLabel').text("Status: Importing output to HDFS");
							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
						} else if(d.status == "mapreduce"){
							$('#statusLabel').text("Status: Running MapReduce on cluster");
							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
						} else if(d.status == "retrieve"){
							$('#statusLabel').text("Status: Retrieving output");
							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}
					}*/
						if(d.status != window.status){
							$('#statusLabel').text("Status: " + d.status_message);

							if($('#progressDiv').hasClass('progress-striped') == true){
								$('#progressDiv').removeClass('progress-striped active');
							}

							if(d.status == "submitted"){
								$('#progressDiv').addClass('progress-striped active');
								$('#processProgress').css("width", 100 + "%");
							} else if(d.status == "completed") {
								$('#viewResultsLink').css('visibility','visible');
								$('#processHeading').css('visibility','hidden');
								$('#processProgress').css("width", 100 + "%");
							} else {	
								$('#processProgress').css("width", d.progress_percent + "%");
							}

							console.log(d.status);
							status = d.status;
						}
					}
				});
			}
			$(document).ready(function() {
				/*$('#cp').click(function(){
					$.post("progressService.php?op=getStatus", {tokenid:"token1"}, function(data, status){
						alert("Data: " + data + "\nStatus: " + status);
						var d = JSON.parse(data);
						alert(d.status);
					});
				});*/
				window.status = "";
				value = $('#tokenid').val();
				if($('#progressDiv').css("visibility") != "hidden"){
					callWS();
					setInterval(function(){
						// $.post("progressService.php?op=getStatus", {tokenid:value}, function(data, status){
							// //alert("Data: " + data + "\nStatus: " + status);
							// var d = JSON.parse(data);
							// console.log(d.status);
						// });
						callWS();
					}, 500);
				}
			});
			
		</script>
	</head>

	<body class="container">
		<?php
			require "helpers/header.php";
			require "helpers/MySQLConnection.php";
			require_once "helpers/WCRequest.php";
			require "helpers/txtUpload.php";
		?>
		<div class="row">
			<p>
				<?php
					$tokenid = NULL;
					$invalidToken = FALSE;
					if(!empty($_FILES)){
						$filename = upload("textFileUpload", $_FILES);
						echo "Your input file has been uploaded.";
						$tokenid=uniqid();
						$conn = new MySQLConnection();
						$conn->connect();
						$date = date('Y-m-d H:i:s');
						$query = "INSERT INTO webdb.Request (tokenid, app_name, input_file, status, progress_percent, expired, " .
							"requested_on) VALUES ('$tokenid', 'wc', '$filename', 'submitted', 10, 0, '$date')";
						mysql_query($query) or die(mysql_error());
						$conn->disconnect();
					} else if(isset($_GET['tokenid'])){
						$tokenid = $_GET['tokenid'];
						if(WCRequest::isTokenValid($tokenid) == FALSE){
							echo "<h2>Invalid token</h2>";
							$invalidToken = TRUE;
						}
					} else {
						header("Location: /Gravity/apps/wc/index.php");
					}
				?>
			</p>
			<p id="statusLabel" class="lead">
				
			</p>
			<div id="progressDiv" class="progress" style="<?php if($invalidToken==TRUE){echo "visibility:hidden";}else{echo "";}?>">
				<div id="processProgress" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
				</div>
			</div>
			<a id="viewResultsLink" href="results.php?tokenid=<?php echo $tokenid;?>" style="visibility: hidden;">View Results</a>
			<!--<button id="cp" class="btn btn-primary">Change Progress by 10</button>-->		
			<h2 id="processHeading" class="text-center" style="<?php if($invalidToken == TRUE){echo "visibility:hidden";}else{echo "";}?>">Processing. Please wait. This process can take up to 5 minutes.</h2>
			<form id="dataform" action="progressService.php?op=getStatus" method="post" style="visibility: hidden;">
				<input type="hidden" name="tokenid" id="tokenid" value="<?php echo $tokenid;?>"/>
			</form>
			<div class="text-center">
				<h4>Please bookmark this page or note down tokenid <?php echo $tokenid; ?> for future reference.</h4>
			</div>
		</div>
	</body>
</html>
