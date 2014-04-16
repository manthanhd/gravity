<?php
require "/var/www/Gravity/helpers/WCRequest.php";

$options = "t:p:m:";
$options = getopt($options);
if($options['t'] && $options['p'] && $options['m']){
	$tokenid = $options['t'];
	$progress = $options['p'];
	$message = $options['m'];
	$response = WCRequest::updateProgress($tokenid, $progress, $message);
	if($response == TRUE){
		exit(0);
	} else {
		exit(1);
	}
}

function display($str){
	print $str . "\n";
}
?>
