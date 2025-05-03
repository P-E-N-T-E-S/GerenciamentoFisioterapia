import React from 'react';
import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList,
  FaCalendarAlt, FaBoxOpen, FaSignOutAlt, FaTooth
} from 'react-icons/fa';
import '../styles/Home.css';

const Material = () => {
  return (
    <div className="app-container">
      <aside className="sidebar">
        <div className="logo">
          <FaTooth className="logo-icon" />
          <span>Clínica+ Saúde</span>
        </div>
        <ul className="nav-list">
          <li><Link to="/"><FaHome className="icon" /> Homepage</Link></li>
          <li><Link to="#"><FaUserInjured className="icon" /> Pacientes</Link></li>
          <li><Link to="#"><FaClipboardList className="icon" /> Consultas</Link></li>
          <li><Link to="#"><FaCalendarAlt className="icon" /> Calendário</Link></li>
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
          <h2 style={{ marginBottom: '20px' }}>Material</h2>

          <div className="material-actions">
            <button className="btn-primary">Adicionar material</button>
            <select className="dropdown">
              <option value="">Filtro</option>
            </select>
          </div>

          <div className="table-wrapper">
            <table className="table">
              <thead>
                <tr>
                  <th>Material</th>
                  <th>Quantidade</th>
                </tr>
              </thead>
              <tbody>
                {/* Nenhum dado aqui */}
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Material;
