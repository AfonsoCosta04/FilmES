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

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<PaginaInicialUti />} />
        <Route path='/Catalogo' element={<Catalogo />} />
        <Route path='/Localizacao' element={<Localizacao />} />
        <Route path='/Login' element={<Login />} />
        <Route path='/SignUp' element={<SignUp />} />
        <Route path='/Produto' element={<Produto />} />
        <Route path='/Carrinho' element={<Carrinho />} />
        <Route path='/EsqueciPassword' element={<EsqueciPassword />} />
        <Route path='/PaginaInicialAdmin' element={<PaginaInicialAdmin />} />
        <Route path='/GerirFilmes' element={<GerirFilmes />} />
        <Route path='/GerirFuncionario' element={<GerirFuncionario />} />
        <Route path='/EditarFuncionario' element={<EditarFuncionario />} />
        <Route path='/SelecionarFuncionario' element={<SelecionarFuncionario />} />
        <Route path='/AdicionarFuncionario' element={<AdicionarFuncionario />} />
        <Route path='/AdicionarFilme' element={<AdicionarFilme />} />
        <Route path='/EditarFilme' element={<EditarFilme />} />
        <Route path='/EscolhaFilme' element={<EscolhaFilme />} />
        <Route path='/MudarEstado' element={<MudarEstado />} />
        <Route path='/PaginaInicialFuncionario' element={<PaginaInicialFuncionario />} />
      </Routes>
    </Router>
  );
}

export default App;
