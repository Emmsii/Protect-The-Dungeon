<?php

if(!empty($_GET['u']) && !empty($_GET['p'])) {
	$user = $_GET['u'];
	$pass = $_GET['p'];

	$con=mysql_connect('localhost','giftedpi_matt','Z{D7aCdxq#pN') or die(mysql_error());
	mysql_select_db('giftedpi_ptd') or die("cannot select DB");

	$query=mysql_query("SELECT * FROM users WHERE user='".$user."'");
	$numrows=mysql_num_rows($query);
	if($numrows==0){
		echo "no";
	}else{
		echo "yes";
	}
}
?>