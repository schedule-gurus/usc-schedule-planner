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
	// var_dump($_GET);

	$names = explode(" ", $_GET['search']);
	// echo $names;

	// I want name, id, major, grad year, and an add friend button
	$sql = "SELECT CONCAT(users.first , ' ' , users.last) as name, users.ID as id, users.gradYear as gradYear, major.abrv as majorAbrv, major.name as major
		FROM users
		LEFT JOIN major
			ON users.major = major.ID
		WHERE 1 = 1";

	if(isset($_GET["search"]) && !empty($_GET["search"])) {
		foreach ($names as $n) {
			$sql = $sql . " AND (users.first LIKE '%" . $n . "%' OR users.last LIKE '%" . $n . "%')";
		}
		
	}

	$sql = $sql . ";";

	// var_dump($sql);

	$results = $mysqli->query($sql);
	if($results == false) {
		echo $mysqli->error;
		exit();
	}

	$mysqli->close();
} else {
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
	<title>Search Results</title>
	<style>
		.col-12 {
            text-align: center;
            width:auto;
        }
      
        h2 {
			color:maroon;
			font-weight: bold;
		}
		#space {
			padding:10px;
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
                    <h2>Search Results</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                	<?php 
					if(mysqli_num_rows($results) < 1) {
						echo "No users found.";
					}
					else {
						echo "Showing " . mysqli_num_rows($results) . " result(s).";
					}
				?>
        		</div>
        	</div>
        	<div id="space" class="row">
                <div class="col-1">
                	<a href="social.php" role="button" class="btn btn-primary btn-dark" id="back">Back</a>
        		</div>
                
            </div>

           

        	


        	<div class="col-12">
				<table class="table table-hover table-responsive-sm">
					<thead>
						<tr>
							<th class="name">Name</th>
							<th>Graduation Year</th>
							<th>Major Abbreviation</th>
							<th>Major</th>
							<th>Add to Friends</th>
						</tr>
					</thead>

					<tbody>
						<?php while($row = $results->fetch_assoc()) : ?>
						<tr>
							<td class="name">
								<?php echo $row["name"];?>
							</td>
									<td class="gradYear">
										<?php echo $row['gradYear'];?>
									</td>
									<td class="majorabrv">
										<?php echo $row['majorAbrv'];?>
									</td>
									<td class="major">
										<?php echo $row['major'];?>
									</td>
							<td class="add">
								<a href="add-friend.php?id=<?php echo $row['id'];?>&name=<?php echo $row['name'];?>" name="<?php echo $row['id'];?>" class="btn btn-primary btn-dark" role="button" id="add">Add</a>
							</td>

						</tr>	
					<?php endwhile;?>

					</tbody>

				</table>



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
 	


 </script>