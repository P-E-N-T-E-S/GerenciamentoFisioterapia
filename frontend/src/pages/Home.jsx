import React, { useState } from 'react';
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { useMateriais } from '../hooks/useMateriais';
import { useConsultaByDate } from '../hooks/useConsultas';

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
            <div className="card">
              <h3>Consultas de Hoje</h3>
              <div style={{ minHeight: 80 }}>
                {isLoadingConsultasHoje ? (
                  <p style={{ fontSize: '1.1rem', color: '#888', textAlign: 'center' }}>Carregando...</p>
                ) : consultasHoje && consultasHoje.length > 0 ? (
                  <ul className="consultas-hoje-list">
                    {consultasHoje.map((consulta) => (
                      <li key={consulta.id} className="consulta-hoje-item">
                        <span className="consulta-hoje-nome">{consulta.paciente?.nome || '-'}</span>
                        <span className="consulta-hoje-hora">{consulta.dataHora ? new Date(consulta.dataHora).toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) : '-'}</span>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p style={{ fontSize: '1.1rem', color: '#888', textAlign: 'center' }}>Sem consultas para hoje.</p>
                )}
              </div>
            </div>
            <div className="card">
              <h3>Materiais Restantes</h3>
              <div style={{ minHeight: 200 }}>
                {isLoading && <p>Carregando...</p>}
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
        </div>
      </main>
    </div>
  );
};

export default Home;
