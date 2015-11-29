<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $firstName = $_POST['firstName'];
 $lastName = $_POST['lastName'];
 $phone = $_POST['phone'];
 $adress = $_POST['adress'];
 
 require_once('dbConnect.php');
 
 $sql = "INSERT INTO person (firstName,lastName,phone,adress) VALUES ('$firstName','$lastName','$phone','$adress')";
 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully Registered";
 }else{
 echo "Could not register";
 
 }
 }else{
echo 'error';
}