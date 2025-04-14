<?php
include 'databasecon.php';

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["id_filme"])) {
    $id = $_POST["id_filme"];

    $stmt = $conn->prepare("DELETE FROM Filme WHERE id_filme = ?");
    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        echo "<script>alert('Filme eliminado com sucesso!'); window.location.href='escolheFilmeMudar.php';</script>";
    } else {
        echo "<script>alert('Erro ao eliminar filme: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
} else {
    echo "<script>alert('ID do filme não fornecido.'); window.history.back();</script>";
}
?>