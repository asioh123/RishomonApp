<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){
 //Getting values 
 $celender = $_POST['date'];

 
 //Creating sql query
 $sql = "SELECT * FROM listitem WHERE date='$celender' AND NOT item='שולם' ORDER BY id DESC";
 
 //importing dbConnect.php script 
 require_once('dbConnect.php');
 
 //executing query
 $res = mysqli_query($con,$sql);
 
 $result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'name'=>$row[1],
'date'=>$row[2],
'item'=>$row[3],
'price'=>$row[4]
));
}
 
$result2 = array('ListItem' => $result);
 
echo json_encode($result2);

 mysqli_close($con);
 }