import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import { useNavigate } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const Produto = () => {
  const tipoUtilizador = TipoUtilizador;
  const navigate = useNavigate();
  const goToCarrinho = () => {
    if (tipoUtilizador === 4) {
      navigate('/Carrinho');
    }
    else {
      navigate('/Login')
    }
  };
  return (
    <>
      <meta charSet="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Dune</title>
      <Header tipoUtilizador={tipoUtilizador} />
      <div className="background-image" />
      <div id="montra">
        <img src="imagens/Duna.jpg" alt="Filme" />
      </div>
      <div id="descricao">
        <div className='titulo'>
          <h2> Dune: Part 2 </h2>
        </div>
        <div id="caixa-descricao">
          <div className="filme-detalhes">
            <span>
              <strong>Duração:</strong> 2h48min
            </span>
            <span>
              <strong>Ano:</strong> 2024
            </span>
            <span>
              <strong>Gênero:</strong> Drama; Aventura; Sci-Fi
            </span>
          </div>
          <div className="filme-detalhes">
            <span className="atores">
              <strong>Atores:</strong> Pessoa1, Pessoa2, Pessoa3...
            </span>
            <span>
              <strong>IMDB:</strong> XX
            </span>
            <span>
              <strong>Rotten Tomatoes:</strong> XX
            </span>
          </div>
          <div className="filme-sinopse">
            <strong>Sinopse:</strong>
            <p>
              Aqui vai o texto da sinopse do filme. O objetivo é que ele tenha uma
              largura fixa e que a leitura seja mais confortável para o usuário.
            </p>
          </div>
        </div>
        <div className="filme-aluguer">
          <span className="filme-preco">5,00€</span>
          <button className="botao-alugar" onClick={goToCarrinho}>ALUGAR</button>
        </div>
      </div>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>
  )
}

export default Produto
