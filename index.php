<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head><meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	
	<title>Software Engineering Project</title>
		<link rel="stylesheet" href="se.css" type="text/css" />
     <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script>
			function SubmitForm() {
			var Select = document.forms["pInfo"]["Select"].value;
			var time = document.forms["pInfo"]["time"].value;
			var date = document.forms["pInfo"]["dAte"].value;
			
			if (Select == null || Select == "" || time == null || time =="" || date == "null" || date == "" ) {
				 alert("Please Select trades or communication, Date and Time. Thank You");
				 return false;
				}
				else {
			   $.post("submit.php", { Select: Select, time: time, date: date },
			   function(data) {
					$(".query-form").html(data);
			   });
			}}
</script>
<script>
			function like(placeholder) {
     		var name =  $(placeholder).attr('rel');
         	var Select = "alerts";
           	$.post("submit.php", { name: name, Select: Select },
			function(data) {
					$(".query-form").html(data);
			 });
		}
</script>
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
		<div class="query-builder-help" id="alert" >
			<input type="button" class="remove" value="?" id='help'>
			<script type="text/javascript"> 
				$("#help").click(function() {
		    		$("#helpOverlay").toggle();
		    		$("#helpBox").toggle();
				});
			</script>
		</div>
	<h3>Query Builder:</h3>
     <form name="pInfo" method="post" enctype="multipart/form-data" action="submit.php" id="pInfo">
		<select id="type" name ="Select" class='inputstyle' style='width:260px; margin-left:15px; margin-right:15px;'>
			<option value="" disabled selected>Select Trades or Communications</option>
			<option value='Trades'>Trades</option>
			<option value='Communications'>Communications</option>
		</select>
      
		<select class='inputstyle' name='dAte' style='width:260px; margin-left:15px; margin-right:15px;'>
			<option value="" disabled selected>Select Date</option>
				<?php  echo "<option value=".date("Ymd").">"; ?> 
			 	<script type="text/javascript">
				var today = new Date();
				document.write(today.getDate() + "/" + (today.getMonth()+1) + "/" + today.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-1 day')).">"; ?>
			 	<script type="text/javascript">
				var one = new Date();
				one.setDate(today.getDate()-1);
				document.write(one.getDate() + "/" + (one.getMonth()+1) + "/" + one.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-2 day')).">"; ?>
			 	<script type="text/javascript">
				var two = new Date();
				two.setDate(today.getDate()-2);
				document.write(two.getDate() + "/" + (two.getMonth()+1) + "/" + two.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-3 day')).">"; ?>
			 	<script type="text/javascript">
				var three = new Date();
				three.setDate(today.getDate()-3);
				document.write(three.getDate() + "/" + (three.getMonth()+1) + "/" + three.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-4 day')).">"; ?>
			 	<script type="text/javascript">
				var four = new Date();
				four.setDate(today.getDate()-4);
				document.write(four.getDate() + "/" + (four.getMonth()+1) + "/" + four.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-5 day')).">"; ?>
			 	<script type="text/javascript">
				var five = new Date();
				five.setDate(today.getDate()-5);
				document.write(five.getDate() + "/" + (five.getMonth()+1) + "/" + five.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-6 day')).">"; ?>
			 	<script type="text/javascript">
				var six = new Date();
				six.setDate(today.getDate()-6);
				document.write(six.getDate() + "/" + (six.getMonth()+1) + "/" + six.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-7 day')).">"; ?>
			 	<script type="text/javascript">
				var seven = new Date();
				seven.setDate(today.getDate()-7);
				document.write(seven.getDate() + "/" + (seven.getMonth()+1) + "/" + seven.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-8 day')).">"; ?>
			 	<script type="text/javascript">
				var eight = new Date();
				eight.setDate(today.getDate()-8);
				document.write(eight.getDate() + "/" + (eight.getMonth()+1) + "/" + eight.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-9 day')).">"; ?>
			 	<script type="text/javascript">
				var nine = new Date();
				nine.setDate(today.getDate()-9);
				document.write(nine.getDate() + "/" + (nine.getMonth()+1) + "/" + nine.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-10 day')).">"; ?>
			 	<script type="text/javascript">
				var ten= new Date();
				ten.setDate(today.getDate()-10);
				document.write(ten.getDate() + "/" + (ten.getMonth()+1) + "/" + ten.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-11 day')).">"; ?>
			 	<script type="text/javascript">
				var eleven = new Date();
				eleven.setDate(today.getDate()-11);
				document.write(eleven.getDate() + "/" + (eleven.getMonth()+1) + "/" + eleven.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-12 day')).">"; ?>
			 	<script type="text/javascript">
				var twelve = new Date();
				twelve.setDate(today.getDate()-12);
				document.write(twelve.getDate() + "/" + (twelve.getMonth()+1) + "/" + twelve.getFullYear());
				</script>
			</option>
		<?php  echo "<option value=".date("Ymd", strtotime('-13 day')).">"; ?>
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
      
	 <input type="button" class='submitbutton' id="searchForm" onClick="SubmitForm();" value="Submit" />
         </form>
	</div>
	<div class="query-builder-footer">
	</div>
	
	<div class="alert-feed" id="alert" >
		<table id="alerts" style="width:100%;">
	            <?php checkAlert(); ?>
	        </table>
	        <script>
			$('button[type="button"]').click(function(e){
			var alerts = $(this).closest('tr').find("button[name='buttonID']").val();
		    $(this).closest('tr').remove()
			var Select = "remove";
           	$.post("submit.php", { Select: Select, alerts: alerts },
			function(data) {
				$(".query-form").html(data);
			 });
			})
		</script>
	</div>
    <?php 
			 $firstPageDesign = "<div class='query-form'>Build your own Queries on the right :D";
			 echo $firstPageDesign;
	
		?>
	</div>
	<div id='helpOverlay'>
		<script type="text/javascript"> 
			$("#helpOverlay").click(function() {
				$("#helpOverlay").toggle();
				$("#helpBox").toggle();	
	
			});
		</script>
	</div>
	<div id='helpBox'>
	<h4>Help: Query Builder</h4>
	The Query Builder allows you to view any data from trades or communications from the past two weeks. Using the drop down boxes below: <br>
	1. Select whether you would like to view trade data or communications. <br>
	2. Select the date <br>
	3. Select a time slot <br> 
	</div>
	
	</body>
	</html>
