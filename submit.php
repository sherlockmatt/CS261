<script type="text/javascript">
			$(document).ready(function(){
				$("#spinner").bind("ajaxSend", function() {
					$(this).show();
				}).bind("ajaxStop", function() {
					$(this).hide();
				}).bind("ajaxError", function() {
					$(this).hide();
				});
			 
			});
</script>
<?php 
date_default_timezone_set('Europe/London');
if ($_POST['Select'] == 'Trades'){
	$startTime = substr($_POST['time'], 0,2);
	$endTime =  substr($_POST['time'], 9,-6);
	$year = substr($_POST['date'], 0 ,4);
	$month = substr($_POST['date'], 4 ,-2);
	$day = substr($_POST['date'], 6);
	$queryDate = $day.'/'.$month.'/'.$year;
	echo  "<div id='spinner'class='spinner' style='display:none;'> <img id='img-spinner' src='image/spin.gif' alt='Loading'/> </div>";
	$query = "Trades ".$year." ".$month." ".$day." ".$startTime;
	if(!exec('java -cp DBA.jar com.cs261.output.QueryPrinter '.$query.'', $output))
	{
		echo "Files are not stored. Due to limited space in the server. Sorry";
		$file = fopen("logfile.csv","a");
		$log = array ('Trades', $queryDate ,$_POST['time'],'Failed', date('m/d/Y h:i:s a', time()), "\r\n" );
		fputcsv($file, $log);	
		fclose($file);
	}
	else 
	{
		for ($i = 0; $i <= sizeof($output)-1; $i++)
		{
			echo $output[$i];
		}
		$file = fopen("logfile.csv","a");
		$log = array ('Trades', $queryDate ,$_POST['time'],'Success', date('m/d/Y h:i:s a', time()), "\r\n" );
		fputcsv($file, $log);	
		fclose($file);
	}
}
else if ($_POST['Select'] == 'Communications'){
	$startTime = substr($_POST['time'], 0,2);
	$endTime =  substr($_POST['time'], 9,-6);
	$year = substr($_POST['date'], 0 ,4);
	$month = substr($_POST['date'], 4 ,-2);
	$day = substr($_POST['date'], 6);
	$queryDate = $day.'/'.$month.'/'.$year;
	echo  "<div id='spinner'class='spinner' style='display:none;'> <img id='img-spinner' src='image/spin.gif' alt='Loading'/> </div>";
	$query = "Comms ".$year." ".$month." ".$day." ".$startTime;
	if(!exec('java -cp DBA.jar com.cs261.output.QueryPrinter '.$query.'', $output))
	{
		echo "Files are not stored. Due to limited space in the server. Sorry";
		$file = fopen("logfile.csv","a");
		$log = array ('Communications', $queryDate ,$_POST['time'],'Failed', date('m/d/Y h:i:s a', time()), "\r\n" );
		fputcsv($file, $log);	
		fclose($file);
	}
	else 
	{
		for ($i = 0; $i <= sizeof($output)-1; $i++)
		{
			echo $output[$i];
		}
		$file = fopen("logfile.csv","a");
		$log = array ('Communications', $queryDate ,$_POST['time'],'Success', date('m/d/Y h:i:s a', time()), "\r\n" );
		fputcsv($file, $log);	
		fclose($file);
	}
}
else if ($_POST['Select'] == 'alerts')
{
	error_reporting(0);
	if( substr($_POST['name'],0,4) == "Trades") {
		$fileName = $_POST['name'];
		try {
				readSpecifcTimeTrades($fileName);
			
			} catch(Exception $e) {
		  	  	echo $e->getMessage(), "\n";
				}
		}
	 else {
	 	$fileName = $_POST['name'];
		try {
				readSpecficComms($fileName);
			} catch(Exception $e) {
		  	  	echo $e->getMessage(), "\n";
				}
		}
}

else if ($_POST['Select'] == "remove"){
	error_reporting(0);
	$removeAlert = $_POST['alerts'];
	
	if (rename("alerts/".$removeAlert."", "Remove/".$removeAlert."") === false)
	{
		echo "</br>File had been deleted by other users. Please check the archive folder";
	} 

}
else if ($_POST['Select'] == "log")
{
	error_reporting(0);
 		try {
				readLog();
			
		} catch(Exception $e) {
		  	  	echo $e->getMessage(), "\n";
				}
}


function readSpecifcTimeTrades(&$fileName){

if (fopen("alerts/".$fileName, "r") === false)
{	
	throw new Exception('</br> File had been deleted by other users. Please check the archive folder');	
}	
else
{
$file_handle = fopen("alerts/".$fileName, "r") or die ("Cant get file");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file.

echo "<table id = 'comms' style='width:100%;'>";	

while (!feof($file_handle) ) { // While loop, to open all of the files 	

$trades = fgetcsv($file_handle, 1024);  // Array of results

 echo "<tr> <th width='15%;'> ".$trades[0] . " </th>";
 echo "<th width='12%;'> ".$trades[1] ." </th>";
 echo "<th width='12%;'> ".$trades[2] ." </th>" ; // Printing the results
 echo "<th width='5%;'> ".$trades[3] ." </th>" ; // Printing the results		<br>
 echo "<th width='7%;'> ".$trades[4] ." </th>" ; // Printing the results		<br>
 echo "<th width='6%;'> ".$trades[5] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[6] ." </th>" ; // Printing the results		<br>
 echo "<th width='12%;'> ".$trades[7] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[8] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[9] ." </th>" ; // Printing the results		<br>
 echo "</tr>";
}	
echo "</table>";

fclose($file_handle); // close
}}

function readSpecficComms(&$fileName) {

if (fopen("alerts/".$fileName, "r") === false)
{	
	throw new Exception('</br> File had been deleted by other users. Please check the archive folder');	
}	
else 
{
$file_handle = fopen("alerts/".$fileName, "r") or die ("Cant get file");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file

echo "<table id = 'comms' style='width:100%;'>";
while (!feof($file_handle) ) { // While loop, to open all of the files 	
$comms = fgetcsv($file_handle, 1024);  // Array of results
 echo "<tr> <th width='20%;'> ".$comms[0]. "  </th>";
 echo "<th width='20%;'> ".$comms[1] ." </th>";
 echo "<th width='60%;'> ".$comms[2] ." </th>" ; // Printing the results
 echo "</tr>";
} 
echo "</table>";
fclose($file_handle); // close
}}

function checkAlert() {

$dir = "alerts"; // directory 
$alert = scandir($dir,1); // scan it
$i = 1;
foreach ($alert as $a){
	if ($a != '..' && $a != '.'){
	echo "<tr><td>";
	echo "<a class=alerts href= 'javascript:;' onClick='like(this);' rel = ".$a.">Alert ".$i." </a><br> <h2>".$a. "</h2></td>";
	echo "<td style = 'text-align: right; vertical-align: top;'> <button type='button' id ='buttonID' name= 'buttonID' class='remove' value=".$a.">&times;</button></td>";
	echo "</tr>";
	$i++;
	}
}
}
function readLog() {

if (fopen("logfile.csv", "r") === false)
{	
	throw new Exception('</br> No Log had been created yet');	
}	
else 
{
$file_handle = fopen("logfile.csv", "r") or die ("Cant get file");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file

echo "<table id = 'log' style='width:100%;'>";
echo "<tr> <th width='20%;'>Type of Search</th>";
echo "<th width='20%;'>Date Searched</th>";
echo "<th width='20%;'>Time Searched</th>" ; // Printing the results
echo "<th width='20%;'>Status</th>" ; // Printing the results
echo "<th width='20%;'>Time Stamp</th>" ; // Printing the results
while (!feof($file_handle) ) { // While loop, to open all of the files 	
$logs = fgetcsv($file_handle, 1024);  // Array of results
 echo "<tr> <th width='20%;'> ".$logs[0]. "  </th>";
 echo "<th width='20%;'> ".$logs[1] ." </th>";
 echo "<th width='20%;'> ".$logs[2] ." </th>" ; // Printing the results
  echo "<th width='20%;'> ".$logs[3] ." </th>" ; // Printing the results
   echo "<th width='20%;'> ".$logs[4] ." </th>" ; // Printing the results
 echo "</tr>";
} 
echo "</table>";
fclose($file_handle); // close
}}


?>
