// src/components/DeletePacienteModal.jsx
import React from 'react';
import {
  Modal, Box, Typography, IconButton, List, ListItem, ListItemText, ListItemSecondaryAction, Button
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

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

export const DeletePacienteModal = ({ open, handleClose, pacientes }) => {
  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight="bold">Deletar pacientes</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Box>

        <List>
          {pacientes.map((paciente, idx) => (
            <ListItem key={idx} divider>
              <ListItemText primary={paciente.nome} secondary={paciente.cpf} />
              <ListItemSecondaryAction>
                <Button variant="outlined" color="error">Deletar</Button>
              </ListItemSecondaryAction>
            </ListItem>
          ))}
        </List>
      </Box>
    </Modal>
  );
};
