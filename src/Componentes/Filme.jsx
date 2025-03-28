import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import React from 'react'
import './Filme.css'

const Filme = ({ filme }) => {
    const navigate = useNavigate();

    const handleClick = () => {
        if (filme.tipoUtilizador === 3) {
            if (filme.disponivel) {
                navigate('/Login');
            } else {
                navigate('/Produto');
            }
        }
        else {
            if (filme.disponivel) {
                navigate('/Carrinho');
            } else {
                navigate('/Produto');
            }
        }
    };
    return (
        <div className="catalogo-filme">
            <div className="catalogo-link" >
                <Link to='/Produto'>
                    <img src={filme.imagem} alt={filme.nome} />~
                </Link>
                <div className="catalogo-info">
                    <h3>{filme.nome}</h3>
                    <span className="catalogo-status">
                        {filme.disponivel ? (
                            <span className="disponivel">🟢 Disponível</span>
                        ) : (
                            <span className="indisponivel">🔴 Indisponível</span>
                        )}
                    </span>
                </div>
            </div>

            <button
                className={`catalogo-ver-filme ${!filme.disponivel ? 'indisponivel' : ''}`}
                onClick={handleClick}
            >
                {filme.disponivel ? 'Aluguer Rápido' : 'Ver Filme'}
            </button>
        </div>
    )
}

export default Filme
