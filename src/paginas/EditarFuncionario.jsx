import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';
import PopUp from '../Componentes/PopUp';
import { useState } from 'react';
import { useRef } from 'react';

const EditarFuncionario = () => {
    const tipoUtilizador = TipoUtilizador;

    const [mostrarPopUp, setMostrarPopUp] = useState(false);
    const [acaoConfirmar, setAcaoConfirmar] = useState(() => () => { });
    const [conteudoPopUp, setConteudoPopUp] = useState(null);

    const [mostrarSegundoPopUp, setMostrarSegundoPopUp] = useState(false);
    const [contador, setContador] = useState(5);
    const [podeConfirmar, setPodeConfirmar] = useState(false);

    const intervalRef = useRef(null);

    const abrirPopUp = (acao, conteudo) => {
        setAcaoConfirmar(() => acao);
        setConteudoPopUp(conteudo);
        setMostrarPopUp(true);
    };

    const abrirSegundoPopUp = (acao) => {
        setMostrarSegundoPopUp(true);
        setPodeConfirmar(false);
        setContador(5);
        setAcaoConfirmar(() => acao);

        if (intervalRef.current) {
            clearInterval(intervalRef.current);
        }

        intervalRef.current = setInterval(() => {
            setContador(prev => {
                if (prev === 1) {
                    clearInterval(intervalRef.current);
                    intervalRef.current = null;
                    setPodeConfirmar(true);
                    return 0;
                }
                return prev - 1;
            });
        }, 1000);
    };
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
                                <button type="button" className="botao-eliminar-func" onClick={() => abrirPopUp(() => abrirSegundoPopUp(() =>
                                    console.log("Funcionario apagado")), <p style={{ color: 'black', fontSize: '16px', marginBottom: '20px' }}>
                                    Tem a certeza que deseja eliminar este funcionário? </p>)}>
                                    Eliminar
                                </button>
                                <PopUp
                                    visivel={mostrarPopUp}
                                    fechar={() => setMostrarPopUp(false)}
                                    onConfirm={acaoConfirmar}
                                    yesColor="red"
                                    noColor="gray"
                                >
                                    {conteudoPopUp}
                                </PopUp>
                                <PopUp
                                    visivel={mostrarSegundoPopUp}
                                    fechar={() => setMostrarSegundoPopUp(false)}
                                    onConfirm={() => {
                                        acaoConfirmar();
                                        setMostrarSegundoPopUp(false);
                                    }}
                                    yesColor={podeConfirmar ? 'red' : 'gray'}
                                    noColor="gray"
                                    podeConfirmar={podeConfirmar}
                                >
                                    <p style={{ color: 'black', fontSize: '16px', marginBottom: '20px' }}>
                                        Tem certeza que deseja eliminar este funcionário?
                                    </p>
                                    {!podeConfirmar && (
                                        <p style={{ color: 'gray' }}>
                                            Pode confirmar dentro de {contador} segundo{contador !== 1 ? 's' : ''}...
                                        </p>
                                    )}
                                </PopUp>
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
