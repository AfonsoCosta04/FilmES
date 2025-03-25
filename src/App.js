import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './paginas/Home';
import Catalogo from './paginas/Catalogo';
import Localizacao from './paginas/Localizacao';
import Login from './paginas/Login';
import SignUp from './paginas/SignUp';
function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/Catalogo' element={<Catalogo />} />
        <Route path='/Localizacao' element={<Localizacao/>} />
        <Route path='/Login' element={<Login/>} />
        <Route path='/SignUp' element={<SignUp/>} />
      </Routes>
    </Router>
  );
}

export default App;
