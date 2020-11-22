

<!-- TODO: check if friend is yourself, check if you already have friend added, add friend -->
<?php
require '../config/config.php';
// if you're not logged in
if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
	header('Location: ../main/index.php');
}

// var_dump($_POST);

// if the fields aren't set
if(!isset($_GET["name"]) || empty($_GET["name"]) || !isset($_GET["id"]) || empty($_GET["id"])) {
	$error = "Error: GET fields not set.";
}
// If you're trying to add yourself as a friend
else if($_SESSION['id'] == $_GET['id']) {
	$error = "You cannot add yourself as a friend.";
}
else {

	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}
	$mysqli->set_charset('utf8');

	// If you're already friends with this person

	$sql = "SELECT * FROM friends
	WHERE userID = " . $_SESSION['id'] . " AND 
	friendID = " . $_GET['id'] . ";";
	$results = $mysqli->query($sql);
	if(!$results) {
		echo $mysqli->error;
		exit();
	}


	if(mysqli_num_rows($results) == 0) {
		$sql2 = "INSERT INTO friends(userID, friendID) 
		VALUES(" . $_SESSION['id'] . ", " . $_GET['id'] . ");";
		$results2 = $mysqli->query($sql2);
		if(!$results2) {
			echo $mysqli->error;
			exit();
		}
	} else {
		$error = $_GET['name'] . " is already your friend!";
	}


	$mysqli->close();
}



?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registration Confirmation</title>
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
	<title>Add Confirmation</title>
	<style>
		.btn {
			padding:10px;
			margin:10px;
		}
	</style>
</head>
<body>
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

					<div class="text-success"><span class="font-italic"><?php echo $_GET["name"]; ?></span> was successfully added as a friend.</div>

				<?php endif; ?>


		</div>

		<div class="form-group row justify-content-center">
			<a href="../main/index.php" role="button" class="btn btn-primary btn-dark back" id="back">To Home Page</a>
			<a href="social.php" role="button" class="btn btn-primary btn-dark back">Back to Social</a>
		</div>




	</div>
</div>

	<script src="../login-page/assets/js/jquery.min.js"></script>
    <script src="../login-page/assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include '../main/footer.php'; ?>
</body>
</html>