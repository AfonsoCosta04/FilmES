<?php
session_start();
include "databasecon.php";

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST['email_cliente'] ?? '';
    $password = $_POST['password_cliente'] ?? '';

    $stmt = $conn->prepare("SELECT * FROM Cliente WHERE email_cliente = ? AND password_cliente = ?");
    $stmt->bind_param("ss", $email, $password);
    $stmt->execute();
    $resultado = $stmt->get_result();

    if ($resultado && $resultado->num_rows === 1) {
        $utilizador = $resultado->fetch_assoc();
        $_SESSION['utilizador_id'] = $utilizador['id_cliente'];
        $_SESSION['utilizador_nome'] = $utilizador['nome_cliente'];
        $_SESSION['tipoUtilizador'] = 4; // tipo cliente

        header("Location: catalogo.php");
        exit();
    } else {
        echo "<script>alert('Utilizador não encontrado ou credenciais inválidas.'); window.location.href='login.html';</script>";
        exit();
    }
} else {
    header("Location: login.html");
    exit();
}
?>