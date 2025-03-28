import React from 'react';
import Footer from '../Componentes/Footer';
import Header from '../Componentes/Header';
import './styles.css';
import { Link } from 'react-router-dom';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const SignUp = () => {
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
          <div className="signup-box">
            <h2>Sign Up</h2>
            <form action="" method="POST">
              <input type="text" placeholder="Nome de utilizador" required />
              <input type="email" placeholder="Email" required />
              <input
                type="date"
                placeholder="Data de nascimento (dd/mm/aa)"
                required
              />
              <input type="tel" placeholder="Número de telemóvel" required />
              <input type="password" placeholder="Password" required />
              <input type="password" placeholder="Repetir password" required />
              <input type="text" placeholder="NIF (Opcional)" />
              <button type="submit">Sign Up</button>
            </form>
            <div className="links-signup-alt">
              <span>
                Já tem conta? <Link to="/Login">Login</Link>
              </span>
            </div>
          </div>
        </div>
        <Footer tipoUtilizador={tipoUtilizador} />
      </main>
    </>

  )
}

export default SignUp
