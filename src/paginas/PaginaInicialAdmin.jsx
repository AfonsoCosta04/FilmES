import React from 'react';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import './styles.css';
import { useNavigate } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const PaginaInicialAdmin = () => {
    const tipoUtilizador = TipoUtilizador;
    const navigate = useNavigate();
    const goToFilmes = () => {
        navigate('/GerirFilmes')
    }
    const goToFunc = () => {
        navigate('/GerirFuncionario')
    }
    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S.</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <main>
                <div className="background-image">
                    <div className="opcao-gerir-admin">
                        <div className="opcao-gerir-admin-alt" onClick={goToFunc}>
                            <p>Gerir Funcionário</p>
                        </div>
                        <div className="opcao-gerir-admin-alt" onClick={goToFilmes}>
                            <p>Gerir Filmes</p>
                        </div>
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>

    )
}

export default PaginaInicialAdmin
