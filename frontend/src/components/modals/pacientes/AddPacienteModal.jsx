// src/components/AddPacienteModal.jsx
import React, { useState } from 'react';
import {
  Modal, Box, TextField, Button, IconButton, Grid, Typography
} from '@mui/material';
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
  const size = { xs: 2, sm: 4, md: 6 };

  const handleAddPaciente = () => {
    // Aqui você pode implementar a lógica para adicionar o paciente
    console.log('Paciente adicionado!');
    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Adicionar paciente</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item xs={6} size={size}><TextField fullWidth label="Nome do paciente" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="CPF" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Logradouro" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Rua" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Complemento" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Cidade" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Cep" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Celular" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="E-mail" /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Médico Responsável" /></Grid>
        </Grid>

        <Box mt={4} textAlign="right">
          <Button variant="contained" color="primary" onClick={handleAddPaciente}>
            Adicionar
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};
