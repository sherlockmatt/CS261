<?PHP
function readTrades() {
$file_handle = fopen("Trades.csv", "r");  // Search for the Csv file name, R means open for reading only; place the file pointer at the beginning of the file.

echo "<table id = 'trades' style='width:100%;'>";	
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
?>