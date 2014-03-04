<?php
	define('BIG_ASCII', 'ASCII');
	define('BIG_BINARY', 'BINARY');
	define('BIG_ARCHIVE', 'ARCHIVE');
	/**
	 * BigFile class
	 */
	class BigFile {
		
		public $file_path = null, $type = null, $extension = null;
		
		function __construct($file_path, $type) {
			$this->file_path = $file_path;
			$this->type = $type;
			$this->extension = pathinfo($file_path, PATHINFO_EXTENSION);
		}
	}
	
?>