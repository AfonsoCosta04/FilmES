import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const Carrinho = () => {
  const tipoUtilizador = TipoUtilizador;
  return (
    <>
      <meta charSet="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Carrinho</title>
      <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
      <Header tipoUtilizador={tipoUtilizador} />
      <main>
        <div className="background-image">
          <div className="caixa-carrinho">
            <div className="caixa-carrinho-produto">
              <div className="caixa-carrinho-imagem">
                <img src="imagens/Duna.jpg" alt="Duna" />
              </div>
              <div className="caixa-carrinho-descricao">
                <p>Dune: Part 2 - 5,00€</p>
              </div>
            </div>
            {/* Soma total */}
            <div className="caixa-total">
              <span>Total:</span>
              <span className="preco-total">5,00€</span>
            </div>
          </div>
          {/* Opções de pagamento (agora separadas e encostadas à direita) */}
          <div className="caixa-pagamento">
            <p>Escolha um método de pagamento:</p>
            <label>
              <input type="radio" name="pagamento" defaultValue="cartao" /> Cartão
              de Crédito
            </label>
            <label>
              <input type="radio" name="pagamento" defaultValue="paypal" /> PayPal
            </label>
            <label>
              <input type="radio" name="pagamento" defaultValue="mbway" /> MB Way
            </label>
            {/* Botão de confirmação */}
            <button className="botao-confirmar">Confirmar</button>
          </div>
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />

    </>

  )
}

export default Carrinho
