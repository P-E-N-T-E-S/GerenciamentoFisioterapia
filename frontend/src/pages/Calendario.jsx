import React, { useState } from 'react';  
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar.jsx';
import { Header } from '../components/template/Header.jsx';
import { useConsultaByDate, useConsultas } from '../hooks/useConsultas';

import { Button, Box, CircularProgress } from '@mui/material';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

const getMonthDays = (month, year) => {
  // month: 0-based (0=Jan), year: full year
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

const formatDate = (dia, year) => {
  const [dd, mm] = dia.split('/');
  return `${dd}/${mm}/${year}`; // dd/MM/yyyy para o backend
};


const Calendario = () => {
  const todayDate = new Date();
  const [currentMonth, setCurrentMonth] = useState(todayDate.getMonth()); // 0-based
  const [currentYear, setCurrentYear] = useState(todayDate.getFullYear());
  const [dataSelecionada, setDataSelecionada] = useState('');
  const [sidebarOpen, setSidebarOpen] = useState(false);
  // Para bolinhas: todas as consultas
  const { data: todasConsultas } = useConsultas();
  // Para lista do dia selecionado
  const { data: consultas, isLoading } = useConsultaByDate(dataSelecionada);

  const nomeDoMes = new Date(currentYear, currentMonth).toLocaleDateString('pt-BR', {
    month: 'long',
    year: 'numeric'
  });

  const dias = getMonthDays(currentMonth, currentYear);
  const weekDays = ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'];
  const today = todayDate.getDate();
  const isToday = (dia) => {
    return (
      currentMonth === todayDate.getMonth() &&
      currentYear === todayDate.getFullYear() &&
      dia === today.toString().padStart(2, '0') + '/' + (currentMonth + 1).toString().padStart(2, '0')
    );
  };

  const handlePrevMonth = () => {
    if (currentMonth === 0) {
      setCurrentMonth(11);
      setCurrentYear((y) => y - 1);
    } else {
      setCurrentMonth((m) => m - 1);
    }
    setDataSelecionada(''); // Limpa seleção ao trocar mês
  };

  const handleNextMonth = () => {
    if (currentMonth === 11) {
      setCurrentMonth(0);
      setCurrentYear((y) => y + 1);
    } else {
      setCurrentMonth((m) => m + 1);
    }
    setDataSelecionada(''); // Limpa seleção ao trocar mês
  };

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
        <div className="calendar-header" style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
          <Button
            onClick={handlePrevMonth}
            aria-label="Mês anterior"
            style={{
              fontSize: 20,
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              transition: 'background 0.2s',
            }}
            sx={{
              '&:hover': {
                background: '#696969',
                borderRadius: '50%',
              },
              minWidth: 0,
              padding: '8px',
            }}
          >
            <ArrowBackIosIcon />
          </Button>
          <h2 style={{ margin: 0 }}>
            Calendário - {nomeDoMes.charAt(0).toUpperCase() + nomeDoMes.slice(1)}
          </h2>
          <Button
            onClick={handleNextMonth}
            aria-label="Próximo mês"
            style={{
              fontSize: 20,
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              transition: 'background 0.2s',
            }}
            sx={{
              '&:hover': {
                background: '#696969',
                borderRadius: '50%',
              },
              minWidth: 0,
              padding: '8px',
            }}
          >
            <ArrowForwardIosIcon />
          </Button>
        </div>

          <div className="calendar-weekdays">
            {weekDays.map((dia, i) => (
              <div key={i}>{dia}</div>
            ))}
          </div>
          <div className="calendar-grid-mes">
            {dias.map((dia, i) => {
              // Verifica se há consulta para o dia
              let hasConsulta = false;
              if (dia && dia.includes('/')) {
                const diaFormatado = formatDate(dia, currentYear); // dd/MM/yyyy
                hasConsulta = Array.isArray(todasConsultas) && todasConsultas.some(c => {
                  if (!c.dataHora) return false;
                  const dataConsulta = new Date(c.dataHora);
                  // Só considera hoje ou futuras
                  const hoje = new Date();
                  hoje.setHours(0,0,0,0);
                  dataConsulta.setHours(0,0,0,0);
                  if (dataConsulta < hoje) return false;
                  const dd = dataConsulta.getDate().toString().padStart(2, '0');
                  const mm = (dataConsulta.getMonth() + 1).toString().padStart(2, '0');
                  const yyyy = dataConsulta.getFullYear();
                  return `${dd}/${mm}/${yyyy}` === diaFormatado;
                });
              }
              return (
                <div
                  className={`calendar-cell-mes ${isToday(dia) ? 'today' : ''} ${dataSelecionada === formatDate(dia, currentYear) ? 'selected' : ''}`}
                  key={i}
                  style={{ position: 'relative' }}
                  onClick={() => {
                    if (dia && dia.includes('/')) {
                      setDataSelecionada(formatDate(dia, currentYear));
                    }
                  }}
                >
                  {dia}
                  {hasConsulta && (
                    <span
                      style={{
                        position: 'absolute',
                        top: '-7px',
                        right: '-7px',
                        width: 16,
                        height: 16,
                        background: 'red',
                        borderRadius: '50%',
                        display: 'inline-block',
                        boxShadow: '0 0 4px #fff',
                        border: '2.5px solid #fff',
                        zIndex: 3,
                      }}
                    />
                  )}
                </div>
              );
            })}
          </div>

          {dataSelecionada && (
            <div className="consulta-result">
              <h3>Consultas para {dataSelecionada}:</h3>
              {isLoading ? (
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                <CircularProgress />
              </Box>
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