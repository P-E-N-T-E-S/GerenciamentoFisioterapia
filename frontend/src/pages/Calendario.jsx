import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
  FaHome, FaUserInjured, FaClipboardList, FaCalendarAlt,
  FaBoxOpen, FaSignOutAlt, FaTooth
} from 'react-icons/fa';

import '../styles/Home.css';
import {Sidebar} from '../components/Sidebar.jsx';

const getWeekDays = () => {
  const today = new Date();
  const start = new Date(today);
  start.setDate(today.getDate() - today.getDay() + 1); // Segunda

  return Array.from({ length: 7 }, (_, i) => {
    const day = new Date(start);
    day.setDate(start.getDate() + i);
    return day.toLocaleDateString('pt-BR', { weekday: 'long', day: '2-digit', month: '2-digit' });
  });
};

const getMonthDays = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth();

  const firstDay = new Date(year, month, 1);
  const startWeekday = firstDay.getDay() === 0 ? 6 : firstDay.getDay() - 1; // ajusta para começar na segunda
  const lastDay = new Date(year, month + 1, 0).getDate();

  const days = [];

  // espaços vazios antes do primeiro dia do mês
  for (let i = 0; i < startWeekday; i++) {
    days.push('');
  }

  for (let d = 1; d <= lastDay; d++) {
    days.push(`${d.toString().padStart(2, '0')}/${(month + 1).toString().padStart(2, '0')}`);
  }

  return days;
};

const getYearMonths = () => {
  const year = new Date().getFullYear();
  return Array.from({ length: 12 }, (_, i) => {
    const month = new Date(year, i, 1);
    return month.toLocaleDateString('pt-BR', { month: 'long' }) + ` ${year}`;
  });
};

const Calendario = () => {
  const [modo, setModo] = useState('Mensal');

  const renderGrid = () => {
    if (modo === 'Semanal') {
      const dias = getWeekDays();
      return (
        <div className="calendar-grid">
          {dias.map((dia, i) => (
            <div className="calendar-cell" key={i}>
              <div className="calendar-cell-header">{dia}</div>
            </div>
          ))}
        </div>
      );
    }

    if (modo === 'Mensal') {
      const dias = getMonthDays();
      const weekDays = ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'];

      return (
        <>
          <div className="calendar-weekdays">
            {weekDays.map((dia, i) => (
              <div key={i}>{dia}</div>
            ))}
          </div>
          <div className="calendar-grid-mes">
            {dias.map((dia, i) => (
              <div className="calendar-cell-mes" key={i}>
                {dia}
              </div>
            ))}
          </div>
        </>
      );
    }

    if (modo === 'Anual') {
      const meses = getYearMonths();
      return (
        <div className="calendar-grid-ano">
          {meses.map((mes, i) => (
            <div className="calendar-cell-ano" key={i}>{mes}</div>
          ))}
        </div>
      );
    }
  };

  return (
    <div className="app-container">
      <Sidebar/>

      <main className="main-content">
        <header className="header">
          <span>Seja bem-vinda, <strong>Thaysa</strong>!</span>
          <span className="profile">👤</span>
        </header>

        <div className="dashboard center-material">
          <div className="calendar-header">
            <h2>Calendário</h2>
            <select className="dropdown" value={modo} onChange={(e) => setModo(e.target.value)}>
              <option>Semanal</option>
              <option>Mensal</option>
              <option>Anual</option>
            </select>
          </div>
          {renderGrid()}
        </div>
      </main>
    </div>
  );
};

export default Calendario;
