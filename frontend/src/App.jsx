import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import Home from './pages/Home';
import Material from './pages/Material';
import Pacientes from './pages/Pacientes';
import Consultas from './pages/Consultas';
import Calendario from './pages/Calendario';
import Login from './pages/Login';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/home" element={<Home />} />
          <Route path="/material" element={<Material />} />
          <Route path="/pacientes" element={<Pacientes />} />
          <Route path="/consultas" element={<Consultas />} />
          <Route path="/calendario" element={<Calendario />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
