import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';
import { Sidebar } from '../components/Sidebar';
import { Header } from '../components/Header';
import { AddPacienteModal } from '../components/AddPacienteModal';
import { PacienteDetalhesModal } from '../components/PacienteDetalhesModal';
import { DeletePacienteModal } from '../components/DeletePacienteModal'; // novo import

const Pacientes = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [openDeleteModal, setOpenDeleteModal] = useState(false); // novo estado
  const [pacienteSelecionado, setPacienteSelecionado] = useState(null);

  // Exemplo de paciente (substitua depois com dados reais do back-end)
  const pacientes = [
    {
      nome: 'João Silva',
      cpf: '123.456.789-00',
      endereco: 'Rua A, 123',
      profissao: 'Professor',
      celular: '(81) 91234-5678',
      consultas: [
        {
          dia: '15', hora: '09:00',
          tipo: 'intraoperatório',
          pagamento: 'pendente',
          observacoes: 'Paciente teve evolução grande.'
        },
        {
          dia: '15', hora: '09:00',
          tipo: 'intraoperatório',
          pagamento: 'pendente',
          observacoes: 'Paciente teve evolução grande.'
        }
      ]
    }
  ];

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
