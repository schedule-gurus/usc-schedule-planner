<?php

require '../config/config.php';
// error_reporting(E_ALL);

if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {

} else {
    header("Location: ../main/index.php");
}



?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Register Page</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="assets/css/Header-Blue.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
    <style>
    	.form-register input {
    		margin-top: 10px;
    	}
    	label {
    		margin-top:10px;
    		margin-left: 5px;
    	}
    	#inputMajorAbrv {
    		margin-bottom: 10px;
    	}
        
    </style>
</head>

<body>
    <div>
        <div class="header-blue">

            <!-- Put the nav bar in a separate folder for consistency across pages -->
            <?php include '../main/nav.php'; ?>


        <div class="container hero">
            <div class="row">
                <div class="col">
                    <div class="login-card"><img class="profile-img-card" src="assets/img/avatar_2x.png">
                        <p class="profile-name-card"> </p>
                        <form class="form-register" id="register" action="register-confirmation.php" method="POST">
                        	<span class="reauth-email"> </span>
                        	<input class="form-control" type="name" id="inputFname" name="inputFname" required="" placeholder="First Name" autofocus="">
                        	<input class="form-control" type="name" id="inputLname" name="inputLname" required="" placeholder="Last Name" autofocus="">
                        	<input class="form-control" type="email" id="inputEmail" name="inputEmail" required="" placeholder="Email address" autofocus="">
                        	<input class="form-control" type="password" id="inputPassword" name="inputPassword" required="" placeholder="Password" autofocus="" />
                        	<label for="gradYear">Graduation Year:</label>
                        	<select name="gradYear" required="" id="gradYear"></select>
                        	<input class="form-control" type="major" id="inputMajor" name="inputMajor" required="" placeholder="Major Name" autofocus="" />
                        	<input class="form-control" name="inputMajorAbrv" type="major" id="inputMajorAbrv" required="" placeholder="Major Abbreviation (CSCI, EE...)" autofocus="" />
                            


                        <button class="btn btn-primary btn-block btn-lg btn-signin" type="submit">Sign Up</button>
                    </form></div>
            </div>
        </div>
    </div>
    </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include '../main/footer.php'; ?>
</body>

</html>

<script>
var start = 2018;
// var end = new Date().getFullYear();
var end = 2030;
var options = "";
for(var year = start ; year <=end; year++){
  options += "<option>"+ year +"</option>";
}
document.getElementById("gradYear").innerHTML = options;

// Validate user input
document.querySelector("#register").onsubmit = function(event) {
	if(document.querySelector("#inputFname").value.trim().length == 0) {
		event.preventDefault();
		alert("First name cannot be empty!");
		document.querySelector("#inputFname").value = "";
	} 
	if(document.querySelector("#inputLname").value.trim().length == 0) {
		event.preventDefault();
		alert("Last name cannot be empty!");
		document.querySelector("#inputLname").value = "";
	} 

	if(document.querySelector("#inputEmail").value.trim().length == 0) {
		event.preventDefault();
		alert("Email cannot be empty!");
		document.querySelector("#inputEmail").value = "";
	} 

	if(document.querySelector("#password").value.trim().length == 0) {
		event.preventDefault();
		alert("Password cannot be empty!");
		document.querySelector("#password").value = "";
	} 

	if(document.querySelector("#inputMajor").value.trim().length == 0) {
		event.preventDefault();
		alert("Major cannot be empty!");
		document.querySelector("#inputMajor").value = "";
	} 

	if(document.querySelector("#inputMajorAbrv").value.trim().length == 0) {
		event.preventDefault();
		alert("Major abbreviation cannot be empty!");
		document.querySelector("#inputMajorAbrv").value = "";
	} 
};

</script>
