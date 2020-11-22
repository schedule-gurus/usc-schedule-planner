<?php
require '../config/config.php';
// if you're not logged in
if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
	$error = "You cannot delete a schedule if you're not logged in.";
} else {
	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}
	$mysqli->set_charset('utf8');

	$sql = "DELETE FROM enrolled
	WHERE userID = " . $_SESSION['id'] . ";";
	// var_dump($sql);
	$results = $mysqli->query($sql) or die($mysqli->error);
	if ( !$results ) {
		echo $mysqli->error;
		exit();
	}

	$mysqli->close();
}
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
	<title>Schedule Deletion Confirmation</title>
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
				<a href="schedule.php" role="button" class="btn btn-primary btn-dark" id="back">Back to Schedule</a>
			</div>
		</div>

		<div class="row">
			<div class="col-12">

			<?php if ( isset($error) && !empty($error) ) : ?>

					<div class="text-danger">
						<?php echo $error; ?>
					</div>

				<?php else : ?>

					<div class="text-success"><span class="font-italic">Your schedule was successfully cleared.</div>

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