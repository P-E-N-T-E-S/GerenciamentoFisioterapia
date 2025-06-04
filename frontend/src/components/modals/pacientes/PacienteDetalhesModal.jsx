// src/components/PacienteDetalhesModal.jsx
import React, { useState, useEffect } from 'react';
import {
  Modal, Box, Typography, Grid, IconButton, Stack, TextField, Button
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';

import { useUpdatePaciente, usePacienteById } from '../../../hooks/usePacientes';

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
  const cpf = paciente.cpf?.codigo || '';
  const endereco = paciente.endereco || {};
  const fichaMedica = paciente.fichaMedica || {};

  // Estados para edição
  const [editMode, setEditMode] = useState(false);
  const [nome, setNome] = useState(paciente.nome || '');
  const [contato, setContato] = useState(paciente.contato || '');
  const [medicoResponsavel, setMedicoResponsavel] = useState(paciente.medicoResponsavel || '');
  const [cpfEdit, setCpfEdit] = useState(cpf);
  const [email, setEmail] = useState(paciente.email || '');

  const updatePaciente = useUpdatePaciente();
  const { refetch } = usePacienteById(paciente.pacienteId?.id);

  const handleEdit = () => setEditMode(true);
  const handleSave = async () => {
    await updatePaciente.mutateAsync({
      id: paciente.pacienteId?.id,
      pacienteData: {
        nome,
        contato,
        medicoResponsavel,
        cpf: cpfEdit,
        email,
        endereco,
        // fichaMedica
      }
    },
    {
      onSuccess: async () => {
        alert('Paciente atualizado com sucesso!');
        await refetch();
        handleClose();
      }
    }
  );
    setEditMode(false);
  }


  // Limpando estados
  useEffect(() => {
    setNome(paciente.nome || '');
    setContato(paciente.contato || '');
    setMedicoResponsavel(paciente.medicoResponsavel || '');
    setCpfEdit(paciente.cpf?.codigo || '');
    setEmail(paciente.email || '');
  }, [paciente]);

  useEffect(() => {
    if (!open) {
      setEditMode(false);
    }
  }, [open]);

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Dados do paciente</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={3} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item xs={6} size={size}>
            <TextField  disabled={!editMode} value={nome} label="Nome do paciente" fullWidth 
            onChange={e => setNome(e.target.value)}/>
          </Grid>
          <Grid item xs={6} size={size}>
            <TextField disabled={!editMode} value={cpfEdit} label="CPF" fullWidth
            onChange={e => setCpfEdit(e.target.value)}/>
          </Grid>
          <Grid item xs={6} size={size}>
            <TextField disabled={!editMode} value={email} label="Email" fullWidth
            onChange={e => setEmail(e.target.value)}/>
          </Grid>
          <Grid item xs={6} size={size}>
            <TextField disabled={!editMode} value={contato} label="Telefone" fullWidth
            onChange={e => setContato(e.target.value)}/>
          </Grid>
          <Grid item xs={6} size={size}>
            <TextField disabled={!editMode} value={medicoResponsavel} label="Médico Responsável" fullWidth
            onChange={e => setMedicoResponsavel(e.target.value)}/>
          </Grid>
        </Grid>

        {/* <Typography variant="h6" mt={4} fontWeight="bold">
          {consultas && consultas.length > 0 ? 'Consultas' : '-- Não há consultas --'}
        </Typography>

        {consultas && consultas.map((consulta, idx) => (
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
        ))} */}

        {/* Footer */}
        <Grid item spacing={2} display="flex" justifyContent="flex-end" sx={{ mt: 4 }}>
          {!editMode ? (
            <Button variant='contained' startIcon={<EditIcon />} onClick={handleEdit}>Editar</Button>
          ) :
          (
            <Button variant='contained'
                    color='success'
                    startIcon={<SaveIcon />}
                    onClick={handleSave}
                    disabled={updatePaciente.isLoading}
                    >
              Salvar
            </Button>
          )}
        </Grid>
      </Box>
    </Modal>
  );
};
