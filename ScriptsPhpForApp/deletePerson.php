<?php
 require_once('dbConnect.php');
 

$firstName= $_POST['firstName'];
$lastName= $_POST['lastName'];
$name = $_POST['name'];

echo $name;

 
$result = mysqli_query($con,"DELETE FROM person WHERE firstName='$firstName' AND lastName='$lastName'");

$result2 = mysqli_query($con,"DELETE FROM listitem WHERE name='$name'");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}

if($result2 == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>