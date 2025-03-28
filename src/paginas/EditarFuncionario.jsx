import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const EditarFuncionario = () => {
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
                    <div className="caixa-formulario-editar-func">
                        <h2>Editar Funcionário</h2>
                        <Link to="/SelecionarFuncionario" className="botao-voltar-edicao">
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
                                placeholder="Ana Lima"
                                required
                            />
                            <label htmlFor="cargo">Permições:</label>
                            <input
                                type="text"
                                id="cargo"
                                name="permicao"
                                placeholder="Leitura"
                                required
                            />
                            <label htmlFor="email">Email:</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                placeholder="anafilme.s.@gmail.com"
                                required
                            />
                            <label htmlFor="telefone">Telefone:</label>
                            <input
                                type="tel"
                                id="telefone"
                                name="telefone"
                                placeholder="(+351) 968 123 456"
                            />
                            <label htmlFor="senha">Senha Atual:</label>
                            <input
                                type="password"
                                id="senha"
                                name="senha"
                                placeholder="123Oliveira4"
                                required
                            />
                            <label htmlFor="confirmar">Confirmar Senha:</label>
                            <input
                                type="password"
                                id="confirmar"
                                name="confirmar"
                                placeholder="***********"
                                required
                            />
                            <div className="botoes-edicao">
                                <button type="button" className="botao-eliminar-func">
                                    Eliminar
                                </button>
                                <button type="submit" className="botao-confirmar-func">
                                    Confirmar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>

    )
}

export default EditarFuncionario
