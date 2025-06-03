import React, { useRef, useState, useEffect } from 'react';
import {
  Modal, Box, Typography, Grid, IconButton, Button, TextField,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import SaveIcon from '@mui/icons-material/Save';
import EditIcon from '@mui/icons-material/Edit';
import PictureAsPdfIcon from '@mui/icons-material/PictureAsPdf';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

import { useAddFichaMedica, usePacienteById } from '../../../hooks/usePacientes';

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
  const size = { xs: 4, sm: 8, md: 12 };

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


  // Hook para editar ficha médica
  const addFichaMedica = useAddFichaMedica();
  const [editMode, setEditMode] = useState(false);
  const { refetch } = usePacienteById(paciente.pacienteId?.id);

  const [historicoMedico, setHistoricoMedico] = useState(ficha.historicoMedico || '');
  const [alergias, setAlergias] = useState(ficha.alergias || '');
  const [observacoes, setObservacoes] = useState(ficha.observacoes || '');

  const handleEdit = () => setEditMode(true);
  const handleSave = async () => {
      if (!historicoMedico || !alergias) {
          alert("Campos obrigatórios vazios!");
          return;
      }

      try {
          await addFichaMedica.mutateAsync(
              {
                  id: paciente.pacienteId?.id,
                  historicoMedico,
                  alergias,
                  observacoes
              },
              {
                  onSuccess: async () => {
                      await refetch();
                      handleClose();
                  }
              }
          );
      } catch (error) {
          console.error("Erro ao editar ficha médica:", error);
          alert("Erro ao editar ficha médica. Por favor, tente novamente.");
      }
      setEditMode(false);
  }


  // Limpando os estados
  useEffect(() => {
    setHistoricoMedico(paciente?.fichaMedica?.historicoMedico || '');
    setAlergias(paciente?.fichaMedica?.alergias || '');
    setObservacoes(paciente?.fichaMedica?.observacoes || '');
    setEditMode(false);
  }, [paciente, open]);

  useEffect(() => {
    if (!open) {
      setEditMode(false);
    }
  }, [open]);


  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <div ref={printRef}>
          <Grid container justifyContent="space-between" alignItems="center" mb={4} >
            <Typography variant="h6" fontWeight="bold">Ficha Médica</Typography>
            <IconButton onClick={handleClose}><CloseIcon /></IconButton>
          </Grid>

          <Grid container spacing={3} columns={{ xs: 4, sm: 8, md: 12 }}>
            <Grid item size={size}>
              <TextField disabled={!editMode} value={historicoMedico} label="Histórico Médico" fullWidth
                        multiline
                        onChange={(e) => setHistoricoMedico(e.target.value)}
              />
            </Grid>
            <Grid item size={size}>
              <TextField disabled={!editMode} value={alergias} label="Alergias" fullWidth
                        multiline
                        onChange={(e) => setAlergias(e.target.value)}
              />
            </Grid>
            <Grid item size={size}>
              <TextField disabled={!editMode} value={observacoes || '-'} label="Observações" fullWidth
                        multiline
                        onChange={(e) => setObservacoes(e.target.value)}
              />
            </Grid>
          </Grid>
        </div>

          {!editMode ? (
            <Grid container spacing={2} justifyContent="space-between" mt={4}>
                <Grid item>
                  <Button variant="contained" color='warning' startIcon={<PictureAsPdfIcon />} onClick={handleDownloadPDF}>
                    PDF
                  </Button>
                </Grid>

                <Grid item spacing={2} display="flex" justifyContent="flex-end">
                  <Button variant='contained' startIcon={<EditIcon />} onClick={handleEdit} >Editar</Button>
                </Grid>
            </Grid>

          ) : (
            <Grid container justifyContent="flex-end" mt={4}>      
                <Button variant='contained'
                        color='success'
                        startIcon={<SaveIcon />}
                        onClick={handleSave}
                        disabled={addFichaMedica.isLoading}
                        >
                  Salvar
                </Button>
            </Grid> 
          )}
      </Box>
    </Modal>
  );
};
