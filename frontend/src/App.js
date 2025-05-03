import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Material from './pages/Material';
import Pacientes from './pages/Pacientes';
import Consultas from './pages/Consultas';
import Calendario from './pages/Calendario';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/material" element={<Material />} />
        <Route path="/pacientes" element={<Pacientes />} />
        <Route path="/consultas" element={<Consultas />} />
        <Route path="/calendario" element={<Calendario />} />
      </Routes>
    </Router>
  );
}

export default App;
