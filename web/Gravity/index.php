<html>
	<head>
		<title>Gravity: Apps for Big Data Analysis</title>
		<?php require "helpers/scripts.php";?>
	</head>
		
	<body>
		<div class="container">
			<?php
				require "helpers/header.php";
			?>
			<div class="row">
				<h2 class="text-center">Available Apps</h2>
				<div class="col-sm-6 col-md-4">
					<div class="thumbnail">
						<div class="caption">
							<h3>Word Counter</h3>
							<p>This app counts number of words within a text file. Uses our Hadoop powered cluster for computation.</p>
							<p><a href="apps/wc/index.php" class="btn btn-primary" role="button">Launch app</a> <a href="apps/wc/more.php" class="btn btn-default" role="button">More info</a></p>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</body>
</html>
