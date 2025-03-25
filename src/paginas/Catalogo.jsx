import React from 'react'

const Catalogo = () => {
  return (
    <>
  <meta charSet="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>FILME.S.</title>
  <link rel="stylesheet" href="styles.css" />
  <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
  <header>
    <a href="PagInicial.html">
      <div className="logo">
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
      </div>
    </a>
    <nav>
      <ul>
        <li>
          <a href="catalogonovo.html">Catálogo</a>
        </li>
        <li>
          <a href="#">Localização</a>
        </li>
      </ul>
    </nav>
    <div className="auth">
      <a href="login.html">Login</a> | <a href="#">Sign Up</a>
    </div>
  </header>
  <main>
    <div className="background-image">
      <div className="search-container">
        <div className="search-box">
          <input type="text" placeholder="Pesquisar filmes..." />
          <a href="catalogonovo.html">
            <button>Pesquisar</button>
          </a>
        </div>
        <a href="catalogonovo.html">
          <button className="catalog-button">Catálogo</button>
        </a>
      </div>
      <div className="movie-slider">
        <img src="imagens/71-uZ6tkY8L._AC_SL1500_.jpg" alt="Interstellar" />
        <img src="imagens/Duna.jpg" alt="Dune" />
        <img src="imagens/deadpool.jpeg" alt="Deadpool Wolverine" />
        <img src="imagens/fight club.jpeg" alt="Fight Club" />
        <img src="imagens/la la land.jpg" alt="La La Land" />
      </div>
    </div>
  </main>
  <footer>
    <div className="social-links">
      <a href="#">Instagram</a>
      <a href="#">Twitter</a>
      <a href="#">Facebook</a>
    </div>
  </footer>
</>
  )
}

export default Catalogo
