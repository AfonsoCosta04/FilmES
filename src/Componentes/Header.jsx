import React from 'react'
import './Header.css'
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const Header = ({ tipoUtilizador }) => {
  const navigate = useNavigate();

  const goToPagIniUti = () => {
    navigate('/')
  }
  const goToPagIniAdmin = () => {
    navigate('/PaginaInicialAdmin')
  }
  const goToPagIniFunc = () => {
    navigate('/PaginaInicialFuncionario')
  }
  return (
    <header>
      {tipoUtilizador === 1 && (
        <div className="logo" onClick={goToPagIniAdmin} style={{ cursor: 'pointer' }}>
          <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
        </div>
      )}
      {tipoUtilizador === 2 && (
        <div className="logo" onClick={goToPagIniFunc} style={{ cursor: 'pointer' }}>
          <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
        </div>
      )}
      {tipoUtilizador === 3 && (
        <div className="logo" onClick={goToPagIniUti} style={{ cursor: 'pointer' }}>
          <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
        </div>
      )}
      {tipoUtilizador === 4  && (
        <div className="logo" onClick={goToPagIniUti} style={{ cursor: 'pointer' }}>
          <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
        </div>
      )}
      <nav>
        {tipoUtilizador === 1 && (
          <div className="role-admin">
            <p>
              <strong>ADMIN</strong>
            </p>
          </div>
        )}
        {tipoUtilizador === 2 && (
          <div className="role-admin">
            <p>
              <strong>FUNCIONÁRIO</strong>
            </p>
          </div>
        )}
        {tipoUtilizador === 3 && (
          <div className="auth">
            <Link to='/Login'>Login</Link> | <Link to='/SignUp'>Sign Up</Link>
          </div>
        )}
        {tipoUtilizador === 4 && (
          <div className="auth">
            <Link to='/Carrinho'>Carrinho</Link> | <Link to='/PerfilUtilizador'>Utilizador</Link>
          </div>
        )}
      </nav>
    </header>
  )
}

export default Header
