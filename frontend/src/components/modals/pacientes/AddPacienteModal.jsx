// src/components/AddPacienteModal.jsx
import React, { useState } from 'react';
import {
  Modal, Box, TextField, Button, IconButton, Grid, Typography
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { useCreatePaciente } from '../../../hooks/usePacientes';

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

  // Estados para o Paciente
  const [cpf, setCpf] = useState('');
  const [nome, setNome] = useState('');
  const [contato, setContato] = useState('');
  const [medicoResponsavel, setMedicoResponsavel] = useState('');
  const [email, setEmail] = useState('');
  // Estados para o Endereço do Paciente
  const [nomeEndereco, setNomeEndereco] = useState(''); // Apelido do endereço
  const [logradouro, setLogradouro] = useState('');
  const [numero, setNumero] = useState('');
  const [complemento, setComplemento] = useState('');
  const [cidade, setCidade] = useState('');
  const [cep, setCep] = useState('');

  const createPaciente = useCreatePaciente();

  const handleAddPaciente = async () => {
    if(!nome || !cpf || !contato || !medicoResponsavel) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    const pacienteData = {
      cpf,
      nome,
      endereco: {
        nome: nomeEndereco, // Apelido do endereço
        logradouro,
        numero,
        complemento,
        cidade,
        cep
      },
      email,
      contato,
      medicoResponsavel,
      // Ficha médica tratada como null por enquanto
    };

    try {
      await createPaciente.mutateAsync(pacienteData);

      setCpf('');
      setNome('');
      setContato('');
      setMedicoResponsavel('');
      setEmail('');

      setLogradouro('');
      setNumero('');
      setComplemento('');
      setCidade('');
      setCep('');

      handleClose();
    } catch (error) {
      console.error('Erro ao adicionar paciente:', error);
      alert('Ocorreu um erro ao adicionar o paciente. Por favor, tente novamente.');
    }

  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Adicionar paciente</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item xs={6} size={size}><TextField required fullWidth label="Nome do paciente" value={nome} onChange={(e) => setNome(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField required fullWidth label="CPF" value={cpf} onChange={(e) => setCpf(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Email" value={email} onChange={(e) => setEmail(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField required fullWidth label="Celular" value={contato} onChange={(e) => setContato(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField required fullWidth label="Médico Responsável" value={medicoResponsavel} onChange={(e) => setMedicoResponsavel(e.target.value)} /></Grid>
          
          <Grid item xs={6} size={size}><TextField fullWidth label="Apelido do Endereço" value={nomeEndereco} onChange={(e) => setNomeEndereco(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Logradouro" value={logradouro} onChange={(e) => setLogradouro(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Número" value={numero} onChange={(e) => setNumero(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Complemento" value={complemento} onChange={(e) => setComplemento(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Cidade" value={cidade} onChange={(e) => setCidade(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Cep" value={cep} onChange={(e) => setCep(e.target.value)} /></Grid>
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
