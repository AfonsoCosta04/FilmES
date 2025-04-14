<?php
include "databasecon.php";

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["id"])) {
    $id = $_POST["id"];

    // Prepara e executa o DELETE
    $stmt = $conn->prepare("DELETE FROM Funcionario WHERE id_funcionario = ?");
    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        echo "<script>alert('Funcionário eliminado com sucesso.'); window.location.href='selecionarFuncionario.php';</script>";
    } else {
        echo "<script>alert('Erro ao eliminar funcionário: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
} else {
    echo "<script>alert('Requisição inválida.'); window.location.href='selecionarFuncionario.php';</script>";
}
?>