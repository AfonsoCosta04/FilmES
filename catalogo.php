<?php
include "databasecon.php";
session_start();
$result = $conn->query("SELECT * FROM Filme");
?>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FILME.S.</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/x-icon" href="imagens/favicon.ico">
</head>
<body class="catalogo-body">
<header>
    <a href="PagInicial.html">
        <div class="logo">
            <img src="imagens/FilmE.S..png" alt="Logo FILME.S">
        </div>
    </a>
    <nav>
        <ul>
            <li><a href="catalogonovo.php">Catálogo</a></li>
            <li><a href="#">Localização</a></li>
        </ul>
    </nav>
    <div class="auth"> 
        <?php if (isset($_SESSION['utilizador_nome'])): ?>
            <span><?= htmlspecialchars($_SESSION['utilizador_nome']) ?></span> |
            <a href="logout.php">Logout</a>
        <?php else: ?>
            <a href="login.php">Login</a> |
            <a href="signup.html">Sign Up</a>
        <?php endif; ?>
        </div>
</header>

<div class="catalogo-container">
    <aside class="catalogo-caixa-filtros">
        <h2>Filtros</h2>
        <!-- Filtros iguais -->
        <div class="catalogo-filtro">
            <h3>Gênero</h3>
            <ul>
                <li><label><input type="radio" name="genero" value="acao"> Ação</label></li>
                <li><label><input type="radio" name="genero" value="comedia"> Comédia</label></li>
                <li><label><input type="radio" name="genero" value="drama"> Drama</label></li>
                <li><label><input type="radio" name="genero" value="romance"> Romance</label></li>
                <li><label><input type="radio" name="genero" value="terror"> Terror</label></li>
            </ul>
        </div>
        <div class="catalogo-filtro">
            <h3>Ano</h3>
            <ul>
                <li><label><input type="radio" name="ano" value="2025"> 2025</label></li>
                <li><label><input type="radio" name="ano" value="2024"> 2024</label></li>
                <li><label><input type="radio" name="ano" value="2023"> 2023</label></li>
                <li><label><input type="radio" name="ano" value="2022"> 2022</label></li>
                <li><label><input type="radio" name="ano" value="2021"> 2021</label></li>
                <li><label><input type="radio" name="ano" value="2020"> 2020</label></li>
                <li><label><input type="radio" name="ano" value="older"> Mais antigo</label></li>
            </ul>
        </div>
        <div class="catalogo-filtro">
            <h3>Duração</h3>
            <ul>
                <li><label><input type="radio" name="duracao" value="-90min"> -90min</label></li>
                <li><label><input type="radio" name="duracao" value="90min-120min"> 90min-120min</label></li>
                <li><label><input type="radio" name="duracao" value="+120min"> +120min</label></li>
            </ul>
        </div>
        <button class="botao-filtrar">Filtrar</button>
    </aside>

    <section class="catalogo-filmes">
        <?php while ($filme = $result->fetch_assoc()): ?>
            <div class="catalogo-filme">
                <a href="produto.php?id=<?= $filme['id_filme'] ?>" class="catalogo-link">
                    <img src="<?= htmlspecialchars($filme['foto']) ?>" alt="<?= htmlspecialchars($filme['titulo']) ?>">
                    <div class="catalogo-info">
                        <h3><?= htmlspecialchars($filme['titulo']) ?></h3>
                        <span class="catalogo-status">
                            <?= $filme['disponivel'] ? '🟢 Disponível' : '🔴 Indisponível' ?>
                        </span>
                    </div>
                </a>
                <?php if ($filme['disponivel']): ?>
                    <a href="produto.php" class="catalogo-ver-filme disponivel">Ver Filme</a>
                <?php else: ?>
                    <button class="catalogo-ver-filme indisponivel">Indisponível</button>
                <?php endif; ?>
            </div>
        <?php endwhile; ?>
    </section>
</div>

<footer>
    <div class="social-links">
        <a href="#">Instagram</a>
        <a href="#">Twitter</a>
        <a href="#">Facebook</a>
    </div>
</footer>
</body>
</html>