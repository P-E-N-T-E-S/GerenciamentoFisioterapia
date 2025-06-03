import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import AppRoutes from './routes/AppRoutes.js';
import {AuthProvider} from './services/AuthContext';

import Home from './pages/Home';
import Material from './pages/Material';
import Pacientes from './pages/Pacientes';
import Consultas from './pages/Consultas';
import Calendario from './pages/Calendario';
import Login from './pages/Login';
import Cadastro from './pages/Cadastro';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <AuthProvider>
          <AppRoutes />
        </AuthProvider>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
