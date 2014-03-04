<?php
	define('ROOT', '/var/www/Gravity');
	
	/**
	 * 
	 */
	class BigApp {
		
		public $app_name, $app_shortname, $input, $jar, $jarMainClass;
		
		function __construct($app_name, $app_shortname, $input, $jar, $jarMainClass) {
			$this->app_name = $app_name;
			$this->app_shortname = $app_shortname;
			$this->input = $input;
			$this->jar = $jar;
			$this->jarMainClass = $jarMainClass;
		}
	}
	
?>