import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddPacienteModal } from '../components/modals/pacientes/AddPacienteModal';
import { PacienteDetalhesModal } from '../components/modals/pacientes/PacienteDetalhesModal';
import { DeletePacienteModal } from '../components/modals/pacientes/DeletePacienteModal'; // novo import

// Exemplo de paciente (substitua depois com dados reais do back-end)
export const pacientes = [
  {
    nome: 'João Silva',
    cpf: '123.456.789-00',
    endereco: 'Rua A, 123',
    profissao: 'Professor',
    celular: '(81) 91234-5678',
    consultas: [
      {
        tipo: 'intraoperatório',
        dia: '15',
        hora: '09:00',
        metodoPagamento: 'pix',
        valor: 'R$ 200,00',
        pagamentoRealizado: true, //
        consultaRealizada: true, //
        observacoes: 'Paciente teve evolução grande.'
      },
      {
        tipo: 'intraoperatório',
        dia: '15',
        hora: '09:00',
        metodoPagamento: 'pix',
        valor: 'R$ 200,00',
        pagamentoRealizado: false, //
        consultaRealizada: true, //
        observacoes: 'Paciente teve evolução grande.'
      }
    ]
  },

  {
    nome: 'Maria Oliveira',
    cpf: '987.654.321-00',
    endereco: 'Rua B, 456',
    profissao: 'Engenheira',
    celular: '(81) 98765-4321',
    consultas: [
      {
        tipo: 'pré-operatório',
        dia: '16',
        hora: '10:30',
        metodoPagamento: 'cartao',
        valor: 'R$ 150,00',
        pagamentoRealizado: true, //
        consultaRealizada: false, //
        observacoes: 'Paciente está estável.'
      }
    ]
  },

  // Adicione mais pacientes aqui
];


const Pacientes = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [openDeleteModal, setOpenDeleteModal] = useState(false); // novo estado
  const [pacienteSelecionado, setPacienteSelecionado] = useState(null);


  return (
    <div className="app-container">
      <Sidebar />

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
            {pacientes.map((paciente, idx) => (
              <div
                key={idx}
                className="paciente-card"
                onClick={() => {
                  setPacienteSelecionado(paciente);
                  setOpenDetailsModal(true);
                }}
              >
                <div className="paciente-info">
                  <span className="avatar">👤</span>
                  <span>{paciente.nome}</span>
                </div>
                <button className="btn-secondary">Ficha médica</button>
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

      <DeletePacienteModal
        open={openDeleteModal}
        handleClose={() => setOpenDeleteModal(false)}
        pacientes={pacientes}
      />
    </div>
  );
};

export default Pacientes;
