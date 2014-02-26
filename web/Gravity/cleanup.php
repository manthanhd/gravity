<?php
	require "helpers/WCRequest.php";
	$failedCount = 0;
	$failThreshold = 5;
	$failInterval = 1; # in minutes
	$interval = 30; # in minutes
	$mode = "debug";
	while(true){
		llog($mode, "Requesting cleanup...");
		$response = WCRequest::cleanupRequests(5);
		if($response == FALSE){
			llog($mode, "Cleanup failed. Attempt $failedCount.");
			if($failedCount<$failThreshold){
				$failedCount++;
				sleep($failInterval * 60);
			} else {
				llog("debug", "Cleanup has been failing since $failedCount attempts. Please investigate the issue.");
				exit(1);
			}
		} else {
			llog($mode, "Cleanup successful. Will wait for $interval minutes.");
			$failedCount = 0;
			sleep($interval * 60);
		}
	}

	function llog($mode, $str){
			if(isset($mode)){
				if($mode == "debug"){
					echo date("Y-m-d H:i:s") . "\t$str\n";
				}
			}
	}
?>
