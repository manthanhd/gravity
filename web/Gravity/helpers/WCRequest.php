<?php
	require_once "MySQLConnection.php";
	class WCRequest{
		public $tokenid, $app_name, $input_file, $status, $progress_percent, $output_file, $output_file_hdfs, $expired, $requested_on, $started_on, $completed_on;
	 	public function __construct(){

		}

		public function fill($tokenid, $app_name, $input_file, $status, $progress_percent, $output_file, $output_file_hdfs, $expired, $requested_on, $started_on, $completed_on){
			$this->tokenid = $tokenid;
			$this->app_name = $app_name;
			$this->input_file = $input_file;
			$this->status = $status;
			$this->progress_percent = $progress_percent;
			$this->output_file = $output_file;
			$this->output_file_hdfs = $output_file_hdfs;
			$this->expired = $expired;
			$this->requested_on = $requested_on;
			$this->started_on = $started_on;
			$this->completed_on = $completed_on;
		}

		public function start(){
			$this->status = "started";
			$this->progress = 50;
			$this->started_on = date('Y-m-d H:i:s');
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "UPDATE webdb.Request SET status='$this->status', started_on='$this->started_on', progress_percent=$this->progress WHERE tokenid='$this->tokenid';";
			$result = mysql_query($query) or die(mysql_error());
			$conn->disconnect();
		}

		public function complete($output_file){
			$this->status = "completed";
			$this->completed_on = date('Y-m-d H:i:s');
			$this->expired = 0;
			$this->output_file = $output_file;
			$this->progress_percent = 100;
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "UPDATE webdb.Request SET status='$this->status', completed_on='$this->completed_on', expired=$this->expired, output_file='$this->output_file', progress_percent=$this->progress_percent WHERE tokenid='$this->tokenid';";
			$result = mysql_query($query) or die("Failed to update.");
			$conn->disconnect();
		}

		public static function isTokenValid($tokenid){
			$conn = new MySQLConnection();
			$conn->connect();
			$tid = mysql_real_escape_string($tokenid);
			$query = "SELECT * FROM webdb.Request WHERE tokenid='$tid';";
			$res = mysql_query($query) or die(mysql_error());
			if(mysql_num_rows($res)>0){
				$conn->disconnect();
				return TRUE;
			} else {
				$conn->disconnect();
				return FALSE;
			}
		}

		public static function getNew(){
			$conn = new MySQLConnection();
			$conn->connect();
			$requests = array();
			$query = "SELECT * FROM webdb.Request WHERE completed_on IS NULL;";
			$result = mysql_query($query);

			while($row = mysql_fetch_array($result, MYSQL_ASSOC)){
				$tokenid = $row['tokenid'];
				$app_name = $row['app_name'];
				$input_file = $row['input_file'];
				$status = $row['status'];
				$progress_percent = $row['progress_percent'];
				$output_file = $row['output_file'];
				$output_file_hdfs = $row['output_file_hdfs'];
				$expired = $row['expired'];
				$requested_on = $row['requested_on'];
				$started_on = $row['started_on'];
				$completed_on = $row['completed_on'];
				$request = new WCRequest();
				$request->fill($tokenid, $app_name, $input_file, $status, $progress_percent, $output_file, $output_file_hdfs, $expired, $requested_on, $started_on, $completed_on);
				array_push($requests, $request);
			}
			$conn->disconnect();
			return $requests;
		}	
	}
?>
