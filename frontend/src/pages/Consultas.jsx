import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddConsultaModal } from '../components/modals/consultas/AddConsultaModal';
import { DetalhesConsultaModal } from '../components/modals/consultas/DetalhesConsultaModal';

import { useConsultas } from '../hooks/useConsultas'; // Hook correto

const Consultas = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [consultaSelecionada, setConsultaSelecionada] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const { data: consultas, isLoading, isError } = useConsultas();

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
          <h2>Consultas</h2>

          <div className="tabs">
            <button className="tab active">Hoje</button>
            <button className="tab">Geral</button>
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
            {!isLoading && !isError && consultas?.map((consulta, i) => (
              <div className="consulta-card" key={i}>
                <div className="consulta-date">
                  <span className="day">seg</span>
                  <strong className="number">{new Date(consulta.dataHora).getDate()}</strong>
                </div>
                <div className="consulta-info" onClick={() => {
                  setConsultaSelecionada(consulta);
                  setOpenDetailsModal(true);
                }}>
                  <p><strong>🕒</strong> {new Date(consulta.dataHora).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</p>
                  <p><strong>👤</strong> {consulta.paciente?.nome || 'Paciente não informado'}</p>
                  <p><strong>Descrição:</strong> {consulta.descricao}</p>
                  <p><strong>Pagamento:</strong> {consulta.pagamentoRealizado ? 'realizado' : 'pendente'}</p>
                </div>
                <input type="checkbox" defaultChecked={consulta.consultaRealizada} />
              </div>
            ))}
          </div>
        </div>
      </main>

      <AddConsultaModal
        open={openAddModal}
        handleClose={() => setOpenAddModal(false)}
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
