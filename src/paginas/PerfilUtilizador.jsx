import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import TipoUtilizador from '../Componentes/TipoUtilizador';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import PopUp from '../Componentes/PopUp';
import { useRef } from 'react';

const PerfilUtilizador = () => {
    const tipoUtilizador = TipoUtilizador;
    const navigate = useNavigate();
    const goToEditar = () => {
        navigate('/EditarConta');
    }
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
            <main className="background-image">
                <div className='perfil-container'>
                    <Link to="/" className="botao-voltar-edicao">
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
                    <h1>Perfil de Utilizador</h1>
                    <div className="perfil-buttons">
                        <button className="perfil-btn" onClick={goToEditar}>Editar conta</button>
                        <button className="perfil-btn" onClick={() => abrirPopUp(() => console.log("Logout feito!"), <p style={{
                            color: 'black', fontSize: '16px', marginBottom: '20px'
                        }}> Tem a certeza que deseja terminar sessão?</p>)} >Terminar Sessão</button>
                        <button className="perfil-btn" onClick={() => abrirPopUp(() => abrirSegundoPopUp(() =>
                            console.log("Conta apagada")), <p style={{ color: 'black', fontSize: '16px', marginBottom: '20px' }}>
                            Tem a certeza que deseja apagar a sua conta? </p>)} > Apagar conta
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
                                Tem certeza que quer apagar a sua conta?
                            </p>
                            {!podeConfirmar && (
                                <p style={{ color: 'gray' }}>
                                    Pode confirmar dentro de {contador} segundo{contador !== 1 ? 's' : ''}...
                                </p>
                            )}
                        </PopUp>
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>

    )
}

export default PerfilUtilizador
