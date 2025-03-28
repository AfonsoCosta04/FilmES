import React from 'react';
import './EstadoFilme.css';
import { useState } from 'react';

const EstadoFilme = ({ filme }) => {
    const [disponivel, setDisponivel] = useState(filme.disponivel);

    const toggleDisponibilidade = () => {
        setDisponivel(!disponivel);
        console.log(`${filme.titulo} está agora ${!disponivel ? 'disponível' : 'indisponível'}`);
    };
    return (
        <div className="box-montra-filme-disponibilidade" style={filme.style}>
            <img src={filme.imagem} alt={filme.titulo} />
            <div className="catalogo-info">
                <h3>{filme.titulo}</h3>
            </div>
            <label className="switch">
                <input
                    type="checkbox"
                    checked={disponivel}
                    onChange={toggleDisponibilidade}
                />
                <span className="slider round" />
            </label>
        </div>
    )
}

export default EstadoFilme
