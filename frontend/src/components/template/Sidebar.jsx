import React from 'react';
import './Sidebar.css';

import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt,
  FaBoxOpen, FaSignOutAlt, FaTooth
} from 'react-icons/fa';

export const Sidebar = () => {
  return (
      <div className="sidebar">
          <Link to="/" className="logo" style={{ textDecoration: 'none', color: 'inherit' }}>
              <FaTooth className="logo-icon" />
              <span>Clínica+ Saúde</span>
          </Link>
          <ul className="nav-list">
              <li><Link to="/"><FaHome className="icon" /> Homepage</Link></li>
              <li><Link to="/pacientes"><FaUserInjured className="icon" /> Pacientes</Link></li>
              <li><Link to="/consultas"><FaClipboardList className="icon" /> Consultas</Link></li>
              <li><Link to="/calendario"><FaCalendarAlt className="icon" /> Calendário</Link></li>
              <li><Link to="/material"><FaBoxOpen className="icon" /> Material</Link></li>
          </ul>
          <div className="logout"><FaSignOutAlt className="icon" /> Sair</div>
      </div>
  )
}