<?php
session_start();
include 'databasecon.php';

// Verifica se está autenticado
if (!isset($_SESSION['utilizador_id'])) {
    echo "<script>alert('É necessário fazer login para alugar.'); window.location.href='login.html';</script>";
    exit();
}

// Verifica se o ID do filme foi passado
if (!isset($_GET['id'])) {
    echo "<script>alert('Filme não especificado.'); window.location.href='catalogonovo.html';</script>";
    exit();
}

$id_filme = $_GET['id'];
$id_cliente = $_SESSION['utilizador_id'];

// Buscar data de nascimento do cliente
$stmt = $conn->prepare("SELECT data_de_nasc_cliente FROM Cliente WHERE id_cliente = ?");
$stmt->bind_param("i", $id_cliente);
$stmt->execute();
$resCliente = $stmt->get_result();
$cliente = $resCliente->fetch_assoc();

// Buscar idade recomendada do filme
$stmt = $conn->prepare("SELECT idade_recomendada FROM Filme WHERE id_filme = ?");
$stmt->bind_param("i", $id_filme);
$stmt->execute();
$resFilme = $stmt->get_result();
$filme = $resFilme->fetch_assoc();

// Calcular idade do cliente
$dataNascimento = new DateTime($cliente['data_de_nasc_cliente']);
$hoje = new DateTime();
$idade = $hoje->diff($dataNascimento)->y;

// Comparar com a idade recomendada do filme
if ($idade < $filme['idade_recomendada']) {
    echo "<script>alert('Idade insuficiente para alugar este filme.'); window.location.href='catalogo.php';</script>";
    exit();
} else {
    header("Location: carrinho.html");
    exit();
}
?>