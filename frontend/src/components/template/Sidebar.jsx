import React from 'react';
import './Sidebar.css';

import { Link, NavLink } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt,
  FaBoxOpen, FaSignOutAlt
} from 'react-icons/fa';

export const Sidebar = ({ open, setOpen }) => {
  const cargo = localStorage.getItem("cargo");

  return (
    <div className={`sidebar${open ? ' open' : ''}`}>
      <Link to="/" className="logo" style={{ textDecoration: 'none', color: 'inherit' }}>
        <span>Clínica+ Saúde</span>
      </Link>
      <ul className="nav-list">
        {cargo === "ROLE_ADMINISTRADOR" && (
          <>
            <li><NavLink to="/home"><FaHome className="icon" /> <span>Homepage</span></NavLink></li>
            <li><NavLink to="/pacientes"><FaUserInjured className="icon" /> <span>Pacientes</span></NavLink></li>
            <li><NavLink to="/consultas"><FaClipboardList className="icon" /> <span>Consultas</span></NavLink></li>
            <li><NavLink to="/calendario"><FaCalendarAlt className="icon" /> <span>Calendário</span></NavLink></li>
            <li><NavLink to="/material"><FaBoxOpen className="icon" /> <span>Material</span></NavLink></li>
          </>
        )}
        {cargo === "ROLE_ASSISTENTE" && (
          <li><NavLink to="/pacientes"><FaUserInjured className="icon" /> <span>Pacientes</span></NavLink></li>
        )}
      </ul>
      {(cargo === "ROLE_ADMINISTRADOR" || cargo === "ROLE_ASSISTENTE") && (
        <div
          className="logout"
          onClick={() => {
            localStorage.removeItem("token");
            localStorage.removeItem("cargo");
            window.location.href = "/";
          }}
          style={{ cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '8px' }}
        >
          <FaSignOutAlt className="icon" />
          <span>Sair</span>
        </div>
      )}
    </div>
  )
}