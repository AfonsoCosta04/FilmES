import React from 'react'
import Footer from '../Componentes/Footer'
import Header from '../Componentes/Header'
import './styles.css'
import { Link, useNavigate } from 'react-router-dom'
import TipoUtilizador from '../Componentes/TipoUtilizador'

const EsqueciPassword = () => {
    const tipoUtilizador = TipoUtilizador;
    const navigate = useNavigate();
    const goToLogin = () => {
        navigate('/Login')
    }
    return (
        <>
            <meta charSet="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>FILME.S.</title>
            <link rel="icon" type="image/x-icon" href="imagens/favicon.ico" />
            <Header tipoUtilizador={tipoUtilizador} />
            <main>
                <div className="background-image">
                    <div className="recuperar-box">
                        <h2>
                            Redefinir
                            <br />
                            Password
                        </h2>
                        <p>Insira o mesmo e-mail com o qual efetuou o sign-up.</p>
                        <br />
                        <p>Irá receber o um código que lhe permitirá redifinar a password.</p>
                        <br />
                        <form action="" method="POST">
                            <input type="email" placeholder="Email" required />
                            <button type="submit" onClick={goToLogin}>Login</button>
                        </form>
                        <div className="links-recuperar-alt">
                            <span>
                                Lembrou-se da Password?<Link to="/Login">Login</Link>
                            </span>
                            <br />
                        </div>
                    </div>
                </div>
            </main>
            <Footer tipoUtilizador={tipoUtilizador} />
        </>

    )
}

export default EsqueciPassword
