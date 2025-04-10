import React, { useState } from 'react';
import './Destaques.css';

const Destaques = ({ filmes }) => {
    const [filmesAtuais, setFilmesAtuais] = useState(filmes);

    const rotacionarEsquerda = () => {
        const novoArray = [...filmesAtuais];
        const ultimo = novoArray.pop();
        novoArray.unshift(ultimo);
        setFilmesAtuais(novoArray);
    };

    const rotacionarDireita = () => {
        const novoArray = [...filmesAtuais];
        const primeiro = novoArray.shift();
        novoArray.push(primeiro);
        setFilmesAtuais(novoArray);
    };

    return (
        <div className="destaques-container">
            <div className="slider-wrapper">
                <div className="slider-limite">
                    <h2 className="destaques-titulo">Destaques</h2>
                    <button className="seta seta-esquerda" onClick={rotacionarEsquerda}>&lt;</button>
                    <div className="slider-filmes">
                        <div className="slider-filmes-inner">
                            {filmesAtuais.slice(0, 5).map((filme, index) => (
                                <div className="filme-card" key={index}>
                                    <img src={filme.imagem} alt={filme.titulo} />
                                </div>
                            ))}
                        </div>
                    </div>
                    <button className="seta seta-direita" onClick={rotacionarDireita}>&gt;</button>
                </div>
            </div>
        </div>
    );
};

export default Destaques;