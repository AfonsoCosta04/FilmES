<?php
include 'databasecon.php';
$result = $conn->query("SELECT * FROM Funcionario");
?>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>FILME.S. - Funcionários</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    .funcionarios-background {
      height: 100vh;
      overflow: hidden;
      display: flex;
      justify-content: center;
      align-items: center;
      background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.75)), url('imagens/background.png') center/cover no-repeat;
      background-attachment: fixed;
      padding: 100px 0;
    }
    .funcionarios-container {
      background-color: rgba(0, 0, 0, 0.95);
      border-radius: 15px;
      padding: 30px;
      width: 900px;
      max-height: 80vh;
      overflow-y: auto;
      box-shadow: 0 0 20px rgba(255, 255, 255, 0.1);
      display: flex;
      flex-direction: column;
      gap: 20px;
    }
    .voltar-wrapper {
    position: relative;
    top: -25px;
    left: -450px;
    z-index: 10;
    }
    .botao-voltar {
      position: absolute;
      top: 20px;
      left: 20px;
      width: 50px;
      height: 50px;
      background: linear-gradient(45deg, #ff5733, #ffbd33);
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;
      box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.4);
      transition: transform 0.2s ease-in-out;
      z-index: 1;
      text-decoration: none;
    }
    .botao-voltar:hover {
      transform: scale(1.1);
      background: linear-gradient(45deg, #ffbd33, #ff5733);
    }
  </style>
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

  <main class="funcionarios-background">
    <div class="funcionarios-container">
        <div class="voltar-wrapper">
            <a href="escolhaFuncAdmin.html" class="botao-voltar">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </a>
        </div>

      <h2 class="funcionarios-titulo">Funcionários</h2>

      <?php while ($func = $result->fetch_assoc()): ?>
        <div class="funcionarios-caixa">
          <div class="info-funcionario">
            <span><strong>Nome:</strong> <?= htmlspecialchars($func['nome_funcionario']) ?></span>
            <span><strong>Permissões:</strong>
              <?php
                $permissoes = [];
                if ($func['perm_leitura']) $permissoes[] = "Leitura";
                if ($func['perm_criacao']) $permissoes[] = "Criação";
                if ($func['perm_edicao'])  $permissoes[] = "Edição";
                echo $permissoes ? implode(", ", $permissoes) : "Nenhuma";
              ?>
            </span>
            <span><strong>ID:</strong> <?= str_pad($func['id_funcionario'], 3, "0", STR_PAD_LEFT) ?></span>
          </div>
          <form action="editarFuncionario.php" method="GET">
            <input type="hidden" name="id" value="<?= $func['id_funcionario'] ?>">
            <button class="botao-editar-func" type="submit">Editar</button>
          </form>
        </div>
      <?php endwhile; ?>
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
