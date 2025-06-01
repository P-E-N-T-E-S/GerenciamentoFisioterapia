import React from 'react';
import '../styles/Home.css';
import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { useMateriais } from '../hooks/useMateriais';

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
  const { data: materiais, isLoading, isError } = useMateriais();

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
      <Sidebar />

      <main className="main-content">
        <Header />

        <div className="dashboard">
          <div className="cards-row">
            <div className="card">
              <h3>Consultas de Hoje</h3>
              <div className="placeholder">[Tabela de Consultas]</div>
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
