import React, { useRef } from 'react';
import {
  Modal, Box, Typography, Grid, IconButton, Button
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import PictureAsPdfIcon from '@mui/icons-material/PictureAsPdf';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

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
  const printRef = useRef();

  const handleDownloadPDF = async () => {
    const element = printRef.current;
    const canvas = await html2canvas(element);
    const imgData = canvas.toDataURL('image/png');

    const pdf = new jsPDF();
    const imgProps = pdf.getImageProperties(imgData);
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

    pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
    pdf.save(`ficha_medica_${paciente?.nome || 'paciente'}.pdf`);
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <div ref={printRef}>
          <Grid container justifyContent="space-between" alignItems="center" mb={4}>
            <Typography variant="h6" fontWeight="bold">Ficha Médica</Typography>
            <IconButton onClick={handleClose}><CloseIcon /></IconButton>
          </Grid>

          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Nome:</strong> {paciente?.nome || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Cirurgião:</strong> {ficha.cirurgiao || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Cirurgias anteriores:</strong> {ficha.cirurgiasAnteriores || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Alergias:</strong> {ficha.alergias || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Data da cirurgia:</strong> {ficha.dataCirurgia || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Tipo de cirurgia:</strong> {ficha.cirurgia || '-'}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography><strong>Hospital:</strong> {ficha.hospital || '-'}</Typography>
            </Grid>
            <Grid item xs={12}>
              <Typography><strong>Anotações:</strong> {ficha.anotacoes || '-'}</Typography>
            </Grid>
          </Grid>
        </div>

        <Grid container spacing={2} justifyContent="flex-end" mt={4}>
          <Grid item>
            <Button variant="outlined" startIcon={<PictureAsPdfIcon />} onClick={handleDownloadPDF}>
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
