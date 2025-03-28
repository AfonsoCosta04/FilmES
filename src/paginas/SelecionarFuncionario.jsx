import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import { Link } from 'react-router-dom';
import Funcionario from '../Componentes/Funcionario';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const SelecionarFuncionario = () => {
    const tipoUtilizador = TipoUtilizador;
    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S.</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <main className="funcionarios-background">
                <section className="funcionarios-container">
                    <Link to="/GerirFuncionario" className="botao-voltar">
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
                    <h2 className="funcionarios-titulo">Funcionários</h2>
                    <Funcionario
                        funcionario={{
                            nome: 'Ana Lima',
                            cargo: 'Leitura',
                            id: '001'
                        }}
                    />
                    <Funcionario
                        funcionario={{
                            nome: 'Bruno Rocha',
                            cargo: 'Criação',
                            id: '002'
                        }}
                    />
                    <Funcionario
                        funcionario={{
                            nome: 'Camila Nunes',
                            cargo: 'Eliminação',
                            id: '003'
                        }}
                    />
                </section>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>

    )
}

export default SelecionarFuncionario
