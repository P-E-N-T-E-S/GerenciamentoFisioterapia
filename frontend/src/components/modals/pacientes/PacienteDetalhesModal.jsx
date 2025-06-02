// src/components/PacienteDetalhesModal.jsx
import React from 'react';
import {
  Modal, Box, Typography, Grid, IconButton, Stack
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import AccessTimeIcon from '@mui/icons-material/AccessTime';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
  width: 600,
  maxHeight: '90vh',
  overflowY: 'auto',
};

export const PacienteDetalhesModal = ({ open, handleClose, paciente }) => {
  const size = { xs: 2, sm: 4, md: 6 };

  // Garante que consultas é sempre um array
  const consultas = (paciente && Array.isArray(paciente.consultas)) ? paciente.consultas : [];

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Dados do paciente</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={2} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item xs={6} size={size}><Typography><strong>Nome do paciente</strong><br />{paciente.nome}</Typography></Grid>
          <Grid item xs={6} size={size}><Typography><strong>CPF</strong><br />{paciente.cpf}</Typography></Grid>
          <Grid item xs={6} size={size}><Typography><strong>Endereço</strong><br />{paciente.endereco}</Typography></Grid>
          <Grid item xs={6} size={size}><Typography><strong>Profissão</strong><br />{paciente.profissao}</Typography></Grid>
          <Grid item xs={6} size={size}><Typography><strong>Celular</strong><br />{paciente.celular}</Typography></Grid>
        </Grid>

        <Typography variant="h6" mt={4} fontWeight="bold">
          {consultas.length > 0 ? 'Consultas' : '-- Não há consultas --'}
        </Typography>

        {consultas.map((consulta, idx) => (
          <Box key={idx} mt={1} p={2} border="1px solid #ccc" borderRadius={2}>
            <Grid container spacing={2}>
              <Grid item>
                <Box bgcolor="#f0f0f0" borderRadius={1} p={1} textAlign="center">
                  <Typography variant="body2" fontWeight="bold">Seg</Typography>
                  <Typography variant="h6">{consulta.dia}</Typography>
                </Box>
              </Grid>
              <Grid item xs>
                <Stack spacing={0.5}>
                  <Typography><AccessTimeIcon fontSize="small" /> {consulta.hora}</Typography>
                  <Typography><strong>Tipo:</strong> {consulta.tipo}</Typography>
                  <Typography><strong>Pagamento:</strong> {consulta.pagamentoRealizado ? 'realizado' : 'pendente'}</Typography>
                  <Typography><strong>Observações:</strong> {consulta.observacoes}</Typography>
                </Stack>
              </Grid>
            </Grid>
          </Box>
        ))}
      </Box>
    </Modal>
  );
};
