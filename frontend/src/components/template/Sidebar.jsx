import React from 'react';
import './Sidebar.css';

import { Link, NavLink } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt,
  FaBoxOpen, FaSignOutAlt
} from 'react-icons/fa';

export const Sidebar = () => {
  return (
      <div className="sidebar">
          <Link to="/" className="logo" style={{ textDecoration: 'none', color: 'inherit' }}>
              
              <span>Clínica+ Saúde</span>
          </Link>
          <ul className="nav-list">
              <li><NavLink to="/home"><FaHome className="icon" /> Homepage</NavLink></li>
              <li><NavLink to="/pacientes"><FaUserInjured className="icon" /> Pacientes</NavLink></li>
              <li><NavLink to="/consultas"><FaClipboardList className="icon" /> Consultas</NavLink></li>
              <li><NavLink to="/calendario"><FaCalendarAlt className="icon" /> Calendário</NavLink></li>
              <li><NavLink to="/material"><FaBoxOpen className="icon" /> Material</NavLink></li>
          </ul>
          <div className="logout"><FaSignOutAlt className="icon" /> Sair</div>
      </div>
  )
}