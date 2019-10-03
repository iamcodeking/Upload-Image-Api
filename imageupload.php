<?php
if($_SERVER['REQUEST_METHOD'] == "POST"){
$target="upload/images";
if (isset($_POST['image'])){
$imagefile=$_POST['image'];
if(!file_exists($target))
{
	mkdir($target,0777,true);
}
$target=$target."/".date("d-m-y")."_".time().".jpeg";
file_put_contents($target, base64_decode($imagefile));
echo "saved";
$local="localhost";
$username="root";
$password="";
$database="lak";
$conn2=new mysqli($local,$username,$password,$database);
$sql = "INSERT INTO ima (id, farmer)
VALUES ('1','".$target."')";
$conn2->query($sql);
}
else{
	echo "no data";
}
}
else
	echo "nope";

?>