<?php
    require '../config/config.php';
    $logged_in = false;
    if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {
        $logged_in = false;
    } else {
        $logged_in = true;
    }

    if($logged_in) {
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
        if($mysqli->connect_errno) {
            echo $mysqli->connect_error;
            exit();
        }
        $mysqli->set_charset('utf8');
        $sql = "SELECT * FROM enrolled WHERE userID = " . $_SESSION['id'] . ";";

        $results = $mysqli->query($sql) or die($mysqli->error);
        if ( !$results ) {
            echo $mysqli->error;
            exit();
        }
        if(mysqli_num_rows($results) != 0) {
            $mustDelete = true;
        } else {
            $mustDelete = false;
        }
        $mysqli->close();
    }

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Schedule Page</title>
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../styles.css">
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
        .atag {
            text-decoration: none !important;
            color:white;
        }
        .atag:hover {
            color:white;
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
                    <h2>Schedule</h2>
                </div>
                <div class="col-12">
                    <h3>Navigate to a Page:</h3>
                </div>

                <div class="col col-4 p-2 text-center my-auto">

                <!-- <a href="generator.php"> --><button id="gen" class="btn btn-lg btn-dark btn-primary" onclick="location.href = 'generator.php';">Generate a Schedule</button><!-- </a> -->
            </div>
            <div class="col col-4 p-2 text-center my-auto">

                    <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <?php if ($logged_in) : ?>
                <button id="visualize" class="btn btn-lg btn-dark btn-primary" onclick="location.href = 'visualizer.php?id=<?php echo $_SESSION['id'];?>&user=<?php echo $_SESSION['name'];?>';">Visualize My Schedule</button>
                <?php else : ?>
                    <button id="visualize" class="btn btn-lg btn-dark btn-primary" onclick="location.href = 'visualizer.php';">Visualize My Schedule</button>

                <?php endif; ?>

            </div>
            <div class="col col-4 p-2 text-center my-auto">

                    <!-- <div class="col col-6 col-md-4 col-lg-3 p-2 text-center my-auto d-flex justify-content-center"> -->
                <button id="del" class="btn btn-lg btn-dark btn-primary"onclick="location.href = 'delete-schedule.php';">Delete My Schedule</button>
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

<!-- <script>
    // You must be logged in to add an article


function click() {
    console.log("hello")
}

    document.getElementByID("gen").onclick = function(event) {
        // console.log(x);
        if(loggedin && mustdel) {
            event.preventDefault();
            alert("You must delete your schedule before generating a new one.");
        }
        else {
            location.replace("generator.php");
        }
    };

    document.getElementById(visualize).onclick = function(event) {
        console.log("hello")
        if(loggedin) {
            event.preventDefault();
            alert("You must be logged in to visualize your schedule.");
        } else {
            location.replace("visualizer.php");
        }
    };

    document.getElementById(del).onclick = function(event) {
        if(loggedin) {
            event.preventDefault();
            alert("You must be logged in to delete your schedule.");
        }
        else {
            location.replace("delete-schedule.php");
        }
    };





</script> -->