<html>
	<head>
		<title>Registration - Gravity</title>
		<?php require "helpers/scripts.php";?>
		
		<script type="text/javascript">
			$(document).ready(function(){
				var registerButton = $("#registerButton");
				registerButton.click(function(e){
					var passwordTextBox = $("#passwordTextBox");
					var confirmPasswordTextBox = $("#confirmPasswordTextBox");
					var alertBox = $("#alertBox");
					if(passwordTextBox.val() != confirmPasswordTextBox.val()){
						alertBox.text("Passwords do not match.");
						alertBox.show();
					}
					e.preventDefault();
				});
			});
		</script>
	</head>
		
	<body>
		<div class="container">
			<?php
				require "helpers/header.php";
			?>
			<div class="row">
				<hr/>
				<h2 class="text-center">Developer Registration</h2>
				<hr/>
				<div class="col-md-4">
				</div>
				
				<div class="col-md-4">
					<p>
						<em>Standard developers receive unlimited data and processor use.</em>
					</p>
					<p>
						<div id="alertBox" class="alert alert-danger" hidden></div>
					</p>
					<form action="" method="post">
						<label for="fnameTextBox">First Name</label>
						<input type="text" class="form-control" id="fnameTextBox" name="fnameTextBox"/>
						<label for="lnameTextBox">Last Name</label>
						<input type="text" class="form-control" id="lnameTextBox" name="lnameTextBox"/>
						<label for="emailTextBox">Email Address</label>
						<input type="text" class="form-control" id="emailTextBox" data="email" name="emailTextBox"/>
						<label for="passwordTextBox">Password</label>
						<input type="text" class="form-control" id="passwordTextBox" name="passwordTextBox"/>
						<label for="confirmPasswordTextBox">Confirm Password</label>
						<input type="text" class="form-control" id="confirmPasswordTextBox" name="confirmPasswordTextBox"/>
						<br/>
						<input type="submit" id="registerButton" name="registerButton" class="btn btn-success" style="float:right;" value="Register"/>
						<br/>
					</form>
				</div>
				
			</div>	
		</div>
	</body>
</html>
