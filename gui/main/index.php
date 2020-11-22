<?php
	require '../config/config.php';
?>

<!DOCTYPE html>
<html>
<head>
	<title>Home Page</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login Page</title>
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
    
    <style>
    	.col {
			text-align: center;
			width:auto;
		}

		h2 {
			color:maroon;
			font-weight: bold;
			text-align: center;
		}
		h3 {
			text-align: center;
			padding-bottom: 20px;
		}
		button {
			width:100%;
			/*height: 110%;*/
			margin:10px;
			/*padding:15px;*/
		}

		#footer {
			position: absolute;
			bottom: 0;
		}
		
    </style>
</head>
<body>
	<div>
        <div class="header-blue">

            <!-- Put the nav bar in a separate folder for consistency across pages -->
            <?php include 'nav.php'; ?>


        <div class="container hero">
            <div class="row">
            	<div class="col-12">
					<h2>Welcome to USC Schedule Helper!</h2>
				</div>
				<div class="col-12">
					<h3>Navigate to a Page:</h3>
				</div>
                <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <div class="col col-6 p-2 text-center my-auto">

                	<!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
				<a href="../login-page/login.php"><button class="btn btn-lg btn-dark btn-primary">Log In</button></a>
			</div>
			<div class="col col-6 p-2 text-center my-auto">

				<a href="../login-page/register.php"><button class="btn btn-lg btn-dark btn-primary">Sign Up</button></a>
			<!-- </div> -->
            </div>
                <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <div class="col col-6 p-2 text-center my-auto">

                	<!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
				<a href="../schedule/schedule.php"><button class="btn btn-lg btn-dark btn-primary">Scheduling</button></a>
			</div>
			<div class="col col-6 p-2 text-center my-auto">

				<a href="../social/social.php"><button class="btn btn-lg btn-dark btn-primary">Social</button></a>
            </div>
            <!-- <div class="col col-6 p-2 text-center my-auto"> -->
            	<!-- Todo: might need to make github public -->
				<!-- <a href="activity.php" target=""><button class="btn btn-lg btn-dark btn-primary">Active Users</button></a> -->
            <!-- </div> -->
            <div class="col col-12 p-2 text-center my-auto">
            	<!-- Todo: might need to make github public -->
				<a href="https://github.com/schedule-gurus" target="_blank"><button class="red btn btn-lg btn-dark btn-primary">Project GitHub</button></a>
            </div>

        <!-- </div> -->
    </div>
    </div>
    </div>
</div>
    
    <script src="../login-page/assets/js/jquery.min.js"></script>
    <script src="../login-page/assets/bootstrap/js/bootstrap.min.js"></script>
    <?php include 'footer.php'; ?>
</body>
</html>