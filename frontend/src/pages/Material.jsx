import React from 'react';
import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList,
  FaCalendarAlt, FaBoxOpen, FaSignOutAlt, FaTooth
} from 'react-icons/fa';
import '../styles/Home.css';
import { Sidebar } from '../components/Sidebar';
import { Header } from '../components/Header';

const Material = () => {
  return (
    <div className="app-container">
      <Sidebar />

      <main className="main-content">
        <Header />

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
