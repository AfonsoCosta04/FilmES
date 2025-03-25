import React from 'react'
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();

  const goToCatalogo = () => {
    navigate('/Catalogo');
  };
  const goToHome = () => {
    navigate('/')
  }
  const goToLocalizacao =() => {
    navigate('/Localizacao')
  }
  return (
    <header>
      <div className="logo" onClick={goToHome} style={{cursor: 'pointer'}}>
        <img src="imagens/FilmE.S..png" alt="Logo FILME.S" />
      </div>
    <nav>
      <ul>
        <li onClick={goToCatalogo} style={{ cursor: 'pointer' }}>Catálogo</li>
        <li onClick={goToLocalizacao} style={{cursor : 'pointer'}}>Localizacao</li>
      </ul>
    </nav>
    <div className="auth">
      <Link to = '/Login'>Login</Link> | <Link to = '/SignUp'>Sign Up</Link>
    </div>
  </header>
  )
}

export default Header
