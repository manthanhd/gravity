<html>
	<head>
		<title>Orbit</title>
		<?php require "helpers/scripts.php";?>
	</head>

	<body class="container">
		<?php
			require "helpers/header.php";
		?>
		<h2>Orbit</h2>
		<form role="form" action="process.php" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="jarFileUpload">Choose a jar file to upload:</label>
				<input class="form-control" type="file" id="jarFileUpload" name="jarFileUpload"/>
				<p class="help-block">Only executable hadoop-compatible jar files are allowed.</p>
			</div>
			<div class="form-group">
				<label for="mainClassText">Specify main executable class of the jar file:</label>
				<input class="form-control" type="text" id="mainClassText" name="mainClassText"/>
			</div>
			<div class="form-group">
				<label for="textFileUpload">Choose a text file to upload. This is your input data set:</label>
				<input class="form-control" type="file" id="textFileUpload" name="textFileUpload"/>
				<p class="help-block">File must be in ASCII/text format. Binaries will be rejected.</p>
			</div>
			<div class="form-group">
				<label for="jdkRadio">Select a JRE to use: </label>
				<input type="radio" name="jdkRadio" id="jdkRadio" value="oracle7" checked='checked'> Oracle 7&nbsp;&nbsp;</input>
				<input type="radio" name="jdkRadio" id="jdkRadio" value="oracle6"> Oracle 6&nbsp;&nbsp;</input>
			</div>
			<input class="btn btn-primary" type="submit" name="submitButton" id="submitButton"/>
		</form>
	</body>
</html>
