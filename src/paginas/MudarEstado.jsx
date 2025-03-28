import React from 'react';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import './styles.css';
import { Link } from 'react-router-dom';
import EstadoFilme from '../Componentes/EstadoFilme';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const MudarEstado = () => {
    const tipoUtilizador = TipoUtilizador;
    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S.</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <main>
                <div className="background-pagina-estado">
                    <div className="box-filmes-estado">
                        {tipoUtilizador === 1  && (
                        <Link to="/GerirFilmes" className="botao-voltar-estado">
                            <svg
                                width={26}
                                height={26}
                                viewBox="0 0 24 24"
                                fill="none"
                                xmlns="http://www.w3.org/2000/svg"
                            >
                                <path
                                    d="M15 18L9 12L15 6"
                                    stroke="white"
                                    strokeWidth={2}
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </Link>
                        )}
                         {tipoUtilizador === 2  && (
                        <Link to="/PaginaInicialFuncionario" className="botao-voltar-estado">
                            <svg
                                width={26}
                                height={26}
                                viewBox="0 0 24 24"
                                fill="none"
                                xmlns="http://www.w3.org/2000/svg"
                            >
                                <path
                                    d="M15 18L9 12L15 6"
                                    stroke="white"
                                    strokeWidth={2}
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </Link>
                        )}
                        <h2>Alterar disponibilidade</h2>
                        <EstadoFilme
                            filme={{
                                imagem: 'imagens/Duna.jpg',
                                titulo: 'Dune: Part 2',
                                disponivel: true,
                                style: { marginTop: 60 }
                            }}
                        />
                        <EstadoFilme
                            filme={{
                                imagem: 'imagens/fight club.jpeg',
                                titulo: 'Fight Club',
                                disponivel: false
                            }}
                        />
                        <EstadoFilme
                            filme={{
                                imagem: 'imagens/71-uZ6tkY8L._AC_SL1500_.jpg',
                                titulo: 'Interstellar',
                                disponivel: true
                            }}
                        />
                        <EstadoFilme
                            filme={{
                                imagem: 'imagens/la la land.jpg',
                                titulo: 'La La Land',
                                disponivel: true
                            }}
                        />
                        <EstadoFilme
                            filme={{
                                imagem: 'imagens/deadpool.jpeg',
                                titulo: 'Deadpool & Wolverine',
                                disponivel: false
                            }}
                        />
                        {/* Espaço extra para scroll se necessário */}
                        <div />
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>
    )
}

export default MudarEstado
