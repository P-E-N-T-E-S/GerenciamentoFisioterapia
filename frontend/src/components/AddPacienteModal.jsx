// src/components/AddPacienteModal.jsx
import React from 'react';
import { Modal, Box, TextField, Button, IconButton, Grid, Typography } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
  minWidth: 500,
};

export const AddPacienteModal = ({ open, handleClose }) => {
  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center">
          <Typography variant="h6" fontWeight="bold">Adicionar paciente</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={2} mt={1}>
          <Grid item xs={6}><TextField fullWidth label="Nome do paciente" placeholder="Placeholder" /></Grid>
          <Grid item xs={6}><TextField fullWidth label="CPF" placeholder="Placeholder" /></Grid>
          <Grid item xs={6}><TextField fullWidth label="Endereço" placeholder="Placeholder" /></Grid>
          <Grid item xs={6}><TextField fullWidth label="Profissão" placeholder="Placeholder" /></Grid>
          <Grid item xs={6}><TextField fullWidth label="Celular" placeholder="Placeholder" /></Grid>
          <Grid item xs={6}><TextField fullWidth label="E-mail" placeholder="Placeholder" /></Grid>
        </Grid>

        <Box mt={3} textAlign="right">
          <Button variant="contained" color="primary">Continuar</Button>
        </Box>
      </Box>
    </Modal>
  );
};
