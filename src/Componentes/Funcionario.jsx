import React from 'react'
import './Funcionario.css'
import { Link } from 'react-router-dom'

const Funcionario = ({ funcionario }) => {
    return (
        <div className="funcionarios-caixa">
            <div className="info-funcionario">
                <span>
                    <strong>Nome:</strong> {funcionario.nome}
                </span>
                <span>
                    <strong>Cargo:</strong> {funcionario.cargo}
                </span>
                <span>
                    <strong>ID:</strong> {funcionario.id}
                </span>
            </div>

            <Link to='/EditarFuncionario'>
                <button className="botao-editar-func">Editar</button>
            </Link>
        </div>
    )
}

export default Funcionario
