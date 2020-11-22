<?php
require '../config/config.php';
// if you're not logged in
if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
	header('Location: ../main/index.php');
}

$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
if($mysqli->connect_errno) {
	echo $mysqli->connect_error;
	exit();
}
$mysqli->set_charset('utf8');


if(!isset($_GET["id"]) || empty($_GET["id"]) || !isset($_GET["name"]) || empty($_GET["name"])) {
	$error = "GET error - friends row ID or name is invalid.";
}
if(!isset($error) && empty($error)) {
	$sql = "DELETE FROM friends
	WHERE ID = " . $_GET['id'] . ";";
	// var_dump($sql);
	$results = $mysqli->query($sql) or die($mysqli->error);
	if ( !$results ) {
		echo $mysqli->error;
		exit();
	}

	// $row = $results->fetch_assoc();
}

$mysqli->close();
?>

<!DOCTYPE html>
<html>
<head><meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../styles.css">
	<title>Remove Confirmation</title>
	<style>
		.text-success, .text-danger {
			text-align: center;
		}
	</style>
</head>
<body>
<div>
    <div class="header-blue">

    	<?php include '../main/nav.php'; ?>




        <div class="container hero">
        	<div class="row">
			<div class="col-12">
				<a href="social.php" role="button" class="btn btn-primary btn-dark" id="back">Back to Social</a>
			</div>
		</div>

		<div class="row">
			<div class="col-12">

			<?php if ( isset($error) && !empty($error) ) : ?>

					<div class="text-danger">
						<?php echo $error; ?>
					</div>

				<?php else : ?>

					<div class="text-success"><span class="font-italic"><?php echo $_GET["name"]; ?></span> was successfully removed from your friends.</div>

				<?php endif; ?>
			</div>


		</div>




    
    </div>
    </div>
	</div>
    <script src="../login-page/assets/js/jquery.min.js"></script>
    <script src="../login-page/assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include '../main/footer.php'; ?>
</body>
</html>