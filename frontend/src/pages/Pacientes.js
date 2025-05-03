import React from 'react';
import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList,
  FaCalendarAlt, FaBoxOpen, FaSignOutAlt, FaTooth, FaSearch
} from 'react-icons/fa';
import '../styles/Home.css';

const Pacientes = () => {
  return (
    <div className="app-container">
      <aside className="sidebar">
        <div className="logo">
          <FaTooth className="logo-icon" />
          <span>Clínica+ Saúde</span>
        </div>
        <ul className="nav-list">
          <li><Link to="/"><FaHome className="icon" /> Homepage</Link></li>
          <li><Link to="/pacientes"><FaUserInjured className="icon" /> Pacientes</Link></li>
          <li><Link to="/consultas"><FaClipboardList className="icon" /> Consultas</Link></li>
           <li><Link to="/calendario"><FaCalendarAlt className="icon" /> Calendário</Link></li>
          <li><Link to="/material"><FaBoxOpen className="icon" /> Material</Link></li>
        </ul>
        <div className="logout"><FaSignOutAlt className="icon" /> Sair</div>
      </aside>

      <main className="main-content">
        <header className="header">
          <span>Seja bem-vinda, <strong>Thaysa</strong>!</span>
          <span className="profile">👤</span>
        </header>

        <div className="dashboard center-material">
          <h2>Pacientes</h2>

          <div className="paciente-actions">
            <div className="search-bar">
              <input type="text" placeholder="Buscar por nome" />
              <FaSearch className="search-icon" />
            </div>
            <div className="btn-group">
              <button className="btn-primary">Adicionar</button>
              <button className="btn-secondary">Deletar</button>
            </div>
          </div>

          <div className="paciente-list">
            {[1, 2, 3, 4].map((_, idx) => (
              <div key={idx} className="paciente-card">
                <div className="paciente-info">
                  <span className="avatar">👤</span>
                  <span>Nome do paciente</span>
                </div>
                <button className="btn-secondary">Ficha médica</button>
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
};

export default Pacientes;
