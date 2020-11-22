<?php
// var_dump($_POST);
require '../config/config.php';
// If no user is logged in, do the usual things. Otherwise, redirect user out of this page.
if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {
    // Check if user has entered in username/password
    if(isset($_POST["email"]) && isset($_POST["password"]) ) {
        //  User did not enter username/password, it's blank
        if(empty($_POST['email']) || empty($_POST['password'])) {
            $error = "Please enter username and password.";
        }
        else {
            // User did enter username/password but need to check if the username/password combination is correct
            $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

            if($mysqli->connect_errno) {
                echo $mysqli->connect_error;
                exit();
            }

            // Hash whatever user typed in for password, then compare this to the hashed password in the DB
            // $passwordInput = hash("sha256", $_POST["password"]);
            $passwordInput = hash("sha256", $_POST["password"]);
            // echo $passwordInput;

            $sql = "SELECT * FROM users
            WHERE email = '" . $_POST["email"] . "' AND password = '" . $passwordInput . "';";

            $results = $mysqli->query($sql);

            if(!$results) {
                echo $mysqli->error;
                exit();
            }

            // If we get 1 result back, means username/password combination is correct
            if($results->num_rows > 0) {
                // echo "here";
                $name = "";
                $id = -1;
                while ($row = $results->fetch_assoc()) {
                    $name = $row['first'];
                    $id = $row['ID'];
                }

                // Set session variables to remember this user
                $_SESSION["name"] = $name;
                $_SESSION["id"] = $id;
                $_SESSION["logged_in"] = true;

                // Success! Redirect user to the home page
                // Todo: Change location
                // header("Location: ../main/index.php");
                echo "<script type='text/javascript'> document.location = '../main/index.php'; </script>";
                exit();
            }
            else {
                $error = "Invalid username or password.";
            }
        }
    }
} ?>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login Page</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="assets/css/Header-Blue.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="../styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
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
                        <span id="error" class="text-danger">
                        <?php 
                            if(isset($error) && !empty($error)) {
                                echo $error;
                            }
                        ?>
                    </span>
                        <form class="form-signin" name="login" id="sign-in" action="login.php" method="POST"><span class="reauth-email"></span>
                        <input class="form-control" type="email" id="inputEmail" name="email" required="" placeholder="Email address" autofocus="">
                        <input class="form-control" name="password" type="password" id="inputPassword" required="" placeholder="Password">
                            <div
                                class="checkbox">
                                <!-- Commented this out bc idk how to make it remember logins -->
                                <!-- <div class="form-check"><input class="form-check-input" type="checkbox" id="formCheck-1"><label class="form-check-label" for="formCheck-1">Remember me</label></div> -->
                    </div><button class="btn btn-primary btn-block btn-lg btn-signin" type="submit">Sign in</button></form>
                    <!-- <a class="forgot-password" href="#">Forgot your password?</a> -->
                </div>
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
