import React from 'react';

import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';

const Home = () => {
  return (
    <div className="app-container">
      <Sidebar />

      <main className="main-content">
        <Header />

        <div className="dashboard">
          <div className="cards-row">
            <div className="card">
              <h3>Consultas de Hoje</h3>
              <div className="placeholder">[Tabela de Consultas]</div>
            </div>
            <div className="card">
              <h3>Materiais Restantes</h3>
              <div className="placeholder">[Tabela de Materiais]</div>
            </div>
          </div>
          
        </div>
      </main>
    </div>
  );
};

export default Home;
