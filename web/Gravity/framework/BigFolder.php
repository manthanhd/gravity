<?php
	define('BIG_ASCII', 'ASCII');
	define('BIG_BINARY', 'BINARY');
	define('BIG_ARCHIVE', 'ARCHIVE');
	/**
	 * BigFile class
	 */
	class BigFile {
		
		public $folder_path = null, $type = null;
		
		function __construct($folder_path, $type) {
			$this->folder_path = $folder_path;
			$this->type = $type;
		}
	}
	
?>