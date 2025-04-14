<?php
include "databasecon.php";

if (!isset($_GET['id'])) {
    echo "<script>alert('Funcionário não encontrado.'); window.location.href='selecionarFuncionario.php';</script>";
    exit();
}

$id = $_GET['id'];

$stmt = $conn->prepare("SELECT * FROM Funcionario WHERE id_funcionario = ?");
$stmt->bind_param("i", $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows !== 1) {
    echo "<script>alert('Funcionário não encontrado.'); window.location.href='selecionarFuncionario.php';</script>";
    exit();
}

$func = $result->fetch_assoc();
?>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Editar Funcionário</title>
    <link rel="stylesheet" href="styles.css">
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
        <div class="caixa-formulario-editar-func">
            <h2>Editar Funcionário</h2>
            <a href="selecionarFuncionario.php" class="botao-voltar-edicao">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>

            <form action="guardarEdicaoFuncionario.php" method="POST">
                <input type="hidden" name="id" value="<?= $func['id_funcionario'] ?>">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="<?= htmlspecialchars($func['nome_funcionario']) ?>">

                <label>Permissões:</label>
                <div class="permissoes-checkboxes">
                    <label><input type="checkbox" name="leitura" value="1" <?= $func['perm_leitura'] ? 'checked' : '' ?>> Leitura</label>
                    <label><input type="checkbox" name="criacao" value="1" <?= $func['perm_criacao'] ? 'checked' : '' ?>> Criação</label>
                    <label><input type="checkbox" name="edicao" value="1" <?= $func['perm_edicao'] ? 'checked' : '' ?>> Edição</label>
                </div>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<?= htmlspecialchars($func['email_funcionario']) ?>">

                <label for="telefone">Telefone:</label>
                <input type="tel" id="telefone" name="telefone" value="<?= htmlspecialchars($func['telefone']) ?>">

                <label for="senha">Senha Atual:</label>
                <input type="password" id="senha" name="senha">

                <label for="confirmar">Confirmar Senha:</label>
                <input type="password" id="confirmar" name="confirmar">

                <div class="botoes-edicao">
                    <button type="submit" formaction="eliminarFuncionario.php" class="botao-eliminar-func">Eliminar</button>
                    <button type="submit" formaction="guardarEdicaoFuncionario.php" class="botao-confirmar-func">Confirmar</button>
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