import React, { useState } from 'react';  
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar.jsx';
import { Header } from '../components/template/Header.jsx';
import { useConsultaByDate } from '../hooks/useConsultas';

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

const formatDate = (dia) => {
  const [dd, mm] = dia.split('/');
  const year = new Date().getFullYear();
  return `${dd}/${mm}/${year}`; // dd/MM/yyyy para o backend
};

const Calendario = () => {
  const [dataSelecionada, setDataSelecionada] = useState('');
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const { data: consultas, isLoading } = useConsultaByDate(dataSelecionada);

  const nomeDoMes = new Date().toLocaleDateString('pt-BR', {
    month: 'long',
    year: 'numeric'
  });

  const dias = getMonthDays();
  const weekDays = ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'];
  const today = new Date().getDate();
  const isToday = (dia) =>
    dia === today.toString().padStart(2, '0') + '/' + (new Date().getMonth() + 1).toString().padStart(2, '0');

  return (
    <div className="app-container">
      <button
        className="menu-toggle"
        onClick={() => setSidebarOpen(!sidebarOpen)}
        aria-label="Abrir menu"
      >
        &#9776;
      </button>
      <Sidebar open={sidebarOpen} setOpen={setSidebarOpen} />

      <main className="main-content">
        <Header />
        <div className="dashboard center-material">
          <div className="calendar-header">
            <h2>Calendário - {nomeDoMes.charAt(0).toUpperCase() + nomeDoMes.slice(1)}</h2>
          </div>

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
                  if (dia && dia.includes('/')) {
                    setDataSelecionada(formatDate(dia));
                  }
                }}
              >
                {dia}
              </div>
            ))}
          </div>

          {dataSelecionada && (
            <div className="consulta-result">
              <h3>Consultas para {dataSelecionada}:</h3>
              {isLoading ? (
                <p>Carregando...</p>
              ) : consultas?.length ? (
                <ul>
                  {consultas.map((consulta) => (
                    <li key={consulta.id}>
                      {(consulta.paciente?.nome || '-')} - {consulta.dataHora ? new Date(consulta.dataHora).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) : '-'}
                    </li>
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