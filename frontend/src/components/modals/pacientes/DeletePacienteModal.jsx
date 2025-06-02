import React, { useState } from 'react';
import {
  Modal, Box, Typography, IconButton, List, ListItem, ListItemText, Button, TextField
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { usePacientes, usePacienteByName, useDeletePaciente } from '../../../hooks/usePacientes';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 500,
  bgcolor: 'background.paper',
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
};

export const DeletePacienteModal = ({ open, handleClose }) => {
  const [busca, setBusca] = useState('');
  const { data: pacientesAll, isLoading, isError } = usePacientes();
  const { data: pacientesFiltrados } = usePacienteByName(busca);
  const pacientes = busca.length > 0 ? pacientesFiltrados : pacientesAll;

  // Hook delete de Pacientes
  const deletePaciente = useDeletePaciente();
  const handleDelete = async (pacienteId) => {
    deletePaciente.mutate(pacienteId, {
      onSuccess: () => {
        alert('Paciente deletado com sucesso!');
        // handleClose();
      },
      onError: (error) => {
        alert(`Erro ao deletar paciente: ${error.message}`);
      }
    });
  }

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight="bold">Deletar pacientes</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Box>

        <TextField
          fullWidth
          label="Buscar por nome"
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          sx={{ mb: 2 }}
        />

        {isLoading && <Typography>Carregando pacientes...</Typography>}
        {isError && <Typography color="error">Erro ao carregar pacientes.</Typography>}

        <List>
          {pacientes?.map((paciente, idx) => (
            <ListItem key={idx} divider>
              <ListItemText
                primary={paciente.nome}
                secondary={typeof paciente.cpf === 'string' ? paciente.cpf : ''}
              />
              <Button variant="outlined" color="error" 
                onClick={() => handleDelete(paciente.pacienteId?.id)}
                disabled={deletePaciente.isLoading}
              >
                Deletar
              </Button>
            </ListItem>
          ))}
        </List>
      </Box>
    </Modal>
  );
};
