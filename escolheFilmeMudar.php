<?php
include 'databasecon.php';
$resultado = $conn->query("SELECT id_filme, titulo, foto FROM Filme");
?>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>FILME.S.</title>
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
    <div class="background-pagina-estado">
        <div class="box-filmes-estado">

            <a href="escolhaFilmeAdmin.html" class="botao-voltar-estado">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>

            <h2 style= "left: 390px; position: relative;">Escolha filme a editar:</h2>

            <?php while ($filme = $resultado->fetch_assoc()): ?>
                <a href="editarFilme.php?id=<?= $filme['id_filme'] ?>">
                    <div class="box-montra-filme-disponibilidade" style="margin-top: 60px;">
                        <img src="<?= htmlspecialchars($filme['foto']) ?>" alt="<?= htmlspecialchars($filme['titulo']) ?>">
                        <div class="catalogo-info">
                            <h3><?= htmlspecialchars($filme['titulo']) ?></h3>
                        </div>
                    </div>
                </a>
            <?php endwhile; ?>

            <div></div> <!-- scroll extra -->
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