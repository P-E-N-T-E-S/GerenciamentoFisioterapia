import React from 'react';
import {
  Modal, Box, Typography, Grid, IconButton, Button, Divider
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
  width: 650,
  maxHeight: '90vh',
  overflowY: 'auto',
};

export const FichaMedicaModal = ({ open, handleClose, paciente }) => {
  const ficha = paciente?.fichaMedica || {};

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight="bold">Ficha Médica</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        {/* Dados do Paciente */}
        <Typography variant="subtitle1" fontWeight="bold" gutterBottom>Dados do Paciente</Typography>
        <Grid container spacing={2} mb={2}>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Nome:</strong><br />{paciente?.nome || '-'}</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Cirurgião:</strong><br />{ficha.cirurgiao || '-'}</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Cirurgias anteriores:</strong><br />{ficha.cirurgiasAnteriores || '-'}</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Alergias:</strong><br />{ficha.alergias || '-'}</Typography>
          </Grid>
        </Grid>

        <Divider sx={{ my: 2 }} />

        {/* Dados da Cirurgia */}
        <Typography variant="subtitle1" fontWeight="bold" gutterBottom>Informações da Cirurgia</Typography>
        <Grid container spacing={2} mb={2}>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Data da cirurgia:</strong><br />{ficha.dataCirurgia || '-'}</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <Typography><strong>Tipo de cirurgia:</strong><br />{ficha.cirurgia || '-'}</Typography>
          </Grid>
          <Grid item xs={12}>
            <Typography><strong>Hospital:</strong><br />{ficha.hospital || '-'}</Typography>
          </Grid>
        </Grid>

        <Divider sx={{ my: 2 }} />

        {/* Observações */}
        <Typography variant="subtitle1" fontWeight="bold" gutterBottom>Anotações</Typography>
        <Typography mb={3}>{ficha.anotacoes || '-'}</Typography>

        {/* Botões */}
        <Grid container spacing={2} justifyContent="flex-end">
          <Grid item>
            <Button variant="outlined" color="grey">Editar</Button>
          </Grid>
          <Grid item>
            <Button variant="outlined" color="grey">Deletar</Button>
          </Grid>
        </Grid>
      </Box>
    </Modal>
  );
};
