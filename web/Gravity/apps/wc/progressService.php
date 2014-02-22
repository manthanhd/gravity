<?php
	error_reporting(E_ALL);
	require "helpers/MySQLConnection.php";
	if(!empty($_POST)){
		if(!isset($_POST['tokenid'])){
			die("TokenID not set.");
		} else if(empty($_GET) || !isset($_GET['op'])){
			die("No operation has been selected.");
		} else {
			$conn = new MySQLConnection();
			$conn->connect();# or die(json_encode(array('errorCode' => 500, 'errorDescription' => 'Server fault. Could not establish connection to database.')));;
			$tokenid = mysql_real_escape_string($_POST['tokenid']);
			$query = "SELECT * FROM webdb.Request WHERE webdb.Request.tokenid='$tokenid';";
			$result = mysql_query($query) or die("Failed to query database.");
			if(mysql_num_rows($result) > 0){
				$lines = mysql_fetch_array($result, MYSQL_ASSOC);
				# We have the record. Now decide what to return based on the operation.
				if(strtolower($_GET['op']) == 'getstatus'){
					$status = $lines['status'];
					$pp = $lines['progress_percent'];
					echo json_encode(array('status' => $status, 'progress_percent' => $pp));
					//echo json_encode(array('status'=>$status));
				} else if(strtolower($_GET['op']) == 'getresult'){
					$status = $lines['status'];
					if($status != "complete"){
						echo json_encode(array('errorCode' => 1, 'errorDescription' => 'Illegal operation. Task is not complete yet.'));
					} else {
						$expired = $lines['expired'];
						if($expired == 0){
							$output_file = $lines['output_file'];
							$requested_on = $lines['requested_on'];
							$started_on = $lines['started_on'];
							$completed_on = $lines['completed_on'];
							echo json_encode(array(
									'output_file' => $output_file,
									'requested_on' => $requested_on,
									'started_on' => $started_on,
									'completed_on' => $completed_on
							));
						}
					}
				}
			}
			$conn->disconnect();
		}
	} else {
		die(json_encode(array('errorCode' => 3, 'errorDescription' => 'No post data is present.')));
	}

?>
