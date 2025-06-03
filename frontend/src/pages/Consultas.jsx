import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddConsultaModal } from '../components/modals/consultas/AddConsultaModal';
import { DetalhesConsultaModal } from '../components/modals/consultas/DetalhesConsultaModal';

import { usePacientes } from '../hooks/usePacientes';

// Agora busca pacientes da API
const Consultas = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [consultaSelecionada, setConsultaSelecionada] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [tab, setTab] = useState('hoje');

  const { data: consultas, isLoading, isError } = useConsultas();

  // Funções de filtro
  const today = new Date();
  const isToday = (dataHora) => {
    const d = new Date(dataHora);
    return d.getDate() === today.getDate() && d.getMonth() === today.getMonth() && d.getFullYear() === today.getFullYear();
  };
  const isPast = (dataHora) => {
    const d = new Date(dataHora);
    return d < today.setHours(0,0,0,0);
  };
  const isFuture = (dataHora) => {
    const d = new Date(dataHora);
    return d > today.setHours(23,59,59,999);
  };

  let consultasFiltradas = consultas || [];
  if (tab === 'hoje') {
    consultasFiltradas = consultasFiltradas.filter(c => isToday(c.dataHora));
  } else if (tab === 'historico') {
    consultasFiltradas = consultasFiltradas.filter(c => isPast(c.dataHora));
  } else if (tab === 'futuras') {
    consultasFiltradas = consultasFiltradas.filter(c => isFuture(c.dataHora));
  }

  return (
    <div className="app-container">
      {/* Botão para abrir/fechar o sidebar */}
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
          <h2>Consultas</h2>

          <div className="tabs">
            <button className={`tab${tab === 'hoje' ? ' active' : ''}`} onClick={() => setTab('hoje')}>Hoje</button>
            <button className={`tab${tab === 'historico' ? ' active' : ''}`} onClick={() => setTab('historico')}>Histórico</button>
            <button className={`tab${tab === 'futuras' ? ' active' : ''}`} onClick={() => setTab('futuras')}>Futuras</button>
          </div>

          <div className="consulta-actions">
            <div className="search-bar">
              <input type="text" placeholder="Buscar por nome" />
              <FaSearch className="search-icon" />
            </div>
            <button className="btn-primary" onClick={() => setOpenAddModal(true)}>
              Adicionar
            </button>
          </div>

          <div className="consulta-list">
            {isLoading && <p>Carregando consultas...</p>}
            {isError && <p>Erro ao carregar consultas.</p>}
            {!isLoading && !isError && consultasFiltradas?.map((consulta, i) => (
              <div className="consulta-card" key={i}>
                <div className="consulta-date">
                  <span className="day">seg</span>
                  <strong className="number">{consulta.dia}</strong>
                </div>
                <div className="consulta-info" onClick={() => {
                  setConsultaSelecionada(consulta);
                  setOpenDetailsModal(true);
                }}>
                  <p><strong>🕒</strong> {consulta.hora}</p>
                  <p><strong>👤</strong> {consulta.paciente}</p>
                  <p><strong>Tipo:</strong> {consulta.tipo}</p>
                  <p><strong>Pagamento:</strong> {consulta.pagamentoRealizado ? 'realizado' : 'pendente'}</p>
                </div>
                <input type="checkbox" defaultChecked={consulta.consultaRealizada} />
              </div>
            ))}
            {!isLoading && !isError && consultasFiltradas.length === 0 && (
              <p style={{textAlign: 'center', color: '#888', marginTop: 32}}>Nenhuma consulta encontrada.</p>
            )}
          </div>
        </div>
      </main>

      <AddConsultaModal
        open={openAddModal}
        handleClose={() => setOpenAddModal(false)}
        pacientesExistentes={pacientes ? pacientes.map(p => p.nome) : []}
      />

      {consultaSelecionada && (
        <DetalhesConsultaModal
          open={openDetailsModal}
          handleClose={() => setOpenDetailsModal(false)}
          consulta={consultaSelecionada}
        />
      )}
    </div>
  );
};

export default Consultas;
