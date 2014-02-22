<?php
require_once "helpers/WCRequest.php";
class WCRequestHandler{
	public function __construct(){

	}

	public static function kickOffPendingJobs(){
		$mapper = "/home/hduser/hadoop-sandbox/perl/mapper.pl";
		$reducer = "/home/hduser/hadoop-sandbox/perl/reducer.pl";
		$requests = WCRequest::getNew();
		foreach ($requests as $request){
			$tokenid = $request->tokenid;
			$app_name = $request->app_name;
			$input_file = getcwd() . '/' . $request->input_file;
			$logfile = getcwd() . '/logs/' . $tokenid . '.log';
			$outputDir = getcwd() . "/out/$tokenid";
			$command = "/home/hduser/analyseData.pl -dataset $input_file -outputDir $outputDir -mode stream -mapper $mapper -reducer $reducer > $logfile 2>&1";
			$request->start();
			$rc = system($command);
			if($rc == 0){
				echo "success";
				$request->complete($outputDir);
			} else {
				echo "fail";
			}
		}
	}
}
?>
