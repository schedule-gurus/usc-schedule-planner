<?php
require '../config/config.php';
// echo phpinfo();
error_reporting(E_ALL);

// var_dump($_POST);
// Server side input validation
if(!isset($_POST["inputFname"]) || empty($_POST["inputFname"]) || !isset($_POST["inputLname"]) || empty($_POST["inputLname"]) || !isset($_POST["inputEmail"]) || empty($_POST["inputEmail"]) || !isset($_POST["inputPassword"]) || empty($_POST["inputPassword"]) || !isset($_POST["gradYear"]) || empty($_POST["gradYear"]) || !isset($_POST["inputMajor"]) || empty($_POST["inputMajor"]) || !isset($_POST["inputMajorAbrv"]) || empty($_POST["inputMajorAbrv"])) {
	$error = "Please fill out all required fields.";
}
else {
	// Store user in database
	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}

	// Check if username is already taken (exists in users table)
	$sql_registered = "SELECT * FROM users
	WHERE email = '" . $_POST["inputEmail"] . "';";

	$results_registered = $mysqli->query($sql_registered);
	if(!$results_registered) {
		echo $mysqli->error;
		exit();
	}

	// var_dump($_POST);

	if($results_registered->num_rows > 0) {
		$error = "Email has been already taken. Please choose another one.";
	}
	else {
		$password = hash("sha256", $_POST["inputPassword"]);
		
		// Foreign key stuff
		$sql_major = "SELECT ID FROM major
		WHERE abrv = '" . $_POST["inputMajorAbrv"] . "';"; 
		$results_major = $mysqli->query($sql_major);
		if(!$results_major) {
			echo $mysqli->error;
			exit();
		}

		// Insert new major into database
		if(mysqli_num_rows($results_major) == 0) {
			$sql_major_input = "INSERT INTO major(name, abrv) 
			VALUES('" . $_POST["inputMajor"] . "', '" . $_POST["inputMajorAbrv"] . "');";
			$results_major_insert = $mysqli->query($sql_major_input);
			if(!$results_major_insert) {
				echo $mysqli->error;
				exit();
			}
		}
		
		// query database for new id (lowkey repetitive)
		$results_major = $mysqli->query($sql_major);
		if(!$results_major) {
			echo $mysqli->error;
			exit();
		}

		$major_final = NULL;
		while ($row = $results_major->fetch_assoc()) {
		    $major_final = $row['ID'];
		}

		$gradYear = $_POST["gradYear"] + 0;

		$sql = "INSERT INTO users(email, password, gradYear, major, first, last) 
		VALUES('" . $_POST["inputEmail"] . "', '" . $password . "', " . $gradYear . ", '" . $major_final . "', '" . $_POST["inputFname"] . "', '" . $_POST["inputLname"] . "');";

		$results = $mysqli->query($sql);
		if(!$results) {
			echo $mysqli->error;
			exit();
		}
	}

	$mysqli->close();
	
}?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registration Confirmation</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="assets/css/Header-Blue.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
	<style>
		.back {
			padding:10px;
			margin:10px;
		}
		.content {
			padding:10px;
		}
	</style>
</head>
<body>

	<!-- Nav bar -->
	<div>
        <div class="header-blue container-fluid">

            <!-- Put the nav bar in a separate folder for consistency across pages -->
            <?php include '../main/nav.php'; ?>
		

		<div class="row justify-content-center content">

			<?php if ( isset($error) && !empty($error) ) : ?>

					<div class="text-danger">
						<?php echo $error; ?>
					</div>

				<?php else : ?>

					<div class="text-success"><span class="font-italic"><?php echo $_POST["inputFname"]; ?></span>'s account was successfully created.</div>

				<?php endif; ?>


		</div>

		<div class="form-group row justify-content-center">
			<!-- TODO: Update links -->
			<a href="../login-page/login.php" role="button" class="btn btn-primary btn-dark back" id="back">To Login Page</a>
			<a href="register.php" role="button" class="btn btn-primary btn-dark back">Back to Sign Up</a>
			<a href="../main/index.php" role="button" class="btn btn-primary btn-dark back">To Main</a>
		</div>




	</div>

	<script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include '../main/footer.php'; ?>
</body>
</html>