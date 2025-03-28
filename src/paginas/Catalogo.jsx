import React from 'react';
import Header from '../Componentes/Header.jsx';
import Footer from '../Componentes/Footer';
import './styles.css';
import Filme from '../Componentes/Filme.jsx';
import TipoUtilizador from '../Componentes/TipoUtilizador.jsx';

const Catalogo = () => {
  const tipoUtilizador = TipoUtilizador;
  return (
    <>
      <meta charSet="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>FILME.S.</title>
      <Header tipoUtilizador={tipoUtilizador} />
      <main className='catalogo-body'>
        <div className="catalogo-container">
          <aside className="catalogo-caixa-filtros">
            <h2>Filtros</h2>
            <div className="catalogo-filtro">
              <h3>Gênero</h3>
              <ul>
                <li>
                  <label>
                    <input type="radio" name="genero" defaultValue="acao" /> Ação
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="genero" defaultValue="comedia" />{" "}
                    Comédia
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="genero" defaultValue="drama" /> Drama
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="genero" defaultValue="romance" />{" "}
                    Romance
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="genero" defaultValue="terror" /> Terror
                  </label>
                </li>
              </ul>
            </div>
            <div className="catalogo-filtro">
              <h3>Ano</h3>
              <ul>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2025} /> 2025
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2024} /> 2024
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2023} /> 2023
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2022} /> 2022
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2021} /> 2021
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue={2020} /> 2020
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="ano" defaultValue="older" /> Mais antigo
                  </label>
                </li>
              </ul>
            </div>
            <div className="catalogo-filtro">
              <h3>Duração</h3>
              <ul>
                <li>
                  <label>
                    <input type="radio" name="duracao" defaultValue="-90min" /> -90min
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="duracao" defaultValue="90min-120min" />{" "}
                    90min-120min
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="duracao" defaultValue="+120min" />{" "}
                    +120min
                  </label>
                </li>
              </ul>
            </div>
            <button className="botao-filtrar">Filtrar</button>
          </aside>
          <section className="catalogo-filmes">
            <Filme
              filme={{
                nome: 'Deadpool & Wolverine',
                imagem: 'imagens/deadpool.jpeg',
                disponivel: false,
                tipoUtilizador: tipoUtilizador
              }}
            />
            <Filme
              filme={{
                nome: 'Dune: Part 2',
                imagem: 'imagens/Duna.jpg',
                disponivel: true,
                tipoUtilizador: tipoUtilizador
              }}
            />
            <Filme
              filme={{
                nome: 'Interstellar',
                imagem: 'imagens/71-uZ6tkY8L._AC_SL1500_.jpg',
                disponivel: true,
                tipoUtilizador: tipoUtilizador
              }}
            />
            <Filme
              filme={{
                nome: 'Fight Club',
                imagem: 'imagens/fight club.jpeg',
                disponivel: false,
                tipoUtilizador: tipoUtilizador
              }}
            />
          </section>
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>
  )
}

export default Catalogo
