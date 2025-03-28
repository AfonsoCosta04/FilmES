import React from 'react';
import './styles.css';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const AdicionarFuncionario = () => {
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
                    <div className="caixa-formulario-adicionar-func">
                        <h2>Adicionar Funcionário</h2>
                        <Link to="/GerirFuncionario" className="botao-voltar-edicao">
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
                        <form>
                            <label htmlFor="nome">Nome:</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                placeholder="nome"
                                required
                            />
                            <label htmlFor="cargo">Permições:</label>
                            <input
                                type="text"
                                id="cargo"
                                name="permicao"
                                placeholder="permições"
                                required
                            />
                            <label htmlFor="email">Email:</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                placeholder="exemplo@gmail.com"
                                required
                            />
                            <label htmlFor="telefone">Telefone:</label>
                            <input
                                type="tel"
                                id="telefone"
                                name="telefone"
                                placeholder="(+351) 123 456 789"
                                required
                            />
                            <label htmlFor="senha">Senha:</label>
                            <input
                                type="password"
                                id="senha"
                                name="senha"
                                placeholder="password"
                                required
                            />
                            <label htmlFor="confirmar">Confirmar Senha:</label>
                            <input
                                type="password"
                                id="confirmar"
                                name="confirmar"
                                placeholder="confirme password"
                                required
                            />
                            <button type="submit">Adicionar</button>
                        </form>
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>


    )
}

export default AdicionarFuncionario
