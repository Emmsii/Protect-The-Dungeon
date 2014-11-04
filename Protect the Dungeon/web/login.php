<?php

$db_server	= 'localhost';
$db_name	= 'giftedpi_ptd';
$db_user	= 'giftedpi_matt';
$db_pass	= 'ng"\'L|B?4V9OyZyUN';

$data = $_POST";

if(!empty($data)){
	$db = mysqli_connect($db_server, $db_user, $db_user, $db_pass, $db_name);
	if($db->connect_errno > 0){
		die('It broke.');
	}

	$user = $data["user"];
	$pass = $data["pass"];

	$sql = "SELECT * FROM users WHERE user='".$name."'";

	if(!mysqli_query($db, $sql)){
		printf("Error: %s\n", mysqli_error($db));
	}

	$row = mysqli_fetch_array($query, MYSQLI_ASSOC);


}else{
	var_dump($data);
}

?>