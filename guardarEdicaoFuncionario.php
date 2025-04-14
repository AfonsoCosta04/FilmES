<?php
include "databasecon.php";

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["id"])) {
    $id = $_POST["id"];
    $nome = $_POST["nome"];
    $email = $_POST["email"];
    $telefone = $_POST["telefone"];
    $senha = $_POST["senha"];
    $confirmar = $_POST["confirmar"];

    // Verifica se a senha e a confirmação coincidem
    if ($senha !== $confirmar) {
        echo "<script>alert('As palavras-passe não coincidem.'); window.history.back();</script>";
        exit();
    }

    // Permissões (0 se não marcadas, 1 se marcadas)
    $leitura = isset($_POST["leitura"]) ? 1 : 0;
    $criacao = isset($_POST["criacao"]) ? 1 : 0;
    $edicao  = isset($_POST["edicao"])  ? 1 : 0;

    // Atualiza os dados do funcionário
    $stmt = $conn->prepare("UPDATE Funcionario SET 
        nome_funcionario = ?, 
        email_funcionario = ?, 
        telefone = ?, 
        password_funcionario = ?, 
        perm_leitura = ?, 
        perm_criacao = ?, 
        perm_edicao = ?
        WHERE id_funcionario = ?");
    
    $stmt->bind_param("ssssiiii", $nome, $email, $telefone, $senha, $leitura, $criacao, $edicao, $id);

    if ($stmt->execute()) {
        echo "<script>alert('Dados do funcionário atualizados com sucesso.'); window.location.href='selecionarFuncionario.php';</script>";
    } else {
        echo "<script>alert('Erro ao atualizar funcionário: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
} else {
    echo "<script>alert('Requisição inválida.'); window.location.href='selecionarFuncionario.php';</script>";
}
?>
