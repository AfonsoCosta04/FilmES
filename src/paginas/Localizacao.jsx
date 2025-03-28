import React from 'react';
import './styles.css';
import Header from '../Componentes/Header';
import Footer from '../Componentes/Footer';
import TipoUtilizador from '../Componentes/TipoUtilizador';

const Localizacao = () => {
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
          <div className="caixa-texto-loc">
            <div className="texto-loc">
              <h2>Encontre-nos</h2>
              <p>Estamos localizados no seguinte endereço:</p>
              <br />
              <p>
                <strong>
                  Av. do Empresário, Campus da Talagueira, Zona do Lazer, 6000-767
                  Castelo Branco, Portugal
                </strong>
              </p>
              <br />
              <p>
                Telefone: <strong>912 345 678</strong>
              </p>
              <br />
              <p>
                Email: <strong>filme.s.@gmail.com</strong>
              </p>
              <br />
              <p>
                Horário de atendamento: <br />
                <br />{" "}
                <strong>
                  2ºFeira-6ºFeira: 8:00 - 19:00 <br />
                  <br />
                  Sábado: 9:00 - 13:00
                </strong>
              </p>
            </div>
          </div>
          <div className="mapa-container">
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1288.4453689532509!2d-7.51201423860533!3d39.81917484267759!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd3d5ea6bb2280e1%3A0x1c460157bc4b46c8!2sEscola%20Superior%20de%20Tecnologia%20-%20Instituto%20Polit%C3%A9cnico%20de%20Castelo%20Branco!5e0!3m2!1spt-PT!2spt!4v1742932596968!5m2!1spt-PT!2spt"
              style={{ border: 0, width: "100%", height: "100%", borderRadius: 15 }}
              allowFullScreen=""
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            />
          </div>
        </div>
      </main>
      <Footer tipoUtilizador={tipoUtilizador} />
    </>
  )
}

export default Localizacao
