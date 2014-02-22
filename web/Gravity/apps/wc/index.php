<html>
	<head>
		<title>Word Counter</title>
		<?php require "helpers/scripts.php";?>
	</head>

	<body class="container">
		<?php
			require "helpers/header.php";
		?>
		<h2>Word Counter</h2>
		<form role="form" action="process.php" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="textFileUpload">Choose a file to perform word count analysis on:</label>
				<input class="form-control" type="file" id="textFileUpload" name="textFileUpload"/>
				<p class="help-block">File must be in text/ASCII format. Binary files will be rejected.</p>
			</div>
			<input class="btn btn-primary" type="submit" name="submitButton" id="submitButton"/>
		</form>
	</body>
</html>
