import React from 'react';
import './styles.css';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const EditarFilme = () => {
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

export default EditarFilme
