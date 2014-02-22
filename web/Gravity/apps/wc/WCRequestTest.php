<?php
	require "helpers/WCRequest.php";
	$requests = WCRequest::getNew();
	echo count($requests) . ' requests found.';
	if(count($requests)>0){
		foreach($requests as $request){
			echo "\nProcessing... $request->tokenid";
			echo "\nStarting...";
			sleep(5);
			$request->start();
			sleep(30);
			$request->complete();
		}
	}
?>
