import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import TipoUtilizador from '../Componentes/TipoUtilizador';
import PopUp from '../Componentes/PopUp';
import { useState } from 'react';
import { Link } from 'react-router-dom';


const EditarConta = () => {
    const tipoUtilizador = TipoUtilizador;
    const [mostrarPopUp, setMostrarPopUp] = useState(false);
    const [acaoConfirmar, setAcaoConfirmar] = useState(() => () => { });
    const [popupType, setPopupType] = useState(null);

    const [passAtual, setPassAtual] = useState('');
    const [novaPass, setNovaPass] = useState('');
    const [confirmarPass, setConfirmarPass] = useState('');

    const abrirPopUp = (tipo, acao) => {
        setPopupType(tipo);
        setAcaoConfirmar(() => acao);
        setMostrarPopUp(true);
    };
    const confirmarMudancaPassword = () => {
        if (novaPass !== confirmarPass) {
            alert("As passwords não coincidem.");
            return;
        }
        console.log("Password alterada de:", passAtual, "para:", novaPass);
    };
    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <div className="background-image">
                <div className="formulario-adicionar-filme-alt">
                    <Link to="/PerfilUtilizador" className="botao-voltar-edicao">
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
                    <h2>Editar conta</h2>
                    <form action="#" method="POST">
                        <input type="text" placeholder="Nome de utilizador" />
                        <input type="email" placeholder="Email" />
                        <input
                            type="date"
                            placeholder="Data de nascimento (dd/mm/aa)"
                        />
                        <input type="tel" placeholder="Número de telemóvel" />
                        <input type="text" placeholder="NIF (Opcional)" />
                        <button type="submit" className='save-button' >Guardar</button>
                    </form>
                    <button className="pass-button" onClick={() => abrirPopUp("password", confirmarMudancaPassword)}> Mudar Password</button>
                    <PopUp
                        visivel={mostrarPopUp}
                        fechar={() => setMostrarPopUp(false)}
                        onConfirm={acaoConfirmar}
                        yesColor="#28a745"
                        noColor="#dc3545"
                    >
                        {popupType === "password" && (
                            <div>
                                <input
                                    type="password"
                                    placeholder="Palavra-passe atual"
                                    value={passAtual}
                                    onChange={(e) => setPassAtual(e.target.value)}
                                    style={{ marginBottom: '10px', padding: '8px', width: '90%' }}
                                />
                                <input
                                    type="password"
                                    placeholder="Nova palavra-passe"
                                    value={novaPass}
                                    onChange={(e) => setNovaPass(e.target.value)}
                                    style={{ marginBottom: '10px', padding: '8px', width: '90%' }}
                                />
                                <input
                                    type="password"
                                    placeholder="Confirmar nova palavra-passe"
                                    value={confirmarPass}
                                    onChange={(e) => setConfirmarPass(e.target.value)}
                                    style={{ marginBottom: '10px', padding: '8px', width: '90%' }}
                                />
                            </div>
                        )}
                    </PopUp>
                </div>
            </div>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>
    )
}

export default EditarConta
