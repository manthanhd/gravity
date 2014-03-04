<?php
require_once "helpers/Request.php";
require_once "helpers/MySQLConnection.php";

class RequestHandler{
	public function __construct(){

	}

	public static function kickOffPendingJobs($mode){
		# $mapper = "/home/hduser/hadoop-sandbox/perl/mapper.pl";
		# $reducer = "/home/hduser/hadoop-sandbox/perl/reducer.pl";
		RequestHandler::llog($mode, "Looking for new requests...");
		$requests = Request::getNew("custom");
		foreach ($requests as $request){
			$tokenid = $request->tokenid;
			RequestHandler::llog($mode, "Found new request. Processing " . $tokenid);
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "SELECT * FROM webdb.App_Custom WHERE tokenid='$tokenid';";
			$result = mysql_query($query) or die(mysql_error());
			$row = mysql_fetch_array($result,MYSQL_ASSOC);
			$jarFile = $row['jar_file'];
			$mainClass = $row['main_class'];
			$jreVersion = $row['jre_version'];
			$conn->disconnect();
			$app_name = $request->app_name;
			$input_file = getcwd() . '/' . $request->input_file;
			$logfile = 'logs/' . $tokenid . '.log';
			$outputDir = "out/$tokenid";
			$command = "/home/hduser/analyseData.pl -dataset $input_file -outputDir $outputDir -mode native -hadoopJar $jarFile -hadoopClass $mainClass -package -tokenid $tokenid > $logfile 2>&1";
			$request->start();
			RequestHandler::llog($mode, "Started processing $tokenid");
			$rc = system($command);
			if($rc == 0){
				echo "success";
				RequestHandler::llog($mode, "Processing for $tokenid was successful.");
				$request->complete($outputDir . ".tar.gz");
			} else {
				echo "fail";
				RequestHandler::llog($mode, "Processing failed for $tokenid. Moving on.");
			}
		}
	}

	private static function llog($mode, $str){
		if(isset($mode)){
			if($mode == "debug"){
				echo date("Y-m-d H:i:s") . "\t$str\n";
			}
		}
	}
}
?>
