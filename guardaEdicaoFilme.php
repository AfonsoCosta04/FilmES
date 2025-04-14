<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
include 'databasecon.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id_filme"] ?? 0;
    $titulo = $_POST["nome"] ?? '';
    $ano = $_POST["ano"] ?? 0;
    $horas = $_POST["horas"] ?? 0;
    $minutos = $_POST["minutos"] ?? 0;
    $idade_recomendada = $_POST['idade_recomendada'];
    $imdb = floatval($_POST['imdb']);
    $rotten = $_POST["rotten"] ?? null;
    $genero1 = $_POST["genero1"] ?? null;
    $genero2 = $_POST["genero2"] ?? null;
    $genero3 = $_POST["genero3"] ?? null;
    $ator1 = $_POST["ator1"] ?? '';
    $ator2 = $_POST["ator2"] ?? null;
    $ator3 = $_POST["ator3"] ?? null;
    $sinopse = $_POST["sinopse"] ?? '';
    $preco = $_POST["preco"] ?? 5.00;

    // Atualizar a imagem apenas se for enviada
    $foto = null;
    if (isset($_FILES["imagem"]) && $_FILES["imagem"]["error"] == 0) {
        $pastaImagens = "imagens/";
        $nomeImagem = basename($_FILES["imagem"]["name"]);
        $caminhoImagem = $pastaImagens . $nomeImagem;

        if (move_uploaded_file($_FILES["imagem"]["tmp_name"], $caminhoImagem)) {
            $foto = $caminhoImagem;
        }
    }

    if ($foto) {
        $stmt = $conn->prepare("UPDATE Filme SET 
            titulo=?, ano=?, duracao_horas=?, duracao_minutos=?, idade_recomendada=?, imdb=?, rotten_tomatoes=?, 
            genero1=?, genero2=?, genero3=?, 
            ator1=?, ator2=?, ator3=?, 
            sinopse=?, preco=?, foto=? 
            WHERE id_filme=?");

        $stmt->bind_param("siiiidisssssssdsi", 
            $titulo, $ano, $horas, $minutos,$idade_recomendada, $imdb, $rotten,
            $genero1, $genero2, $genero3,
            $ator1, $ator2, $ator3,
            $sinopse, $preco, $foto, $id);
    } else {
        $stmt = $conn->prepare("UPDATE Filme SET 
            titulo=?, ano=?, duracao_horas=?, duracao_minutos=?, idade_recomendada=?, imdb=?, rotten_tomatoes=?, 
            genero1=?, genero2=?, genero3=?, 
            ator1=?, ator2=?, ator3=?, 
            sinopse=?, preco=? 
            WHERE id_filme=?");

        $stmt->bind_param("siiiidisssssssdi", 
            $titulo, $ano, $horas, $minutos, $idade_recomendada, $imdb, $rotten,
            $genero1, $genero2, $genero3,
            $ator1, $ator2, $ator3,
            $sinopse, $preco, $id);
    }

    if ($stmt->execute()) {
        echo "<script>alert('Filme atualizado com sucesso!'); window.location.href='escolheFilmeMudar.php';</script>";
    } else {
        echo "<script>alert('Erro ao atualizar filme: " . $stmt->error . "'); window.history.back();</script>";
    }

    $stmt->close();
    $conn->close();
}
?>
