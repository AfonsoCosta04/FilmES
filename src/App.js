import './paginas/styles.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PaginaInicialUti from './paginas/PaginaInicialUti';
import Catalogo from './paginas/Catalogo';
import Localizacao from './paginas/Localizacao';
import Login from './paginas/Login';
import SignUp from './paginas/SignUp';
import Produto from './paginas/Produto';
import Carrinho from './paginas/Carrinho';
import EsqueciPassword from './paginas/EsqueciPassword';
import PaginaInicialAdmin from './paginas/PaginaInicialAdmin';
import GerirFilmes from './paginas/GerirFilmes';
import GerirFuncionario from './paginas/GerirFuncionario';
import EditarFuncionario from './paginas/EditarFuncionario';
import SelecionarFuncionario from './paginas/SelecionarFuncionario';
import AdicionarFuncionario from './paginas/AdicionarFuncionario';
import AdicionarFilme from './paginas/AdicionarFilme';
import EditarFilme from './paginas/EditarFilme';
import MudarEstado from './paginas/MudarEstado';
import EscolhaFilme from './paginas/EscolhaFilme';
import PaginaInicialFuncionario from './paginas/PaginaInicialFuncionario';
import PerfilUtilizador from './paginas/PerfilUtilizador';
import EditarConta from './paginas/EditarConta';
import TipoUtilizador from './Componentes/TipoUtilizador';
import { Navigate } from 'react-router-dom';

function App() {
  const tipoUtilizador = TipoUtilizador;

  return (
    <Router>
      <Routes>
        {/*Rotas utilizador sem registo*/}
        <Route path='/' element={<PaginaInicialUti />} />
        <Route path='/Login' element={<Login />} />
        <Route path='/SignUp' element={<SignUp />} />
        <Route path='/EsqueciPassword' element={<EsqueciPassword />} />
        <Route path='/Catalogo' element={<Catalogo />} />
        <Route path='/Localizacao' element={<Localizacao />} />
        <Route path='/Produto' element={<Produto />} />
        {/*Rotas cliente*/}
        {tipoUtilizador === 4 && (
          <>
            <Route path='/Carrinho' element={<Carrinho />} />
            <Route path='/PerfilUtilizador' element={<PerfilUtilizador />} />
            <Route path='/EditarConta' element={<EditarConta />} />
          </>
        )}
        {/*Rotas Admin*/}
        {tipoUtilizador === 1 && (
          <>
            <Route path='/PaginaInicialAdmin' element={<PaginaInicialAdmin />} />
            <Route path='/GerirFilmes' element={<GerirFilmes />} />
            <Route path='/GerirFuncionario' element={<GerirFuncionario />} />
            <Route path='/EditarFuncionario' element={<EditarFuncionario />} />
            <Route path='/SelecionarFuncionario' element={<SelecionarFuncionario />} />
            <Route path='/AdicionarFuncionario' element={<AdicionarFuncionario />} />
          </>
        )}
        {/*Rotas Funcionário e admin*/}
        {[1, 2].includes(tipoUtilizador) && (
          <>
            <Route path='/AdicionarFilme' element={<AdicionarFilme />} />
            <Route path='/EditarFilme' element={<EditarFilme />} />
            <Route path='/EscolhaFilme' element={<EscolhaFilme />} />
            <Route path='/MudarEstado' element={<MudarEstado />} />
          </>
        )}
        {/*Rotas Funcionário*/}
        {tipoUtilizador === 1 && (
          <>
            <Route path='/PaginaInicialFuncionario' element={<PaginaInicialFuncionario />} />
          </>
        )}
        {/*Default */}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;
