import React, { useState } from 'react';
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar.jsx';
import { Header } from '../components/template/Header.jsx';
import { useConsultaByDate } from '../hooks/useConsultas'; // ajuste o caminho conforme necessário

const getWeekDays = () => {
  const today = new Date();
  const start = new Date(today);
  const dayOfWeek = today.getDay();
  const offset = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
  start.setDate(today.getDate() + offset);

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
  const startWeekday = firstDay.getDay() === 0 ? 6 : firstDay.getDay() - 1;
  const lastDay = new Date(year, month + 1, 0).getDate();

  const days = [];

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

const formatDate = (dia) => {
  const [dd, mm] = dia.split('/');
  const year = new Date().getFullYear();
  return `${year}-${mm}-${dd}`;
};

const Calendario = () => {
  const [modo, setModo] = useState('Mensal');
  const [dataSelecionada, setDataSelecionada] = useState('');

  const { data: consultas, isLoading } = useConsultaByDate(dataSelecionada);

  const nomeDoMes = new Date().toLocaleDateString('pt-BR', {
    month: 'long',
    year: 'numeric'
  });

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

      const today = new Date().getDate();
      const isToday = (dia) =>
        dia === today.toString().padStart(2, '0') + '/' + (new Date().getMonth() + 1).toString().padStart(2, '0');

      return (
        <>
          <div className="calendar-weekdays">
            {weekDays.map((dia, i) => (
              <div key={i}>{dia}</div>
            ))}
          </div>
          <div className="calendar-grid-mes">
            {dias.map((dia, i) => (
              <div
                className={`calendar-cell-mes ${isToday(dia) ? 'today' : ''}`}
                key={i}
                onClick={() => {
                  if (dia) setDataSelecionada(formatDate(dia));
                }}
              >
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
      <Sidebar />
      <main className="main-content">
        <Header />
        <div className="dashboard center-material">
          <div className="calendar-header">
            <h2>Calendário - {nomeDoMes.charAt(0).toUpperCase() + nomeDoMes.slice(1)}</h2>
            <select className="dropdown" value={modo} onChange={(e) => setModo(e.target.value)}>
              <option>Semanal</option>
              <option>Mensal</option>
              <option>Anual</option>
            </select>
          </div>

          {renderGrid()}

          {dataSelecionada && (
            <div className="consulta-result">
              <h3>Consultas para {dataSelecionada}:</h3>
              {isLoading ? (
                <p>Carregando...</p>
              ) : consultas?.length ? (
                <ul>
                  {consultas.map((consulta) => (
                    <li key={consulta.id}>{consulta.nomePaciente} - {consulta.horario}</li>
                  ))}
                </ul>
              ) : (
                <p>Nenhuma consulta encontrada.</p>
              )}
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default Calendario;
