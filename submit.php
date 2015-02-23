<?php

if ($_POST['type'] == 'Trades'){
	readTrades();
}
else if ($_POST['type'] == 'Communications'){
	readComms();
}

function readTrades() {
$file_handle = fopen("data/trades.csv", "r");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file
$return = "<table id = 'trades' style='width:100%;'>";	


while (!feof($file_handle) ) { // While loop, to open all of the files 	

$trades = fgetcsv($file_handle, 1024);  // Array of results

 echo "<tr> <th> ".$trades[0] . " </th>";
 echo "<th> ".$trades[1] ." </th>";
 echo "<th> ".$trades[2] ." </th>" ; // Printing the results
 echo "<th> ".$trades[3] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[4] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[5] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[6] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[7] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[8] ." </th>" ; // Printing the results		<br>
 echo "<th> ".$trades[9] ." </th>" ; // Printing the results		<br>
 echo "</tr>";
}	
echo "</table>";
fclose($file_handle); // close
}

function readComms() {
$file_handle = fopen("Comms.csv", "r");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file
$return = "<table id = 'comms' style='width:calc(100% - 320px);'>";	
while (!feof($file_handle) ) { // While loop, to open all of the files 	

$comms = fgetcsv($file_handle, 1024);  // Array of results

 $return .= "<tr> <td> ".$comms[0] . " </td>";
 $return .= "<td> ".$comms[1] ." </td>";
 $return .= "<td> ".$comms[2] ." </td>" ; // Printing the results
 $return .= "</tr>";
}	
$return .= "</table>";
fclose($file_handle); // close
echo $return;
}

function checkAlert() {

$dir = "alerts"; // directory 
$alert = scandir($dir,1); // scan it
$i = 1;
foreach ($alert as $a){
	if ($a != '..' && $a != '.'){
	echo "<tr><td> Alert ".$i." <br> <h2>".$a. "</h2></td>";
	echo "<td style = 'text-align: right; vertical-align: top;'> <input type='button' class='remove' value='&times'> </td>";
	echo "</tr>";
	$i++;
	}
}
}
?>
