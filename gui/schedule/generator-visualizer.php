<?php
	require '../config/config.php';
	// var_dump($_SESSION);
	// var_dump($_GET);

	// $temp = json_decode($_GET);
	// var_dump($temp);
	// var_dump($_GET);

	// if(!isset($_GET['c0']) || empty($_GET['c0'])) {
	// 	$error = "Error with storing info in session.";
	// }
	// else {
	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}
	$mysqli->set_charset('utf8');

	$logged_in = false;
	if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) {
		$logged_in = false;
	} else {
		$logged_in = true;
	}
	if($logged_in) {
		$sql_delete = "DELETE FROM enrolled
		WHERE userID = " . $_SESSION['id'] . ";";
		$r = $mysqli->query($sql_delete);
		if ( !$r ) {
			echo $mysqli->error;
			exit();
		}
	}
	

	$sql = "SELECT sections.ID, sections.session, sections.title, sections.type, sections.startTime, sections.endTime, 
			CONCAT(instructors.first , ' ' , instructors.last) as instructor, instructors.rmp, sections.location, sections.dotw, sections.abrv FROM sections 
			LEFT JOIN instructors
				ON sections.instructor = instructors.ID";

	$count = 0;
	$enrolled_sql = "";

	$list = explode(',', $_GET['list']);
	// var_dump($list);


	for ($i = 0; $i < $_GET['len']; $i++) {
		// $str = "c" + $i;
		if($i == 0) {
			$sql = $sql . " WHERE sections.ID = " . $list[$i];
			if($logged_in) {
				$enrolled_sql = "INSERT INTO enrolled(userID, sectionID) 
				VALUES(" . $_SESSION['id'] . ", " . $list[$i] . ")";
			}
		} else {
			$sql = $sql . " OR sections.ID = " . $list[$i];
			if($logged_in) {
				$enrolled_sql = $enrolled_sql . ", (" . $_SESSION['id'] . ", " . $list[$i] . ")";
			}
		}

	}

	$sql = $sql . " ORDER BY abrv, startTime;";

	if($logged_in) {
		$enrolled_sql = $enrolled_sql . ";";
	}

	// var_dump($enrolled_sql);


	$results = $mysqli->query($sql);
	if ( !$results ) {
		echo $mysqli->error;
		exit();
	}
	// var_dump($results);

	if(mysqli_num_rows($results) == 0) {
		$error = "No classes found.";
	}

	$enrolled_results = NULL;
	if($logged_in) {
		$enrolled_results = $mysqli->query($enrolled_sql);
		if ( !$enrolled_results ) {
			echo $mysqli->error;
			exit();
		}
	}

	$mysqli->close();
// }
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="../login-page/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="../login-page/assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="../login-page/assets/css/Header-Blue.css">
    <link rel="stylesheet" href="../login-page/assets/css/styles.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" href="../styles.css">
	<title>Generated Schedule</title>
	<style>
		h2 {
			color:maroon;
			font-weight: bold;
			text-align: center;
		}
		#back {
			padding:10px;
		}
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
					<div class="col-12">
                    <h2>Your Generated Schedule</h2>
                </div>

					<!-- TODO: add visualizer -->
					<div class="col-12">
				<table class="table table-hover table-responsive-sm">
					<thead>
						<tr>
                            <th>ID</th>
							<th>DotW</th>
							<th>Course Name</th>
							<th>Title</th>
							<th>Type</th>
							<th>Instructor</th>
							<th>RMP</th>
							<th>Start Time</th>
							<th>End Time</th>
							<th>Location</th>
						</tr>
					</thead>

					<tbody>
						<?php while($row = $results->fetch_assoc()) : ?>
						<tr>
                            <td>
                                <?php echo $row["ID"];?>
                            </td>
							<td>
								<?php echo $row["dotw"];?>
							</td>
							<td>
								<?php echo $row['abrv'];?>
							</td>
							<td>
								<?php echo $row['title'];?>
							</td>
							<td>
								<?php echo $row['type'];?>
							</td>
							<td>
								<?php echo $row['instructor'];?>
							</td>
							<td>
								<?php echo $row['rmp'];?>
							<td>
								<?php echo $row['startTime'];?>
							</td>
							<td>
								<?php echo $row['endTime'];?>
							</td>
							<td>
								<?php echo $row['location'];?>
							</td>

						</tr>	
					<?php endwhile;?>

					</tbody>

				</table>



			</div>


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
