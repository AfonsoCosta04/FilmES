import React from 'react'
import './Footer.css'
import { Link } from 'react-router-dom'

const Footer = ({ tipoUtilizador }) => {
  return (
    <footer>
      <div className="social-links">
        {tipoUtilizador === 3 && (
          <Link to='/Localizacao'>Localização</Link>
        )}
        {tipoUtilizador === 4 && (
          <Link to='/Localizacao'>Localização</Link>
        )}
        <Link to='' >Instagram</Link>
        <Link to=''>Twitter</Link>
        <Link to=''>Facebook</Link>
      </div>
    </footer>
  )
}

export default Footer
