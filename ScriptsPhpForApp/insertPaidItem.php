<?php
 require_once('dbConnect.php');
 
$name = $_POST['name'];
$date = $_POST['date'];
$kod = $_POST['kod'];
$price = $_POST['price'];
 
$result = mysqli_query($con,"INSERT INTO paid (name, date, kod, price) 
          VALUES ('$name', '$date', '$kod', '$price')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>