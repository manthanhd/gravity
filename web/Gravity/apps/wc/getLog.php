<?php
	require "helpers/scripts.php";
	require "helpers/WCRequest.php";

	if(!empty($_GET) && isset($_GET['tokenid'])){ 
		if(WCRequest::isTokenValid($_GET['tokenid']) != FALSE){   
			$tokenid = $_GET['tokenid'];
			$logLink = "logs/$tokenid.log";
			$handle = fopen($logLink, "r");
			if ($handle) {
				echo "<div class='table-responsive'>";
				echo "<table class='table'>";
				$count = 1;
				    while (($line = fgets($handle)) !== false) {
							echo "<tr>";
							echo "<td>$count</td>";
							echo "<td>$line</td>";
							echo "</tr>";
							$count++;
						}
				echo "</table>";
				echo "</div>";
			} else {
				    // error opening the file.
			}
		} else {
			echo "Token is invalid.";
		}
	}
?>
