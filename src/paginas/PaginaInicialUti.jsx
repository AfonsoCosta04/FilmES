import { useNavigate } from 'react-router-dom';
import './styles.css';
import Header from '../Componentes/Header.jsx';
import Footer from '../Componentes/Footer.jsx';
import TipoUtilizador from '../Componentes/TipoUtilizador.jsx';


const PaginaInicialUti = () => {
  const tipoUtilizador = TipoUtilizador;
  const navigate = useNavigate();

  const goToCatalogo = () => {
    navigate('/Catalogo');
  };
  return (
    <>
      <meta charSet="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>FILME.S.</title>
      <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
      <Header tipoUtilizador={tipoUtilizador} />
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
            <img src="imagens/Duna.jpg" alt="Dune 2" />
            <img src="imagens/deadpool.jpeg" alt="Deadpool Wolverine" />
            <img src="imagens/fight club.jpeg" alt="Fight Club" />
            <img src="imagens/la la land.jpg" alt="La La Land" />
          </div>
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>
  );
}

export default PaginaInicialUti;