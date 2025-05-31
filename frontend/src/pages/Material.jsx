import React, { useState } from 'react';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddMaterialModal } from '../components/modals/material/AddMaterialModal';
import { EditMaterialModal } from '../components/modals/material/EditMaterialModal';

const Material = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [materialSelecionado, setMaterialSelecionado] = useState(null);

  const [materiais, setMateriais] = useState([
    { nome: 'Faixa elástica', quantidade: 10 },
    { nome: 'Bola de Pilates', quantidade: 5 },
    { nome: 'Colchonete', quantidade: 12 }
  ]);

  return (
    <div className="app-container">
      <Sidebar />

      <main className="main-content">
        <Header />

        <div className="dashboard center-material">
          <h2 style={{ marginBottom: '20px' }}>Material</h2>

          <div className="material-actions">
            <button className="btn-primary" onClick={() => setOpenAddModal(true)}>Adicionar material</button>
            <select className="dropdown">
              <option value="">Filtro</option>
            </select>
          </div>

          <div className="table-wrapper">
            <table className="table">
              <thead>
                <tr>
                  <th>Material</th>
                  <th>Quantidade</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {materiais.map((material, idx) => (
                  <tr key={idx}>
                    <td>{material.nome}</td>
                    <td>{material.quantidade}</td>
                    <td>
                      <button
                        className="btn-secondary"
                        onClick={() => {
                          setMaterialSelecionado({ ...material, index: idx });
                          setOpenEditModal(true);
                        }}
                      >
                        Editar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </main>

      <AddMaterialModal open={openAddModal} handleClose={() => setOpenAddModal(false)} />

      {materialSelecionado && (
        <EditMaterialModal
          open={openEditModal}
          handleClose={() => setOpenEditModal(false)}
          material={materialSelecionado}
          onSave={(novoMaterial) => {
            const novos = [...materiais];
            novos[materialSelecionado.index] = {
              nome: novoMaterial.nome,
              quantidade: novoMaterial.quantidade
            };
            setMateriais(novos);
            setOpenEditModal(false);
          }}
        />
      )}
    </div>
  );
};

export default Material;
