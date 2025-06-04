import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import '../styles/Home.css';

import { Button, Box, CircularProgress, Card, CardContent, Typography, Avatar, IconButton as MuiIconButton, Tooltip, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions } from '@mui/material';

import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import CancelIcon from '@mui/icons-material/Cancel';

import { Sidebar } from '../components/template/Sidebar';
import { Header } from '../components/template/Header';
import { AddConsultaModal } from '../components/modals/consultas/AddConsultaModal';
import { DetalhesConsultaModal } from '../components/modals/consultas/DetalhesConsultaModal';

import { useConsultas } from '../hooks/useConsultas'; // Hook correto
import { useDeleteConsulta } from '../hooks/useConsultas'; // Hook para deletar consultas

const Consultas = () => {
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openDetailsModal, setOpenDetailsModal] = useState(false);
  const [consultaSelecionada, setConsultaSelecionada] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [tab, setTab] = useState('hoje');
  // Para confirmação de exclusão
  const [openConfirmDialog, setOpenConfirmDialog] = useState(false);
  const [consultaIdToDelete, setConsultaIdToDelete] = useState(null);

  const { data: consultas, isLoading, isError } = useConsultas();

  // Hook para deletar consultas
  const deleteConsultas = useDeleteConsulta();
  const handleDeleteConsulta = (consultaId) => {
    deleteConsultas.mutate(consultaId);
    setOpenConfirmDialog(false);
    setConsultaIdToDelete(null);
  };

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
            {isLoading && 
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
                <CircularProgress />
              </Box>
            }
            {isError && <p>Erro ao carregar consultas.</p>}
            {!isLoading && !isError && consultasFiltradas?.map((consulta, i) => {
              const diasSemana = ['dom', 'seg', 'ter', 'qua', 'qui', 'sex', 'sáb'];
              const data = new Date(consulta.dataHora);
              const diaSemana = diasSemana[data.getDay()];
              // Avatar: para historico/futuras mostra dia/mes (sem zero à esquerda), para hoje só o dia
              let avatarText = data.getDate().toString();
              if (tab === 'historico' || tab === 'futuras') {
                avatarText += '/' + (data.getMonth() + 1).toString();
              }
              return (
                <Card key={i} sx={{ display: 'flex', alignItems: 'center', mb: 2, boxShadow: 2, borderRadius: 2, background: '#f9f9f9', transition: 'box-shadow 0.2s', '&:hover': { boxShadow: 6, background: '#f1f8e9' } }}>
                  <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', px: 2, py: 1, minWidth: 64 }}>
                    <Avatar sx={{ bgcolor: '#1976d2', width: 48, height: 48, fontSize: 18, mb: 0.5 }}>
                      {avatarText}
                    </Avatar>
                    <Typography variant="caption" color="text.secondary" sx={{ textTransform: 'uppercase', fontWeight: 500 }}>
                      {diaSemana}
                    </Typography>
                  </Box>
                  <CardContent sx={{ flex: 1, cursor: 'pointer', py: 2 }} onClick={() => {
                    setConsultaSelecionada(consulta);
                    setOpenDetailsModal(true);
                  }}>
                    <Typography variant="subtitle1" sx={{ fontWeight: 600, mb: 0.5 }}>
                      {consulta.paciente?.nome || 'Paciente não informado'}
                    </Typography>
                    <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
                      <strong>🕒</strong> {data.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                    </Typography>
                    {/* <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
                      <strong>Descrição:</strong> {consulta.descricao}
                    </Typography> */}
                    <Typography variant="body2" color={consulta.clientePagou ? 'success.main' : 'warning.main'} sx={{ fontWeight: 500 }}>
                      <strong>Pagamento:</strong> {consulta.clientePagou ? 'Realizado' : 'Pendente'}
                    </Typography>
                  </CardContent>
                  <Box sx={{ pr: 2 }}>
                    <Tooltip title="Cancelar consulta">
                      <MuiIconButton
                        aria-label="delete"
                        size="large"
                        onClick={() => {
                          setConsultaIdToDelete(consulta.consultaId?.id);
                          setOpenConfirmDialog(true);
                        }}
                      >
                        <CancelIcon color='error' />
                      </MuiIconButton>
                    </Tooltip>
                  </Box>
      {/* Dialog de confirmação de cancelamento */}
      <Dialog
        open={openConfirmDialog}
        onClose={() => { setOpenConfirmDialog(false); setConsultaIdToDelete(null); }}
      >
        <DialogTitle>Cancelar consulta</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza que deseja cancelar esta consulta? Esta ação não poderá ser desfeita.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => { setOpenConfirmDialog(false); setConsultaIdToDelete(null); }} color="inherit">
            Não
          </Button>
          <Button onClick={() => handleDeleteConsulta(consultaIdToDelete)} color="error" variant="contained">
            Sim, cancelar
          </Button>
        </DialogActions>
      </Dialog>
                </Card>
              );
            })}
            {!isLoading && !isError && consultasFiltradas.length === 0 && (
              <p style={{textAlign: 'center', color: '#888', marginTop: 32}}>Nenhuma consulta encontrada.</p>
            )}
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
