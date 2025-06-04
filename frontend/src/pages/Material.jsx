import React, { useState } from 'react';
import '../styles/Home.css';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddMaterialModal } from '../components/modals/material/AddMaterialModal';
import { EditMaterialModal } from '../components/modals/material/EditMaterialModal';

import { useMateriais } from '../hooks/useMateriais';

import { Box, CircularProgress } from '@mui/material';

const Material = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [materialSelecionado, setMaterialSelecionado] = useState(null);

  const [sidebarOpen, setSidebarOpen] = useState(false);

  const { data: materiais, isLoading, isError } = useMateriais();

  // Apenas exibe alerta por enquanto
  const handleDeleteClick = (nome) => {
    alert(`Você clicou para excluir o material: ${nome}`);
    // Aqui você poderá chamar o hook de exclusão depois
  };

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
          <h2 style={{ marginBottom: '20px' }}>Material</h2>

          <div className="material-actions">
            <button className="btn-primary" onClick={() => setOpenAddModal(true)}>Adicionar material</button>
          </div>

          <div className="table-wrapper">
            {isLoading && 
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                <CircularProgress />
              </Box>
            }
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
      />

      {materialSelecionado && (
        <EditMaterialModal
          open={openEditModal}
          handleClose={() => setOpenEditModal(false)}
          material={materialSelecionado}
        />
      )}
    </div>
  );
};

export default Material;
