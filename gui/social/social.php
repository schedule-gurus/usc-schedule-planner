<?php
    require '../config/config.php';
    $logged_in = false;
    if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {
        $logged_in = false;
    } else {
        $logged_in = true;
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
    <title>Social</title>
    <style>
        .col {
            text-align: center;
            width:auto;
        }
        button {
            width:100%;
            /*height: 110%;*/
            margin:10px;
            /*padding:15px;*/
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
                    <h2>Social</h2>
                </div>
                <div class="col-12">
                    <h3>Navigate to a Page:</h3>
                </div>

                <div class="col col-6 p-2 text-center my-auto">

                    <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <a href="my-friends.php"><button id="friends" class="btn btn-lg btn-dark btn-primary">My Friends</button></a>
            </div>
            <div class="col col-6 p-2 text-center my-auto">

                    <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <a href="search.php"><button id="add" class="btn btn-lg btn-dark btn-primary">Find Friends</button></a>
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

<script>
    // You must be logged in to add an article
    var x = "<?php echo $logged_in ?>";
    //console.log(x);

    document.querySelector("#add").onclick = function(event) {
        if(x == "") {
            event.preventDefault();
            alert("You must be logged in to find friends.");
        }
        else {
            location.replace("add-friends.php");
        }
    };

    document.querySelector("#friends").onclick = function(event) {
        if(x == "") {
            event.preventDefault();
            alert("You must be logged in to see your friends.");
        }
        else {
            location.replace("my-friends.php");
        }
    };



</script>