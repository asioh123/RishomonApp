<?php
 define('HOST','');
 define('USER','');
 define('PASS','');
 define('DB','');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');

 mysqli_set_charset($con,"utf8");