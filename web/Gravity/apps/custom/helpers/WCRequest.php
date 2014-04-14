<?php
	require_once "MySQLConnection.php";
	class WCRequest{
		public $tokenid, $app_name, $input_file, $status, $progress_percent, $output_file, $output_file_hdfs, $expired, $requested_on, $started_on, $completed_on;
		public $prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime;
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

		public function fillTime($prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime){
			$this->prepTime = $prepTime;
			$this->hdfsInTime = $hdfsInTime;
			$this->mapreduceTime = $mapreduceTime;
			$this->hdfsOutTime = $hdfsOutTime;
			$this->cleanupTime = $cleanupTime;
		}

		public static function updateProgress($tokenid, $progress, $message){
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "UPDATE webdb.Request SET status='$message', progress_percent=$progress WHERE tokenid='$tokenid';";
			$result = mysql_query($query) or die(mysql_error());
			$response = FALSE;
			if($result){
				$response = TRUE;
			}
			$conn->disconnect();
			return $response;
		}

		public static function updateTime($tokenid, $prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime){
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "UPDATE webdb.Request SET prepTime='$prepTime', hdfsInTime='$hdfsInTime', mapreduceTime='$mapreduceTime', hdfsOutTime='$hdfsOutTime', cleanupTime='$cleanupTime' WHERE tokenid='$tokenid';";
			$result = mysql_query($query) or die(mysql_error());
			$response = FALSE;
			if($result){
				$response = TRUE;
			}
			$conn->disconnect();
			return $response;
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

		/*public static function updateTime($tokenid, $prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime){
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "UPDATE webdb.Request SET prepTime='$prepTime', hdfsInTime='$hdfsInTime', mapreduceTime='$mapreduceTime', hdfsOutTime='$hdfsOutTime', cleanupTime='$cleanupTime' WHERE tokenid='$tokenid';";
			$result = mysql_query($query) or die(mysql_error());
			$response = FALSE;
			if($result){
				$response = TRUE;
			}
			$conn->disconnect();
			return $response;
		}
		 */
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

		public static function get($tokenid){
			$conn = new MySQLConnection();
			$conn->connect();
			$query = "SELECT * FROM webdb.Request WHERE tokenid='$tokenid';";
			$res = mysql_query($query) or die(mysql_error());
			$row = mysql_fetch_array($res, MYSQL_ASSOC) or die(mysql_error());
			$request = new WCRequest();
			$request->fill($row['tokenid'], $row['app_name'], $row['input_file'], $row['status'], $row['progress_percent'], $row['output_file'], $row['output_file_hdfs'], $row['expired'], $row['requested_on'], $row['started_on'], $row['completed_on']);
			$request->fillTime($row['prepTime'], $row['hdfsInTime'], $row['mapreduceTime'], $row['hdfsOutTime'], $row['cleanupTime']);
			$conn->disconnect();
			return $request;
		}

		public static function cleanupRequests($dayCount){
			if(!$dayCount){
				return FALSE;
			} elseif (!is_int($dayCount)){
				return FALSE;
			}
			$conn = new MySQLConnection();
			$conn->connect();
			$response = FALSE;
			$query = "INSERT INTO webdb.Request_Archived SELECT * FROM webdb.Request WHERE completed_on <= DATE_SUB(CURDATE(), INTERVAL $dayCount DAY) AND expired = 0;";
			$result = mysql_query($query);
			if($result){
				$query = "DELETE FROM webdb.Request WHERE completed_on <= DATE_SUB(CURDATE(), INTERVAL $dayCount DAY) AND expired = 0;";
				$request = mysql_query($query);
				$response = TRUE;
			}
			$conn->disconnect();
			return $response;
			
		}

		public static function getNew(){
			$conn = new MySQLConnection();
			$conn->connect();
			$requests = array();
			$query = "SELECT * FROM webdb.Request WHERE completed_on IS NULL AND app_name='wc';";
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
