<?php
	require "helpers/WCRequest.php";
	$invalidToken = FALSE;
	$expired = FALSE;
	$request = NULL;
	if(!empty($_GET) && isset($_GET['tokenid'])){
		if(WCRequest::isTokenValid($_GET['tokenid']) == FALSE){
			$invalidToken = TRUE;
			echo "<html><body><h1>Token is Invalid</h1></body></html>";
		} else {
			$request = WCRequest::get($_GET['tokenid']);
			if($request->expired == 1){
				echo "<html><body><h1>Your token has expired. Output for this token is not available any more.</h1></body></html>";
			} else {
				$file_path = $request->output_file;
				if(file_exists($file_path)){
					#header('Content-Type: application/x-gzip');
					header("Pragma: public");
					header("Expires: 0");
					header('Cache-Control: no-store, no-cache, must-revalidate');
					header('Cache-Control: pre-check=0, post-check=0, max-age=0', false);
					header("Content-Length: " . filesize($file_path));
					header('Content-Type: application/x-download');
					header('Content-Disposition: attachment; filename="'. basename($file_path) .'"');
					header('Content-Transfer-Encoding: binary');
					if ($file = fopen($file_path, 'rb'))
					{
						while(!feof($file) and (connection_status()==0))
						{
							 print(fread($file, filesize($file_path)));
							 flush();
						}
						fclose($file);
					}
				} else {
					echo $file_path . "File does not exist.";
				}
			}
		}
	}
?>
