import React from 'react';

import {
  Modal, Box, Typography, Grid, IconButton, Button
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import PictureAsPdfIcon from '@mui/icons-material/PictureAsPdf';
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
  const size = { xs: 2, sm: 4, md: 6 };
  const ficha = paciente?.fichaMedica || {};

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Ficha Médica</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        {/* Dados do Paciente */}
        {/* <Typography variant="subtitle1" fontWeight="bold" gutterBottom>Dados do Paciente</Typography> */}
        <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Nome:</strong><br />&nbsp;{paciente?.nome || '-'}</Typography>
          </Grid>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Cirurgião:</strong><br />&nbsp;{ficha.cirurgiao || '-'}</Typography>
          </Grid>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Cirurgias anteriores:</strong><br />&nbsp;{ficha.cirurgiasAnteriores || '-'}</Typography>
          </Grid>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Alergias:</strong><br />&nbsp;{ficha.alergias || '-'}</Typography>
          </Grid>


        {/* Dados da Cirurgia */}
        {/* <Typography variant="subtitle1" fontWeight="bold" gutterBottom>Informações da Cirurgia</Typography> */}
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Data da cirurgia:</strong><br />&nbsp;{ficha.dataCirurgia || '-'}</Typography>
          </Grid>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Tipo de cirurgia:</strong><br />&nbsp;{ficha.cirurgia || '-'}</Typography>
          </Grid>
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Hospital:</strong><br />&nbsp;{ficha.hospital || '-'}</Typography>
          </Grid>
          
          {/* Observações */}
          <Grid item size={size}>
            <Typography noWrap variant='inherit'><strong>Anotações:</strong><br />&nbsp;{ficha.anotacoes || '-'}</Typography>
          </Grid>

        </Grid>

        {/* Botões */}
        <Grid container spacing={2} justifyContent="flex-end" mt={4}>
        <Grid item>
    <Button variant="outlined" startIcon={<PictureAsPdfIcon />} color="primary">
      PDF
       </Button>
  </Grid>
          <Grid item>
            <Button variant='contained' startIcon={<EditIcon />}>Editar</Button>
          </Grid>
          <Grid item>
            <Button variant='outlined' startIcon={<DeleteIcon />} color="error">Deletar</Button>
          </Grid>
        </Grid>
      </Box>
    </Modal>
  );
};
