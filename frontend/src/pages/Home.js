import React from 'react';
import { FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt, FaBoxOpen, FaSignOutAlt, FaTooth } from 'react-icons/fa';
import '../styles/Home.css';

const Home = () => {
  return (
    <div className="app-container">
      <aside className="sidebar">
        <div className="logo">
          <FaTooth className="logo-icon" />
          <span>Clínica+ Saúde</span>
        </div>
        <ul className="nav-list">
          <li><FaHome className="icon" /> Homepage</li>
          <li><FaUserInjured className="icon" /> Pacientes</li>
          <li><FaClipboardList className="icon" /> Consultas</li>
          <li><FaCalendarAlt className="icon" /> Calendário</li>
          <li><FaBoxOpen className="icon" /> Material</li>
        </ul>
        <div className="logout"><FaSignOutAlt className="icon" /> Sair</div>
      </aside>

      <main className="main-content">
        <header className="header">
          <span>Seja bem-vinda, <strong>Thaysa</strong>!</span>
          <span className="profile">👤</span>
        </header>

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
          <div className="chart-card">
            <h3>Rendimento Mensal</h3>
            <div className="placeholder">[Gráfico de Rendimento]</div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Home;
