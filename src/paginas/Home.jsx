import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import './styles.css';
import Header from '../Componentes/Header';


function Home() {
  const navigate = useNavigate();

  const goToCatalogo = () => {
    navigate('/Catalogo');
  };
  const goToHome = () => {
    navigate('/')
  }
  const goToLocalizacao =() => {
    navigate('/Localizacao')
  }
  return (
    <>
  <meta charSet="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>FILME.S.</title>
  <link rel="stylesheet" href="styles.css" />
  <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
  {/*<header>
      <div className="logo" onClick={goToHome} style={{cursor: 'pointer'}}>
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
      </div>
    <nav>
      <ul>
        <li onClick={goToCatalogo} style={{ cursor: 'pointer' }}>Catálogo</li>
        <li onClick={goToLocalizacao} style={{cursor : 'pointer'}}>Localizacao</li>
      </ul>
    </nav>
    <div className="auth">
      <Link to = '/Login'>Login</Link> | <Link to = '/SignUp'>Sign Up</Link>
    </div>
  </header>*/}
  <Header/>
  <main>
    <div className="background-image">
      <div className="search-container">
        <div className="search-box">
          <input type="text" placeholder="Pesquisar filmes..." />
            <button onClick={goToCatalogo}>Pesquisar</button>
        </div>
          <button className="catalog-button" onClick={goToCatalogo}>Catálogo</button>
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
      <Link>Instagram</Link>
      <Link>Twitter</Link>
      <Link>Facebook</Link>
    </div>
  </footer>
</>
  );
}

export default Home;