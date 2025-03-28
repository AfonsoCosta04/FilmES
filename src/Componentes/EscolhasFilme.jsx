import React from 'react';
import '../paginas/styles.css';
import { Link } from 'react-router-dom';

const EscolhasFilme = ({ filme }) => {
    return (
        <Link to ='/EditarFilme'>
            <div className="box-montra-filme-disponibilidade" style={filme.style}>
                <img src={filme.imagem} alt={filme.titulo} />
                <div className="catalogo-info">
                    <h3>{filme.titulo}</h3>
                </div>
            </div>
        </Link>
    )
}

export default EscolhasFilme
