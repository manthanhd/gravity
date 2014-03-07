<?php
function upload($control, $_FILES, $tokenid, $outDirectory) {
	$allowedExts = array("txt");
	$temp = explode(".", $_FILES[$control]["name"]);
	$extension = end($temp);
	if (($_FILES[$control]["type"] == "text/plain")) {
		if ($_FILES[$control]["error"] > 0) {
			# echo "There's been an error uploading file.";
		} else {
			$filename = "$outDirectory/$tokenid.$extension";
			if (file_exists($filename)) {
			} else {
				if(move_uploaded_file($_FILES[$control]["tmp_name"], $filename) == TRUE){
					# echo "Uploaded to $filename";
					return $filename;
				}
				return null;
			}
		}
	} else {
		echo "Invalid file type.";
	}
	return null;
}

function uploadJar($control, $_FILES, $tokenid, $outDirectory) {
	$allowedExts = array("jar");
	$temp = explode(".", $_FILES[$control]["name"]);
	$extension = end($temp);
	# echo "Extension of this file is $extension.";
	if (($_FILES[$control]["type"] == "application/octet-stream")) {
		if ($_FILES[$control]["error"] > 0) {
			# echo "There's been an error uploading file." . $_FILES[$control]['name'];
		} else {
			$filename = "$outDirectory/$tokenid.$extension";
			if (file_exists($filename)) {
				echo "An internal error has occurred while uploading this file as $filename already exists. Please try uploading it again.<br/>";
				return null;
			} else {
				if (move_uploaded_file($_FILES[$control]["tmp_name"], $filename) == TRUE) {
					# echo "Uploaded to $filename";
					return $filename;
				}
				return null;
			}
		}
	} else {
		echo $_FILES[$control]['name'] . " has invalid file type.<br/>";
		return null;
	}
	return null;
}
?>
