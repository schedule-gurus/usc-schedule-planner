<?php
require '../config/config.php';
// if you're not logged in
if ( !isset($_SESSION['logged_in']) || !$_SESSION['logged_in'] ) {
	$error = "You cannot visualize saved schedules if you're not logged in.";
} else {
	$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
	if($mysqli->connect_errno) {
		echo $mysqli->connect_error;
		exit();
	}
	$mysqli->set_charset('utf8');

	$sql = "SELECT enrolled.sectionID as id, sections.session as session, sections.title as title, 
		sections.type as type, sections.startTime as start, sections.endTime as end, CONCAT(instructors.first , ' ' , instructors.last) as instructor, sections.location as location, sections.dotw as dotw, sections.abrv as abrv FROM enrolled 
		LEFT JOIN sections
			ON sections.ID = enrolled.sectionID
    	LEFT JOIN instructors
			ON sections.instructor = instructors.ID 
		WHERE " . $_GET['id'] . " = enrolled.userID AND enrolled.sectionID = sections.ID 
		ORDER BY abrv, start;";
	// var_dump($sql);
	$results = $mysqli->query($sql);
	if ( !$results ) {
		echo $mysqli->error;
		exit();
	}

	if(mysqli_num_rows($results) == 0) {
		$error = "No schedule saved.";
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
	<title>Schedule Visualizer</title>
	<style>
		.text-success, .text-danger {
			text-align: center;
		}
		h2 {
            color:maroon;
            font-weight: bold;
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
                    <h2><?php echo $_GET['user'];?>'s Schedule</h2>
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
							<th>Start Time</th>
							<th>End Time</th>
							<th>Location</th>
						</tr>
					</thead>

					<tbody>
						<?php while($row = $results->fetch_assoc()) : ?>
						<tr>
							<td>
                                <?php echo $row["id"];?>
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
								<?php echo $row['start'];?>
							</td>
							<td>
								<?php echo $row['end'];?>
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