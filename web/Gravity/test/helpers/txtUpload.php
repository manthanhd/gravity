<?php
function upload($control, $_FILES){
	$allowedExts = array("txt");
	$temp = explode(".", $_FILES[$control]["name"]);
	$extension = end($temp);
	if (($_FILES[$control]["type"] == "text/plain") || in_array($extension, $allowedExts))
	{
		if ($_FILES[$control]["error"] > 0) {
			echo "There's been an error uploading file.";
		} else {
			$hour = date('G');
			$minute = date('i');
			$seconds = date('s');
			$month = date('n');
			$day = date('j');
			$year = date('Y');
			$date = mktime($hour,$minute,0,$month,$day,$year);
			$filename = "user_upload/" . $date . '-' . $_FILES[$control]["name"];
			if (file_exists($filename)) {
			} else {
				move_uploaded_file($_FILES[$control]["tmp_name"], $filename);
				return $filename;
			}
		}
	} else {
		echo "Invalid file type.";
	}
}
?>
