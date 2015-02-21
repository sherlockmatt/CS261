<?php

if ($_POST['type'] == 'Trades'){
	readTrades();
}
else if ($_POST['type'] == 'Communications'){
	readComms();
}

function readTrades() {
$file_handle = fopen("Trades.csv", "r");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file
$return = "<table id = 'trades' style='width:100%;'>";	


while (!feof($file_handle) ) { // While loop, to open all of the files 	

$trades = fgetcsv($file_handle, 1024);  // Array of results



 $return .= "<tr> <td> ".$trades[0] . " </td>";
 $return .= "<td> ".$trades[1] ." </td>";
 $return .= "<td> ".$trades[2] ." </td>" ; // Printing the results
 $return .= "<td> ".$trades[3] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[4] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[5] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[6] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[7] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[8] ." </td>" ; // Printing the results		<br>
 $return .= "<td> ".$trades[9] ." </td>" ; // Printing the results		<br>
 $return .= "</tr>";
}	
$return .= "</table>";
fclose($file_handle); // close
echo $return;
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

?>