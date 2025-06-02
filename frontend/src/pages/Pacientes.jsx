import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';

import { Button, IconButton } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddPacienteModal } from '../components/modals/pacientes/AddPacienteModal';
import { PacienteDetalhesModal } from '../components/modals/pacientes/PacienteDetalhesModal';
import { DeletePacienteModal } from '../components/modals/pacientes/DeletePacienteModal';
import { FichaMedicaModal } from '../components/modals/pacientes/FichaMedicaModal';
import { AddFichaMedicaModal } from '../components/modals/pacientes/AddFichaMedicaModal';

import { usePacientes } from '../hooks/usePacientes';


const Pacientes = () => {
  // Modais de pacientes
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [openDeleteModal, setOpenDeleteModal] = useState(false);

  const [sidebarOpen, setSidebarOpen] = useState(false);

  // Ficha médica
  const [openFichaModal, setOpenFichaModal] = useState(false);
  const [openAddFichaMedicaModal, setOpenAddFichaMedicaModal] = useState(false);

  const [pacienteSelecionado, setPacienteSelecionado] = useState(null);

  // Hook de listar pacientes
  const { data: pacientes, isLoading, isError } = usePacientes();

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
          <h2>Pacientes</h2>

          <div className="paciente-actions">
            <div className="search-bar">
              <input type="text" placeholder="Buscar por nome" />
              <FaSearch className="search-icon" />
            </div>
            <div className="btn-group">
              <button className="btn-primary" onClick={() => setOpenAddModal(true)}>Adicionar</button>
              <button className="btn-secondary" onClick={() => setOpenDeleteModal(true)}>Deletar</button>
            </div>
          </div>

          <div className="paciente-list">
            {isLoading && <p>Carregando pacientes...</p>}
            {isError && <p>Erro ao carregar pacientes.</p>}
            {pacientes && pacientes.map((paciente, idx) => (
              <div
                key={idx}
                className="paciente-card"
              >
                <div className="paciente-info">
                  <span className="avatar">👤</span>
                  <span className="paciente-name"
                    onClick={() => {
                      setPacienteSelecionado(paciente);
                      setOpenDetailsModal(true);
                    }}>
                    {paciente.nome}
                  </span>
                </div>
                <div className="card-buttons">

                  {paciente.fichaMedica && Object.keys(paciente.fichaMedica).length > 0 ? (

                    <button className="btn-secondary" onClick={(e) => {
                      e.stopPropagation();
                      setPacienteSelecionado(paciente);
                      setOpenFichaModal(true);
                    }}>
                      Ficha médica
                    </button>
                  )
                    : (
                      <IconButton aria-label='Adicionar ficha médica'
                        sx={{ borderRadius: 50, backgroundColor: '#ddd', color: '#000', mr: 2, '&:hover': { backgroundColor: '#ccc' } }}
                        onClick={(e) => {
                          e.stopPropagation();
                          setPacienteSelecionado(paciente);
                          setOpenAddFichaMedicaModal(true);
                        }}>
                        <AddIcon />
                      </IconButton>
                    )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>

      <AddPacienteModal open={openAddModal} handleClose={() => setOpenAddModal(false)} />

      {pacienteSelecionado && (
        <PacienteDetalhesModal
          open={openDetailsModal}
          handleClose={() => setOpenDetailsModal(false)}
          paciente={pacienteSelecionado}
        />
      )}

      {pacienteSelecionado && (
        <FichaMedicaModal
          open={openFichaModal}
          handleClose={() => setOpenFichaModal(false)}
          paciente={pacienteSelecionado}
        />
      )}

      <DeletePacienteModal
        open={openDeleteModal}
        handleClose={() => setOpenDeleteModal(false)}
        pacientes={pacientes || []}
      />

      {pacienteSelecionado && (
        <AddFichaMedicaModal
          open={openAddFichaMedicaModal}
          handleClose={() => setOpenAddFichaMedicaModal(false)}
          paciente={pacienteSelecionado}
        />
      )}

    </div>
  );
};

export default Pacientes;