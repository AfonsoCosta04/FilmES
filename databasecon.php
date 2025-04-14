<?php
$host = 'localhost';
$user = 'root';
$password = ''; // Coloca aqui a tua password, se tiveres uma
$database = 'filmes'; // Altera para o nome real da tua base de dados

$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    die("Erro de ligação à base de dados: " . $conn->connect_error);
}
else{
    echo("cenas");
}
?>