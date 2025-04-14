<?php
include "databasecon.php";
session_start();
if (!isset($_GET["id"])) {
    echo "<script>alert('Filme não especificado.'); window.location.href='catalogonovo.html';</script>";
    exit();
}

$id_filme = $_GET["id"];

$stmt = $conn->prepare("SELECT * FROM Filme WHERE id_filme = ?");
$stmt->bind_param("i", $id_filme);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows !== 1) {
    echo "<script>alert('Filme não encontrado.'); window.location.href='catalogonovo.html';</script>";
    exit();
}

$filme = $result->fetch_assoc();
?>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= htmlspecialchars($filme['titulo']) ?></title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/x-icon" href="imagens/favicon.ico">
</head>
<body>
<header>
    <a href="PagInicial.html"><div class="logo">
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S">
    </div></a>
    <nav>
        <ul>
            <li><a href="catalogonovo.html">Catálogo</a></li>
            <li><a href="#">Localização</a></li>
        </ul>
    </nav>
    <div class="auth">
        <?php if (isset($_SESSION['utilizador_nome'])): ?>
            <span><?= htmlspecialchars($_SESSION['utilizador_nome']) ?></span>
        <?php else: ?>
            <a href="login.php">Login</a> |
            <a href="signup.html">Sign Up</a>
        <?php endif; ?>
    </div>
</header>

<div class="background-image"></div>

<div id="montra">
    <img src="<?= htmlspecialchars($filme['foto']) ?>" alt="Capa do Filme">
</div>

<div id="descricao">
    <div id="caixa-descricao">
        <strong id="titulo"><?= htmlspecialchars($filme['titulo']) ?></strong>
        <br>
        <div class="filme-detalhes">
            <span><strong>Duração:</strong> <?= $filme['duracao_horas'] . 'h' . str_pad($filme['duracao_minutos'], 2, '0', STR_PAD_LEFT) ?></span>
            <span><strong>Ano:</strong> <?= $filme['ano'] ?></span>
            <span><strong>Gênero:</strong>
                <?= implode("; ", array_filter([$filme['genero1'], $filme['genero2'], $filme['genero3']])) ?>
            </span>
        </div>
        <div class="filme-detalhes">
            <span class="atores"><strong>Atores:</strong> <?= implode(", ", array_filter([$filme['ator1'], $filme['ator2'], $filme['ator3']])) ?></span>
            <span><strong>IMDB:</strong> <?= $filme['imdb'] ?></span>
            <span><strong>Rotten Tomatoes:</strong> <?= $filme['rotten_tomatoes'] ?>%</span>
        </div>
        <br>
        <div class="filme-sinopse">
            <strong>Sinopse:</strong> 
            <p><?= nl2br(htmlspecialchars($filme['sinopse'])) ?></p>
        </div>
    </div>
    <div class="filme-idade-recomendada">
    <?php
        if (!empty($filme['idade_recomendada'])) {
            echo '<img src="imagens/m' . $filme['idade_recomendada'] . '.png" class="etiqueta-idade" alt="Classificação etária">';
        }
    ?>
    </div>
    <div class="filme-aluguer">
        <span class="filme-preco"><?= number_format($filme['preco'], 2, ',', '.') ?>€</span>
        <a href="verificaIdade.php?id=<?= $filme['id_filme'] ?>">
            <button class="botao-alugar">ALUGAR</button>
        </a>
    </div>
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