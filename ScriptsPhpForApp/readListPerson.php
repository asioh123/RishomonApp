<?php
 require_once('dbConnect.php');

 
$sql = "select * from person order by lastName ASC";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'firstName'=>$row[1],
'lastName'=>$row[2],
'phone'=>$row[3],
'address'=>$row[4]
));
}

$result2 = array('Person' => $result);
 
echo json_encode($result2);
 
mysqli_close($con);
 
?>