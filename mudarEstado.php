<?php
include 'databasecon.php';

$result = $conn->query("SELECT id_filme, titulo, foto, disponivel FROM Filme");
?>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>FILME.S. - Estado</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/x-icon" href="imagens/favicon.ico">
    <script>
        function toggleEstado(id) {
            fetch('trocarEstado.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'id=' + id
            })
            .then(response => {
                if (!response.ok) {
                    alert('Erro ao mudar estado!');
                }
            });
        }
    </script>
</head>
<body>
<header>
    <div class="logo">
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S">
    </div>
    <div class="role-admin"><p><strong>ADMIN</strong></p></div>
</header>

<main>
    <div class="background-pagina-estado">
        
        <div class="box-filmes-estado">
            <a href="escolhaFilmeAdmin.html" class="botao-voltar-estado">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>

            <?php while ($filme = $result->fetch_assoc()): ?>
                <div class="box-montra-filme-disponibilidade">
                    <img src="<?= $filme['foto'] ?>" alt="<?= htmlspecialchars($filme['titulo']) ?>">
                    <div class="catalogo-info">
                        <h3><?= htmlspecialchars($filme['titulo']) ?></h3>
                    </div>
                    <label class="switch">
                        <input type="checkbox"
                            <?= $filme['disponivel'] ? 'checked' : '' ?>
                            onchange="toggleEstado(<?= $filme['id_filme'] ?>)">
                        <span class="slider round"></span>
                    </label>
                </div>
            <?php endwhile; ?>

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
