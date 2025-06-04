import React, { useState } from 'react';
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { useMateriais } from '../hooks/useMateriais';
import { useConsultaByDate } from '../hooks/useConsultas';
import { useConsultas } from '../hooks/useConsultas';

import { Box, CircularProgress } from '@mui/material';

// Importações do Chart.js
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Home = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const { data: materiais, isLoading, isError } = useMateriais();

  // Consultas de hoje
  const today = new Date();
  const dd = String(today.getDate()).padStart(2, '0');
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const yyyy = today.getFullYear();
  const dataHoje = `${dd}/${mm}/${yyyy}`;
  const { data: consultasHoje, isLoading: isLoadingConsultasHoje } = useConsultaByDate(dataHoje);

  // Consultas para cálculo de arrecadação
  const { data: consultas, isLoading: isLoadingConsultas } = useConsultas();

  // Cálculo do total arrecadado
  const totalArrecadado =
    consultas && Array.isArray(consultas)
      ? consultas.reduce((acc, c) => acc + (typeof c.valor === 'number' ? c.valor : 0), 0)
      : 0;

  // Dados para o gráfico
  const chartData = {
    labels: materiais ? materiais.map((mat) => mat.nome) : [],
    datasets: [
      {
        label: 'Quantidade',
        data: materiais ? materiais.map((mat) => mat.quantidade) : [],
        backgroundColor: 'rgba(54, 162, 235, 0.6)',
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: { display: false },
      title: { display: false },
    },
    scales: {
      y: { beginAtZero: true },
    },
  };

  // Filtra pagamentos pendentes
  const pagamentosPendentes = consultas && Array.isArray(consultas)
    ? consultas.filter(c => c.clientePagou === false || c.clientePagou === undefined)
    : [];

  return (
    <div className="app-container">


    {/* Botão para abrir/fechar o sidebar */}
      <button 
        className="menu-toggle"
        onClick={() => setSidebarOpen(!sidebarOpen)}
        aria-label='Abrir menu'
      >
        &#9776;
      </button>

      <Sidebar open={sidebarOpen} setOpen={setSidebarOpen} />

      <main className="main-content">
        <Header />

        <div className="dashboard">
          <div className="cards-row">
            {/* Card de arrecadação total */}
            <div className="card" style={{ background: '#eafaf1', border: '2px solid #4caf50', minWidth: 260 }}>
              <h3 style={{ color: '#388e3c' }}>Arrecadação Total</h3>
              <div style={{ fontSize: 32, fontWeight: 700, color: '#388e3c', margin: '16px 0' }}>
                {isLoadingConsultas ? (
                  <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                    <CircularProgress />
                  </Box>
                ) : `R$ ${totalArrecadado.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`}
              </div>
            </div>
            <div className="card">
              <h3>Consultas de Hoje</h3>
              <div style={{ minHeight: 80 }}>
                {isLoadingConsultasHoje ? (
                  <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                    <CircularProgress />
                  </Box>
                ) : consultasHoje && consultasHoje.length > 0 ? (
                  <ul className="consultas-hoje-list">
                    {consultasHoje.map((consulta) => (
                      <li key={consulta.id} className="consulta-hoje-item">
                        <span className="consulta-hoje-nome">{consulta.paciente?.nome || '-'}</span>
                        <span className="consulta-hoje-hora">{consulta.dataHora ? new Date(consulta.dataHora).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) : '-'}</span>
                      </li>
                    ))}
                  </ul>
                ) : isError ? (
                  <p style={{ fontSize: '1.1rem', color: '#888', textAlign: 'center' }}>Erro ao carregar consultas.</p>
                ) : (
                  <p style={{ fontSize: '1.1rem', color: '#888', textAlign: 'center' }}>Sem consultas para hoje.</p>
                )}
              </div>
            </div>
            <div className="card">
              <h3>Materiais Restantes</h3>
              <div style={{ minHeight: 200 }}>
                {isLoading && (
                  <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                    <CircularProgress />
                  </Box>
                )}
                {isError && <p>Erro ao carregar materiais.</p>}
                {!isLoading && !isError && materiais && materiais.length > 0 ? (
                  <Bar data={chartData} options={chartOptions} />
                ) : null}
                {!isLoading && !isError && materiais && materiais.length === 0 && (
                  <p>Nenhum material cadastrado.</p>
                )}
              </div>
            </div>
          </div>

          {/* Tabela de pagamentos pendentes */}
          <div style={{ marginTop: 40 }}>
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 12 }}>
              <h3 style={{ color: '#b85c00', margin: 0, fontWeight: 700, fontSize: 22 }}>Pagamentos pendentes</h3>
            </div>
            {isLoadingConsultas ? (
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: 80 }}>
                <CircularProgress />
              </Box>
            ) : pagamentosPendentes.length === 0 ? (
              <p style={{ color: '#888', fontStyle: 'italic' }}>Nenhum pagamento pendente.</p>
            ) : (
              <div style={{ overflowX: 'auto', borderRadius: 14, boxShadow: '0 4px 24px 0 rgba(60,60,100,0.08)', background: 'rgba(255,255,255,0.95)', margin: '0 auto', maxWidth: 800 }}>
                <table style={{ width: '100%', borderCollapse: 'separate', borderSpacing: 0, minWidth: 400 }}>
                  <thead>
                    <tr style={{ background: 'linear-gradient(90deg, #e0eafc 0%, #f7f7f7 100%)' }}>
                      <th style={{ padding: '16px 12px', textAlign: 'left', fontWeight: 700, color: '#3a5ca8', fontSize: 17, borderTopLeftRadius: 14 }}>Paciente</th>
                      <th style={{ padding: '16px 12px', textAlign: 'left', fontWeight: 700, color: '#3a5ca8', fontSize: 17 }}>Data da consulta</th>
                      <th style={{ padding: '16px 12px', textAlign: 'left', fontWeight: 700, color: '#3a5ca8', fontSize: 17 }}>Valor</th>
                      <th style={{ padding: '16px 12px', textAlign: 'left', fontWeight: 700, color: '#3a5ca8', fontSize: 17, borderTopRightRadius: 14 }}>Data do vencimento</th>
                    </tr>
                  </thead>
                  <tbody>
                    {pagamentosPendentes.map((c, idx) => (
                      <tr key={c.id} style={{ background: idx % 2 === 0 ? '#f9fafd' : '#f4f7fb', transition: 'background 0.2s' }}>
                        <td style={{ padding: '14px 12px', fontWeight: 500, color: '#222', borderBottom: '1px solid #e0eafc' }}>{c.paciente?.nome || '-'}</td>
                        <td style={{ padding: '14px 12px', color: '#3a5ca8', fontWeight: 500, borderBottom: '1px solid #e0eafc' }}>{c.dataHora ? new Date(c.dataHora).toLocaleDateString('pt-BR') : '-'}</td>
                        <td style={{ padding: '14px 12px', color: '#b85c00', fontWeight: 600, borderBottom: '1px solid #e0eafc' }}>{c.valor ? `R$ ${c.valor.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}` : '-'}</td>
                        <td style={{ padding: '14px 12px', color: '#3a5ca8', fontWeight: 500, borderBottom: '1px solid #e0eafc' }}>{c.dataVencimento ? new Date(c.dataVencimento).toLocaleDateString('pt-BR') : '-'}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

export default Home;
