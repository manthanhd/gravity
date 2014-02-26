<?php
require_once "helpers/WCRequest.php";
class WCRequestHandler{
	public function __construct(){

	}

	public static function kickOffPendingJobs($mode){
		$mapper = "/home/hduser/hadoop-sandbox/perl/mapper.pl";
		$reducer = "/home/hduser/hadoop-sandbox/perl/reducer.pl";
		WCRequestHandler::llog($mode, "Looking for new requests...");
		$requests = WCRequest::getNew();
		foreach ($requests as $request){
			$tokenid = $request->tokenid;
			WCRequestHandler::llog($mode, "Found new request. Processing " . $tokenid);
			$app_name = $request->app_name;
			$input_file = getcwd() . '/' . $request->input_file;
			$logfile = getcwd() . '/logs/' . $tokenid . '.log';
			$outputDir = getcwd() . "/out/$tokenid";
			$command = "/home/hduser/analyseData.pl -dataset $input_file -outputDir $outputDir -mode stream -mapper $mapper -reducer $reducer -package > $logfile 2>&1";
			$request->start();
			WCRequestHandler::llog($mode, "Started processing $tokenid");
			$rc = system($command);
			if($rc == 0){
				echo "success";
				WCRequestHandler::llog($mode, "Processing for $tokenid was successful.");
				$request->complete($outputDir . ".tar.gz");
			} else {
				echo "fail";
				WCRequestHandler::llog($mode, "Processing failed for $tokenid. Moving on.");
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
