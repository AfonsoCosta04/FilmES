<?php
include "databasecon.php";

// Verifica se o formulário foi submetido
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $nome = $_POST["nome"];
    $email = $_POST["email"];
    $telefone = $_POST["telefone"] ?? null;
    $senha = $_POST["senha"];
    $confirmar = $_POST["confirmar"];

    // Permissões (0 se não marcado, 1 se marcado)
    $leitura = isset($_POST["leitura"]) ? 1 : 0;
    $criacao = isset($_POST["criacao"]) ? 1 : 0;
    $edicao  = isset($_POST["edicao"])  ? 1 : 0;

    // Validação de senha
    if ($senha !== $confirmar) {
        echo "<script>alert('As senhas não coincidem.'); window.history.back();</script>";
        exit();
    }

    // Prepara e executa a query
    $stmt = $conn->prepare("INSERT INTO Funcionario (nome_funcionario, email_funcionario, telefone, password_funcionario, perm_leitura, perm_criacao, perm_edicao)
                            VALUES (?, ?, ?, ?, ?, ?, ?)");

    $stmt->bind_param("ssssiii", $nome, $email, $telefone, $senha, $leitura, $criacao, $edicao);

    if ($stmt->execute()) {
        echo "<script>alert('Funcionário adicionado com sucesso!'); window.location.href='escolhaFuncAdmin.html';</script>";
    } else {
        echo "<script>alert('Erro ao adicionar funcionário: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
} else {
    echo "<script>alert('Acesso inválido.'); window.location.href='adicionarFuncionario.html';</script>";
}
?>