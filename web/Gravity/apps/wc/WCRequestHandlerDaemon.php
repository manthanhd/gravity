<?php
	require "request_handler.php";
	while(true){
		WCRequestHandler::kickOffPendingJobs('debug');
		sleep(60);
	}
?>
