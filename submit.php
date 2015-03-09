<?php 
if ($_POST['Select'] == 'Trades'){

	$startTime = substr($_POST['time'], 0,8);
	$endTime =  substr($_POST['time'], 9);
	$dAte = $_POST['date'];
	exec('java -jar FileName.jar '.$dAte.' '. $startTime.' '.$endTime.'', $output);
	print_r($output); // print 
}
else if ($_POST['Select'] == 'Communications'){
	
	$startTime = substr($_POST['time'], 0,8);
	$endTime =  substr($_POST['time'], 9);
	$dAte = $_POST['date'];
	exec('java -jar FileName.jar '.$dAte.' '. $startTime.' '.$endTime.'', $output);
	print_r($output); // print 
}

else if ($_POST['Select'] == 'alerts')

{
	$fileName = $_POST['name'];
	try {
		readSpecifcTimeTrades($fileName);
	} catch(Exception $e) {
	
       echo $e->getMessage(), "\n";
	}
	
}

else if ($_POST['Select'] == "remove"){

	$removeAlert = $_POST['alerts'];
	rename("alerts/".$removeAlert."", "Remove/".$removeAlert."") or die ("Failed to move file.");
}

function readSpecifcTimeTrades(&$fileName){

if (!fopen("data/".$fileName."comms.csv", "r"))
{	
	throw new Exception('File had been deleted by other users. Please check the archieve folder');	
}	
else
{
$file_handle = fopen("data/".$fileName."trades.csv", "r") or die ("Cant get file");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file.

echo "<table id = 'comms' style='width:100%;'>";	

while (!feof($file_handle) ) { // While loop, to open all of the files 	

$trades = fgetcsv($file_handle, 1024);  // Array of results
if (substr($trades[0],11,19) >= $startTime && substr($trades[0],11,19) <= $endTime)
{
 echo "<tr> <th width='15%;'> ".$trades[0] . " </th>";
 echo "<th width='12%;'> ".$trades[1] ." </th>";
 echo "<th width='12%;'> ".$trades[2] ." </th>" ; // Printing the results
 echo "<th width='5%;'> ".$trades[3] ." </th>" ; // Printing the results		<br>
 echo "<th width='8%;'> ".$trades[4] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[5] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[6] ." </th>" ; // Printing the results		<br>
 echo "<th width='12%;'> ".$trades[7] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[8] ." </th>" ; // Printing the results		<br>
 echo "<th width='5%;'> ".$trades[9] ." </th>" ; // Printing the results		<br>
 echo "</tr>";
}	}
echo "</table>";

fclose($file_handle); // close
}}

function readSpecficComms(&$startDate,&$startTime,&$endTime) {

if (!fopen("data/".$startDate."comms.csv", "r"))
{	
	throw new Exception('File had been deleted by other users. Please check the archive folder');	
}	

$file_handle = fopen("data/".$startDate."comms.csv", "r") or die ("Cant get file");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file

echo "<table id = 'comms' style='width:100%;'>";
while (!feof($file_handle) ) { // While loop, to open all of the files 	
$comms = fgetcsv($file_handle, 1024);  // Array of results
if (substr($comms[0],11,19) >= $startTime && substr($comms[0],11,19) <= $endTime)
{

 echo "<tr> <th width='20%;'> ".$comms[0] . " </th>";
 echo "<th width='20%;'> ".$comms[1] ." </th>";
 echo "<th width='60%;'> ".$comms[2] ." </th>" ; // Printing the results
 echo "</tr>";
} }
echo "</table>";
fclose($file_handle); // close
}

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
?>
