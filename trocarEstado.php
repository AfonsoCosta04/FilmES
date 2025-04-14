<?php
include 'databasecon.php';

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["id"])) {
    $id = intval($_POST["id"]);

    $stmt = $conn->prepare("UPDATE Filme SET disponivel = NOT disponivel WHERE id_filme = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->close();
    $conn->close();

    http_response_code(200); // sucesso
} else {
    http_response_code(400); // erro
    echo "ID inválido.";
}
?>
