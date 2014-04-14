<?php
require "/var/www/Gravity/helpers/Request.php";

$options = "t:p:i:m:o:c:";
$options = getopt($options);
if(isset($options['t']) && isset($options['p']) && isset($options['i']) && isset($options['m']) && isset($options['o']) && isset($options['c'])){
	$tokenid = $options['t'];
	$prepTime = $options['p'];
	$hdfsInTime = $options['i'];
	$mapreduceTime = $options['m'];
	$hdfsOutTime = $options['o'];
	$cleanupTime = $options['c'];

	display("Asking Request helper to update time.");
	$response = Request::updateTime($tokenid, $prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime);
	if($response == TRUE){
		display("Request successful.");
		exit(0);
	} else {
		display("Request failed.");
		exit(1);
	}
} else {
	display("Incorrect number of requirements supplied.");
}

function display($str){
	print $str . "\n";
}
?>
