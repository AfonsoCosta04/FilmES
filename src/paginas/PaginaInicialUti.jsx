import { useNavigate } from 'react-router-dom';
import './styles.css';
import Header from '../Componentes/Header.jsx';
import Footer from '../Componentes/Footer.jsx';
import TipoUtilizador from '../Componentes/TipoUtilizador.jsx';
import Destaques from '../Componentes/Destaques.jsx';


const PaginaInicialUti = () => {
  const tipoUtilizador = TipoUtilizador;
  const navigate = useNavigate();

  const goToCatalogo = () => {
    navigate('/Catalogo');
  };
  const filmes = [
    { titulo: 'Interstellar', imagem: 'imagens/71-uZ6tkY8L._AC_SL1500_.jpg' },
    { titulo: 'Dune', imagem: 'imagens/Duna.jpg' },
    { titulo: 'Deadpool', imagem: 'imagens/deadpool.jpeg' },
    { titulo: 'Fight Club', imagem: 'imagens/fight club.jpeg' },
    { titulo: 'La La Land', imagem: 'imagens/la la land.jpg' },
  ];
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
          <Destaques filmes={filmes} />
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>
  );
}

export default PaginaInicialUti;