import React from 'react';
import './styles.css';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';
import PopUp from '../Componentes/PopUp';
import { useState } from 'react';
import { useRef } from 'react';

const EditarFilme = () => {
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
                    <div className="formulario-editar-filme">
                        <h2>Editar Filme</h2>
                        <Link to="/EscolhaFilme" className="botao-voltar-edicao">
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
                                placeholder="Dune: Part 2"
                                required
                            />
                            <label htmlFor="ano">Ano:</label>
                            <input
                                type="number"
                                id="ano"
                                name="ano"
                                placeholder={2024}
                                min={1888}
                                required
                            />
                            <label>Duração:</label>
                            <div style={{ display: "flex", gap: 10 }}>
                                <input
                                    type="number"
                                    id="horas"
                                    name="horas"
                                    placeholder={2}
                                    min={0}
                                    style={{ flex: 1 }}
                                    required
                                />
                                <input
                                    type="number"
                                    id="minutos"
                                    name="minutos"
                                    placeholder={46}
                                    min={0}
                                    max={59}
                                    style={{ flex: 1 }}
                                    required
                                />
                            </div>
                            <label htmlFor="imdb">Nota IMDb:</label>
                            <input
                                type="number"
                                step="0.1"
                                id="imdb"
                                name="imdb"
                                placeholder="8.5"
                                required
                            />
                            <label htmlFor="rotten">Nota Rotten Tomatoes (%):</label>
                            <input
                                type="number"
                                id="rotten"
                                name="rotten"
                                placeholder={95}
                                min={0}
                                max={100}
                                required
                            />
                            <label htmlFor="genero">Gêneros (até 3):</label>
                            <input
                                type="text"
                                id="genero"
                                name="genero"
                                placeholder="Ação, Sci-Fi, Aventura"
                                required
                            />
                            <label htmlFor="ator1">Ator 1:</label>
                            <input
                                type="text"
                                id="ator1"
                                name="ator1"
                                placeholder="Nome do Ator 1"
                                required
                            />
                            <label htmlFor="ator2">Ator 2:</label>
                            <input
                                type="text"
                                id="ator2"
                                name="ator2"
                                placeholder="Nome do Ator 2"
                            />
                            <label htmlFor="ator3">Ator 3:</label>
                            <input
                                type="text"
                                id="ator3"
                                name="ator3"
                                placeholder="Nome do Ator 3"
                            />
                            <label htmlFor="sinopse">Sinopse:</label>
                            <textarea
                                id="sinopse"
                                name="sinopse"
                                rows={5}
                                placeholder="Aqui vai o texto da sinopse do filme. O objetivo é que ele tenha uma largura fixa e que a leitura seja mais confortável para o usuário."
                                required
                                defaultValue={""}
                            />
                            <label htmlFor="imagem">Imagem do Filme:</label>
                            <input type="file" id="imagem" name="imagem" accept="image/*" />
                            <div className="botoes-edicao">
                                <button type="button" className="botao-eliminar-func" onClick={() => abrirPopUp(() => abrirSegundoPopUp(() =>
                                    console.log("Filme apagado")), <p style={{ color: 'black', fontSize: '16px', marginBottom: '20px' }}>
                                    Tem a certeza que deseja eliminar este filme? </p>)}>
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
                                        Tem certeza que deseja eliminar este filme?
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

export default EditarFilme
