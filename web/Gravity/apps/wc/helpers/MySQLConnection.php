<?php
class MySQLConnection {
		//private $conn;
		private $username = "webuser";
		private $password = "straightwin1";
		private $db_name = "webdb";
		private $hostname = "localhost";
		public $link;
					
		public function __construct(){
								
		}
					
		public function connect(){
			if($this->link == NULL){
				$this->link = mysql_connect($this->hostname, $this->username, $this->password) or die('Could not connect: ' . mysql_error());
				mysql_select_db($this->db_name) or die ("Could not select database " . mysql_error());
			}
		}
					
		public function disconnect(){
			if($this->link != NULL){
				if(is_resource($this->link) && get_resource_type($this->link) === 'mysql link'){
					mysql_close($this->link);
					$this->link == NULL;
				}
			}
			# else the connection has already been closed.
		}
					
		public function __destruct(){
			#mysql_close($this->link);
		}
}
?>
