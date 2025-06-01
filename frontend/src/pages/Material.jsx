import React, { useState } from 'react';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddMaterialModal } from '../components/modals/material/AddMaterialModal';
import { EditMaterialModal } from '../components/modals/material/EditMaterialModal';

import { useMateriais, useCreateMaterial, useUpdateMaterial } from '../hooks/useMateriais';

const Material = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [materialSelecionado, setMaterialSelecionado] = useState(null);

  const { data: materiais, isLoading, isError } = useMateriais();
  const createMaterial = useCreateMaterial();
  const updateMaterial = useUpdateMaterial();

  const handleAddMaterial = async (novoMaterial) => {
    await createMaterial.mutateAsync(novoMaterial);
    setOpenAddModal(false);
  };

  const handleEditMaterial = async (materialEditado) => {
    await updateMaterial.mutateAsync({
      id: materialEditado.id,
      materialData: materialEditado,
    });
    setOpenEditModal(false);
  };

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
            {isLoading && <p>Carregando materiais...</p>}
            {isError && <p>Erro ao carregar materiais.</p>}
            {!isLoading && !isError && (
              <table className="table">
                <thead>
                  <tr>
                    <th>Material</th>
                    <th>Quantidade</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {materiais && materiais.map((material, idx) => (
                    <tr key={material.id || idx}>
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
            )}
          </div>
        </div>
      </main>

      <AddMaterialModal
        open={openAddModal}
        handleClose={() => setOpenAddModal(false)}
        onSave={handleAddMaterial}
      />

      {materialSelecionado && (
        <EditMaterialModal
          open={openEditModal}
          handleClose={() => setOpenEditModal(false)}
          material={materialSelecionado}
          onSave={handleEditMaterial}
        />
      )}
    </div>
  );
};

export default Material;
