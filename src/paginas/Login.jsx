import React from 'react';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import './styles.css';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const Login = () => {
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
          <div className="login-box">
            <h2>Login</h2>
            <form action="" method="POST">
              <input
                type="text"
                placeholder="Nome de utilizador / Email"
                required
              />
              <input type="password" placeholder="Password" required />
              <button type="submit">Login</button>
            </form>
            <div className="links-login-alt">
              <span>
                Não tem conta? <Link to="/SignUp">Sign Up</Link>
              </span>
              <br />
              <br />
              <span>
                <Link to="/EsqueciPassword">Esqueci-me da password</Link>
              </span>
            </div>
          </div>
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>

  )
}

export default Login
