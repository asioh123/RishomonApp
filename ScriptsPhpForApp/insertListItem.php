<?php
 require_once('dbConnect.php');
 
$name = $_POST['name'];
$date = $_POST['date'];
$item = $_POST['item'];
$price = $_POST['price'];
 
$result = mysqli_query($con,"INSERT INTO listitem (name, date, item, price) 
          VALUES ('$name', '$date', '$item', '$price')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>