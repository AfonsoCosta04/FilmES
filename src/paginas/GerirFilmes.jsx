import React from 'react';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import './styles.css';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const GerirFilmes = () => {
    const tipoUtilizador = TipoUtilizador;

    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S.</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <main>
                <div className="background-image">
                    <div className="opcao-gerir-admin-filme">
                        <Link
                            to="/PaginaInicialAdmin"
                            className="botao-voltar-edicao"
                            title="Voltar ao painel inicial"
                        >
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
                                    strokeWidth={3}
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                />
                            </svg>
                        </Link>
                        <div className="opcao-gerir-admin-alt">
                            <Link to="/EscolhaFilme">
                                <p>Editar Filme</p>
                            </Link>
                        </div>
                        <div className="opcao-gerir-admin-alt">
                            <Link to="/AdicionarFilme">
                                <p>Adicionar Filme</p>
                            </Link>
                        </div>
                        <div className="opcao-gerir-admin-alt">
                            <Link to="/MudarEstado">
                                <p>Alterar Disponibilidade</p>
                            </Link>
                        </div>
                    </div>
                </div>
            </main>

            <Footer tipoUtilizador={tipoUtilizador} />
        </>
    )
}

export default GerirFilmes
