<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);
include 'databasecon.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nome = $_POST["nome_cliente"] ?? '';
    $email = $_POST["email_cliente"] ?? '';
    $nascimento = $_POST["data_de_nasc_cliente"] ?? '';
    $telemovel = $_POST["numero_de_telefone"] ?? '';
    $password = $_POST["password_cliente"] ?? '';
    $repetir = $_POST["repetirPassword"] ?? '';
    $nif = $_POST["contribuinte"] ?? null;

    // Validar password
    if ($password !== $repetir) {
        echo "<script>alert('As passwords não coincidem!'); window.history.back();</script>";
        exit();
    }

    // Se NIF estiver vazio, envia como NULL
    if (empty($nif)) {
        $nif = null;
    }

    // Inserção na base de dados
    $stmt = $conn->prepare("INSERT INTO cliente (
        nome_cliente, email_cliente, data_de_nasc_cliente, numero_de_telefone, password_cliente, contribuinte
    ) VALUES (?, ?, ?, ?, ?, ?)");

    $stmt->bind_param("ssssss", $nome, $email, $nascimento, $telemovel, $password, $nif);

    if ($stmt->execute()) {
        echo "<script>alert('Registo efetuado com sucesso!'); window.location.href='catalogo.php';</script>";
    } else {
        echo "<script>alert('Erro ao registar: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
}
?>