<?php
include 'databasecon.php';

$id = $_GET['id'] ?? 0;

$stmt = $conn->prepare("SELECT * FROM Filme WHERE id_filme = ?");
$stmt->bind_param("i", $id);
$stmt->execute();
$filme = $stmt->get_result()->fetch_assoc();
?>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FILME.S. - Editar Filme</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/x-icon" href="imagens/favicon.ico">
</head>
<body>
<header>
    <div class="logo">
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S">
    </div>
    <div class="role-admin">
        <p><strong>ADMIN</strong></p>
    </div>
</header>

<main>
    <div class="background-image">
        <div class="formulario-editar-filme">
            <h2>Editar Filme</h2>
            <a href="escolheFilmeMudar.php" class="botao-voltar-edicao">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>

            <form action="guardaEdicaoFilme.php" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="id_filme" value="<?= $filme['id_filme'] ?>">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="<?= htmlspecialchars($filme['titulo']) ?>" required>

                <label for="ano">Ano:</label>
                <input type="number" id="ano" name="ano" value="<?= $filme['ano'] ?>" min="1888" required>

                <label>Duração:</label>
                <div style="display: flex; gap: 10px;">
                    <input type="number" id="horas" name="horas" placeholder="Horas" value="<?= $filme['duracao_horas'] ?>" min="0" style="flex: 1;" required>
                    <input type="number" id="minutos" name="minutos" placeholder="Minutos" value="<?= $filme['duracao_minutos'] ?>" min="0" max="59" style="flex: 1;" required>
                </div>

                <label for="idade_recomendada">Idade Recomendada:</label>
                    <select name="idade_recomendada" id="idade_recomendada" required>
                        <option value="">-- Selecionar --</option>
                        <?php
                            $idades = [3, 6, 12, 14, 16, 18];
                                foreach ($idades as $idade) {
                                $selected = ($filme['idade_recomendada'] == $idade) ? 'selected' : '';
                                echo "<option value=\"$idade\" $selected>M/$idade</option>";
                                }
                        ?>
                    </select>

                <label for="imdb">Nota IMDb:</label>
                <input type="number" step="0.1" id="imdb" name="imdb" value="<?= $filme['imdb'] ?>" required>

                <label for="rotten">Nota Rotten Tomatoes (%):</label>
                <input type="number" id="rotten" name="rotten" value="<?= $filme['rotten_tomatoes'] ?>" min="0" max="100" required>

                <label for="genero1">Gênero 1:</label>
                <input type="text" id="genero1" name="genero1" value="<?= $filme['genero1'] ?>" required>
                <label for="genero2">Gênero 2:</label>
                <input type="text" id="genero2" name="genero2" value="<?= $filme['genero2'] ?>">
                <label for="genero3">Gênero 3:</label>
                <input type="text" id="genero3" name="genero3" value="<?= $filme['genero3'] ?>">

                <label for="ator1">Ator 1:</label>
                <input type="text" id="ator1" name="ator1" value="<?= htmlspecialchars($filme['ator1']) ?>" required>

                <label for="ator2">Ator 2:</label>
                <input type="text" id="ator2" name="ator2" value="<?= htmlspecialchars($filme['ator2']) ?>">

                <label for="ator3">Ator 3:</label>
                <input type="text" id="ator3" name="ator3" value="<?= htmlspecialchars($filme['ator3']) ?>">

                <label for="sinopse">Sinopse:</label>
                <textarea id="sinopse" name="sinopse" rows="5" required><?= htmlspecialchars($filme['sinopse']) ?></textarea>

                <label for="preco">Preço (€):</label>
                <input type="number" step="0.1" id="preco" name="preco" value="<?= $filme['preco'] ?>" required>

                <label for="imagem">Imagem do Filme:</label>
                <input type="file" id="imagem" name="imagem" accept="image/*">
                <p>Imagem atual:</p>
                <img src="<?= $filme['foto'] ?>" alt="Imagem atual" width="150">

                <div class="botoes-edicao">
                    <button type="submit" formaction="eliminarFilme.php" formmethod="POST" name="eliminar" value="1" class="botao-eliminar-func">Eliminar</button>
                    <button type="submit" class="botao-confirmar-func">Confirmar</button>
                </div>
            </form>
        </div>
    </div>
</main>

<footer>
    <div class="social-links">
        <a href="#">Instagram</a>
        <a href="#">Twitter</a>
        <a href="#">Facebook</a>
    </div>
</footer>
</body>
</html>

<?php $conn->close(); ?>

