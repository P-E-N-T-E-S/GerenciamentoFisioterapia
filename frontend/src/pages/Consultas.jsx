import React, { useState } from 'react';
import {
  FaSearch
} from 'react-icons/fa';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddConsultaModal } from '../components/modals/consultas/AddConsultaModal';

// Importando o array de pacientes
import { pacientes } from './Pacientes';
// dado hardcoded vindo de Pacientes.jsx
const consultasData = pacientes.flatMap(paciente => 
  (paciente.consultas || []).map(consulta => ({
    ...consulta,
    paciente: paciente.nome
  }))
);
// const consultasData = [
//   {
//     day: 'Seg',
//     number: 15,
//     time: '09:00',
//     patient: 'Maria da Silva',
//     type: 'intraoperatório',
//     payment: 'realizado',
//     completed: true
//   },
//   {
//     day: 'Ter',
//     number: 16,
//     time: '10:30',
//     patient: 'João Pedro',
//     type: 'pré-operatório',
//     payment: 'pendente',
//     completed: true
//   },
//   {
//     day: 'Qua',
//     number: 17,
//     time: '14:00',
//     patient: 'Ana Souza',
//     type: 'pós-operatório',
//     payment: 'pendente',
//     completed: false
//   }
// ];


const Consultas = () => {
  const [openAddModal, setOpenAddModal] = useState(false);


  return (
    <div className="app-container">
      <Sidebar />

      <main className="main-content">
        <Header />

        <div className="dashboard center-material">
          <h2>Consultas</h2>

          <div className="tabs">
            <button className="tab active">Hoje</button>
            <button className="tab">Futuro</button>
            <button className="tab">Histórico</button>
          </div>

          <div className="consulta-actions">
            <div className="search-bar">
              <input type="text" placeholder="Buscar por nome" />
              <FaSearch className="search-icon" />
            </div>
            <button className="btn-primary" onClick={() => setOpenAddModal(true)}>
              Adicionar
            </button>
            <select className="dropdown">
              <option>Filtro</option>
            </select>
          </div>

          <div className="consulta-list">
            {consultasData.map((consulta, i) => (
              <div className="consulta-card" key={i}>
                <div className="consulta-date">
                  <span className="day">seg</span>
                  {/* <span className="day">{consulta.dia}</span> */}
                  <strong className="number">{consulta.dia}</strong>
                </div>
                <div className="consulta-info">
                  <p><strong>🕒</strong> {consulta.hora}</p>
                  <p><strong>👤</strong> {consulta.paciente}</p>
                  <p><strong>Tipo:</strong> {consulta.tipo}</p>
                  <p><strong>Pagamento:</strong> {consulta.pagamentoRealizado ? 'realizado' : 'pendente'}</p>
                </div>
                <input type="checkbox" defaultChecked={consulta.consultaRealizada} />
              </div>
            ))}
          </div>
        </div>
      </main>

      <AddConsultaModal open={openAddModal} handleClose={() => setOpenAddModal(false) } 
      pacientesExistentes={consultasData.map(consulta => consulta.paciente)}/>
    </div>
  );
};

export default Consultas;
