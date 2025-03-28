import React from 'react'
import Footer from '../Componentes/Footer'
import Header from '../Componentes/Header'
import './styles.css'
import { Link } from 'react-router-dom'
import TipoUtilizador from '../Componentes/TipoUtilizador'

const PaginaInicialFuncionario = () => {
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

export default PaginaInicialFuncionario
