<?php
 require_once('dbConnect.php');
 

$id= $_POST['id'];
 
$result = mysqli_query($con,"DELETE FROM listitem WHERE id='$id'");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>