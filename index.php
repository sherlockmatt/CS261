<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head><meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	
	<title>Software Engineering Project</title>
		<link rel="stylesheet" href="se.css" type="text/css" />
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<<<<<<< HEAD
		<script type="text/javascript" charset="utf-8"></script>
=======
		<script type="text/javascript" charset="utf-8">
        function validateForm() {
			var nAme = document.forms["pInfo"]["Select"].value;
			var tIme = document.forms["pInfo"]["time"].value
		
			if (nAme == null || nAme == "" || tIme == null || tIme =="") {
					alert("Fill up all the information needed");
				 return false;
				}
		}
	</script>
    
>>>>>>> origin/teo
	</head>
	<body>
	<?php include 'submit.php'; ?>
	<div class="header">
		<img class="DB-logo" src="DB.gif" alt="Deutsche Bank Logo">
		<div class="header-writing">
			Deutsche Bank Trade Analysis
		</div>
	</div>
  
	<div class="query-builder" id="alert" >
	<h3>Query Builder:</h3>
<<<<<<< HEAD
     <form name="pInfo" method="post" enctype="multipart/form-data" action="submit.php">
		<select id="type" name ="Select"class='inputstyle' style='width:260px; margin-left:15px; margin-right:15px;'>
=======
     <form name="pInfo" method="post" enctype="multipart/form-data" action="submit.php" onSubmit="return validateForm();">
		<select id="type" name ="Select" class='inputstyle' style='width:260px; margin-left:15px; margin-right:15px;'>
>>>>>>> origin/teo
			<option value="" disabled selected>Select Trades or Communications</option>
			<option value='Trades'>Trades</option>
			<option value='Communications'>Communications</option>
		</select>
      
		<select class='inputstyle' style='width:260px; margin-left:15px; margin-right:15px;'>
			<option value="" disabled selected>Select Date</option>
			<option value='today'>
			 	<script type="text/javascript">
				var today = new Date();
				document.write(today.getDate() + "/" + (today.getMonth()+1) + "/" + today.getFullYear());
				</script>
			</option>
			<option value='yesterday'>
			 	<script type="text/javascript">
				var one = new Date();
				one.setDate(today.getDate()-1);
				document.write(one.getDate() + "/" + (one.getMonth()+1) + "/" + one.getFullYear());
				</script>
			</option>
			<option value='2daysago'>
			 	<script type="text/javascript">
				var two = new Date();
				two.setDate(today.getDate()-2);
				document.write(two.getDate() + "/" + (two.getMonth()+1) + "/" + two.getFullYear());
				</script>
			</option>
			<option value='3daysago'>
			 	<script type="text/javascript">
				var three = new Date();
				three.setDate(today.getDate()-3);
				document.write(three.getDate() + "/" + (three.getMonth()+1) + "/" + three.getFullYear());
				</script>
			</option>
			<option value='4daysago'>
			 	<script type="text/javascript">
				var four = new Date();
				four.setDate(today.getDate()-4);
				document.write(four.getDate() + "/" + (four.getMonth()+1) + "/" + four.getFullYear());
				</script>
			</option>
			<option value='5daysago'>
			 	<script type="text/javascript">
				var five = new Date();
				five.setDate(today.getDate()-5);
				document.write(five.getDate() + "/" + (five.getMonth()+1) + "/" + five.getFullYear());
				</script>
			</option>
			<option value='6daysago'>
			 	<script type="text/javascript">
				var six = new Date();
				six.setDate(today.getDate()-6);
				document.write(six.getDate() + "/" + (six.getMonth()+1) + "/" + six.getFullYear());
				</script>
			</option>
			<option value='7daysago'>
			 	<script type="text/javascript">
				var seven = new Date();
				seven.setDate(today.getDate()-7);
				document.write(seven.getDate() + "/" + (seven.getMonth()+1) + "/" + seven.getFullYear());
				</script>
			</option>
			<option value='8daysago'>
			 	<script type="text/javascript">
				var eight = new Date();
				eight.setDate(today.getDate()-8);
				document.write(eight.getDate() + "/" + (eight.getMonth()+1) + "/" + eight.getFullYear());
				</script>
			</option>
			<option value='9daysago'>
			 	<script type="text/javascript">
				var nine = new Date();
				nine.setDate(today.getDate()-9);
				document.write(nine.getDate() + "/" + (nine.getMonth()+1) + "/" + nine.getFullYear());
				</script>
			</option>
			<option value='10daysago'>
			 	<script type="text/javascript">
				var ten= new Date();
				ten.setDate(today.getDate()-10);
				document.write(ten.getDate() + "/" + (ten.getMonth()+1) + "/" + ten.getFullYear());
				</script>
			</option>
			<option value='11daysago'>
			 	<script type="text/javascript">
				var eleven = new Date();
				eleven.setDate(today.getDate()-11);
				document.write(eleven.getDate() + "/" + (eleven.getMonth()+1) + "/" + eleven.getFullYear());
				</script>
			</option>
			<option value='12daysago'>
			 	<script type="text/javascript">
				var twelve = new Date();
				twelve.setDate(today.getDate()-12);
				document.write(twelve.getDate() + "/" + (twelve.getMonth()+1) + "/" + twelve.getFullYear());
				</script>
			</option>
			<option value='13daysago'>
			 	<script type="text/javascript">
				var thirteen= new Date();
				thirteen.setDate(today.getDate()-13);
				document.write(thirteen.getDate() + "/" + (thirteen.getMonth()+1) + "/" + thirteen.getFullYear());
				</script>
			</option>
		</select> 
		<select class='inputstyle' name ='time' style='width:260px; margin-left:15px; margin-right:15px;'>
			<option value="" disabled selected>Select Time</option>
			<option value='01:00:00-02:00:00'>01.00 - 02.00</option> 
			<option value='02:00:00-03:00:00'>02.00 - 03.00</option> 
			<option value='03:00:00-04:00:00'>03.00 - 04.00</option>
			<option value='04:00:00-05:00:00'>04.00 - 05.00</option>
			<option value='05:00:00-06:00:00'>05.00 - 06.00</option>	
			<option value='06:00:00-07:00:00'>06.00 - 07.00</option>
			<option value='07:00:00-08:00:00'>07.00 - 08.00</option>
			<option value='08:00:00-09:00:00'>08.00 - 09.00</option>
			<option value='09:00:00-10:00:00'>09.00 - 10.00</option>
			<option value='10:00:00-11:00:00'>10.00 - 11.00</option>
			<option value='11:00:00-12:00:00'>11.00 - 12.00</option>
			<option value='12:00:00-13:00:00'>12.00 - 13.00</option>
			<option value='13:00:00-14:00:00'>13.00 - 14.00</option>
			<option value='14:00:00-15:00:00'>14.00 - 15.00</option>
			<option value='15:00:00-16:00:00'>15.00 - 16.00</option>
			<option value='16:00:00-17:00:00'>16.00 - 17.00</option>
			<option value='17:00:00-18:00:00'>17.00 - 18.00</option>
			<option value='18:00:00-19:00:00'>18.00 - 19.00</option>
			<option value='19:00:00-20:00:00'>19.00 - 20.00</option>
			<option value='20:00:00-21:00:00'>20.00 - 21.00</option>
			<option value='21:00:00-22:00:00'>21.00 - 22.00</option>
			<option value='22:00:00-23:00:00'>22.00 - 23.00</option>
			<option value='23:00:00-24:00:00'>23.00 - 24.00</option>
			
		</select>
		
			
			<input class='submitbutton' type='submit' value='Submit'>
            </form>
	
	</div>
	<div class="query-builder-footer">
	</div>
	
	<div class="alert-feed" id="alert" >
		<table id="alerts" style="width:100%;">
	            <?php checkAlert(); ?>
	        </table>
	       
	        <script>
			$('input[type="button"]').click(function(e){
		   	$(this).closest('tr').remove()
			})
		</script>
	</div>
	<div class="query-form">
		<?php 
			 $type = $_GET['type'];
			 if ($type == 1){
<<<<<<< HEAD
			 	readTrades();
=======
			 	readSpecifcTimeTrades($_GET['startTime'],$_GET['endTime']);
>>>>>>> origin/teo
				}
			 else if ($type == 2){
			 	readComms();
				}
		?>
	</div>
	</body>
	</html>
