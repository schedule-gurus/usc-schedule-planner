<?php
	require '../config/config.php';

	$logged_in = false;
	if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {
		$logged_in = false;
	} else {
		$logged_in = true;
	}
	if(!$logged_in) {
		echo "<script type='text/javascript'> document.location = '../main/index.php'; </script>";
    	exit();
	}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login Page</title>
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://fonts.googleapis.com/css2?family=Bitter:wght@400;700&display=swap" rel="stylesheet">
	<title>Search</title>
	<style>
		#text {
			background-color:maroon;
			color:white;
			margin:10px;
			padding:10px;
			border: 2px solid black;
			border-radius:20px;
			font-size:1.2em;
			text-align: center;
			width:80%;
			margin-left: auto;
			margin-right: auto;
			margin-bottom: 25px;
		}
		h2 {
			color:maroon;
			font-weight: bold;
			text-align: center;
		}
		.form button {
			width:10%;
			padding:10px;
			font-size: 20px;
			border:1px solid black;
			border-left: none;
			cursor:pointer;
			float:left;
			background-color: black;
			color:white;
			border-radius: 10px;
		}
		* {
			box-sizing: border-box;
		}
		.form::after {
			clear:both;
		}
		.form input {
			border:2px solid black;
			border-radius: 10px;
			font-size:20px;
			padding:10px;
			/*float:left;*/
			width:90%;
			float:left;
		}
		p {
			display:block;
			padding-top:5px;
		}
		form {
			padding-top:10px;
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
            <div class="col-12">
                    <h2>Search for People</h2>
                </div>
            </div>

            <div class="row">
			<div id="search" class="col-12 my-auto">
				<form class="form" action="search-results.php" method="GET">
					<!-- <div class="col-10 my-auto p-2 justify-content-center"> -->
						<input type="text" placeholder="Search for a user by name..." name="search">
					<!-- </div> -->
					<!-- <div class="col-2 my-auto p-2 justify-content-center"> -->
						<button type="submit"><i class="fa fa-search"></i></button>
					<!-- </div> -->
				</form>
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