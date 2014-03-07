<?php
if(isset($_GET['app_name']) && isset($_GET['tokenid'])){
	$app_name = $_GET['app_name'];
	$tokenid = $_GET['tokenid'];
	$dir = getcwd() . '/out/' . $tokenid . '.stats';
	if(is_dir($dir)){
		$hosts = array('heisenberg', 'tungsten', 'titanium', 'silicon');
		$filenames = array();
		foreach($hosts as $host){
			array_push($filenames, $dir . '/' . $host . '.mem.stat');
		}
		$count = 0;
		$files = count($filenames);
		$jsonString = '[ ';
		foreach($filenames as $filename){
			// process each file.
			// open each file.
			if(file_exists($filename)){
				$curr_line = 0;
				$linecount = getLineCount($filename);
				if($linecount == 0){
					die("failed to get line count.");
				}
				$handle = fopen($filename, "r");
				if ($handle) {
					while (($line = fgets($handle)) !== false) {
						// process the line read.
						$jsonString .= $line;
						# if($count != ($files - 1)){

						$jsonString .= ', ';

						# if($curr_line < ($linecount) || $count != ($files - 1)){
						# 	$jsonString .= ', ';
						# }
						#} else {
						#	if($count $curr_line < ($linecount)){
						#		$jsonString .= ', ';
						#	}
						#}
						$curr_line++;
					}
				} else {
						// error opening the file.
				}
				fclose($handle);
			}
			$count++;
		}
		$jsonString = substr($jsonString, 0, count($jsonString) - 3);
		$jsonString .= ']';
		echo $jsonString;
	}
} else {
	echo "{ \"error\":\"Parameters not set. app_name and tokenid\" }";
}

function getLineCount($filename){
	$count = 0;
	if(file_exists($filename)){
		$handle = fopen($filename, "r");
		if($handle){
			while(($line = fgets($handle)) !== false){
				$count++;
			}
		}
		fclose($handle);
	}
	return $count;
}
?>
