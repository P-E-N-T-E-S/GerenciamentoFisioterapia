import React from 'react';
import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt,
  FaBoxOpen, FaSignOutAlt, FaTooth, FaSearch
} from 'react-icons/fa';
import '../styles/Home.css';
import { Sidebar } from '../components/Sidebar';
import { Header } from '../components/Header';

const Consultas = () => {
  return (
    <div className="app-container">
    
    <Sidebar />

      <main className="main-content">
        <Header />

        <div className="dashboard center-material">
          <h2>Consultas</h2>

          <div className="tabs">
            <button className="tab active">Hoje</button>
            <button className="tab">Futuro</button>
            <button className="tab">Histórico</button>
          </div>

          <div className="consulta-actions">
            <div className="search-bar">
              <input type="text" placeholder="Buscar por nome" />
              <FaSearch className="search-icon" />
            </div>
            <button className="btn-primary">Adicionar</button>
            <select className="dropdown">
              <option>Filtro</option>
            </select>
          </div>

          <div className="consulta-list">
            {[1, 2, 3].map((_, i) => (
              <div className="consulta-card" key={i}>
                <div className="consulta-date">
                  <span className="day">Seg</span>
                  <strong className="number">15</strong>
                </div>
                <div className="consulta-info">
                  <p><strong>🕒</strong> 09:00</p>
                  <p><strong>👤</strong> Maria da Silva</p>
                  <p><strong>Tipo:</strong> intraoperatório</p>
                  <p><strong>Pagamento:</strong> {i === 0 ? 'realizado' : 'pendente'}</p>
                </div>
                <input type="checkbox" defaultChecked={i !== 2} />
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
};

export default Consultas;
