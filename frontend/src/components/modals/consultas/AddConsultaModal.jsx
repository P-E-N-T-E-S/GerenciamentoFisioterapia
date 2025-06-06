import React, { useState } from "react";
import {
  Box,
  Modal,
  Grid,
  Typography,
  IconButton,
  Button,
  TextField,
  MenuItem,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useCreateConsulta } from "../../../hooks/useConsultas";
import { usePacientes } from "../../../hooks/usePacientes";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  bgcolor: "background.paper",
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
  minWidth: 500,
};

export const AddConsultaModal = ({ open, handleClose }) => {
  const size = { xs: 2, sm: 4, md: 6 };
  
  // Pegando todos os pacientes do back para buscar por id
  const { data: pacientes, isLoading: loadingPacientes } = usePacientes();

  const [pacienteId, setPacienteId] = useState("");
  const [descricao, setDescricao] = useState("");
  const [data, setData] = useState("");
  const [hora, setHora] = useState("");

  const [nomeEndereco, setNomeEndereco] = useState("");
  const [logradouro, setLogradouro] = useState("");
  const [numero, setNumero] = useState("");
  const [complemento, setComplemento] = useState("");
  const [cidade, setCidade] = useState("");
  const [cep, setCep] = useState("");

  const createConsulta = useCreateConsulta();

  const handleSubmit = async () => {
    if (!pacienteId || !descricao || !data || !hora || !cep || !logradouro) {
      alert("Preencha todos os campos obrigatórios.");
      return;
    }

    const consultaData = {
      dataHora: `${data}T${hora}`,
      pacienteId: parseInt(pacienteId),
      descricao,
      endereco: {
        nome: nomeEndereco,
        logradouro,
        numero,
        complemento,
        cidade,
        cep: parseInt(cep),
      },
    };

    try {
      await createConsulta.mutateAsync(consultaData);
      // limpa os campos
      setPacienteId("");
      setDescricao("");
      setData("");
      setHora("");
      setNomeEndereco("");
      setLogradouro("");
      setNumero("");
      setComplemento("");
      setCidade("");
      setCep("");
      handleClose();
    } catch (error) {
      console.error("Erro ao adicionar consulta:", error);
      alert("Ocorreu um erro. Verifique os dados e tente novamente.");
    }
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
          <Typography variant="h6" fontWeight="bold">Adicionar consulta</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={3} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
          <Grid item xs={6} size={size}>
            <TextField
              select
              fullWidth
              label="Paciente"
              value={pacienteId}
              onChange={(e) => setPacienteId(e.target.value)}
              disabled={loadingPacientes}
            >
              {pacientes?.map((paciente) => (
                <MenuItem key={paciente.pacienteId?.id} value={paciente.pacienteId?.id}>
                  {paciente.nome}
                </MenuItem>
              ))}
            </TextField>
          </Grid>
          <Grid item xs={6} size={size}><TextField fullWidth type="date" label="Data" InputLabelProps={{ shrink: true }} value={data} onChange={(e) => setData(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth type="time" label="Hora" InputLabelProps={{ shrink: true }} value={hora} onChange={(e) => setHora(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth multiline label="Descrição" value={descricao} onChange={(e) => setDescricao(e.target.value)} /></Grid>

          <Grid item xs={6} size={size}><TextField fullWidth label="Apelido do Endereço" value={nomeEndereco} onChange={(e) => setNomeEndereco(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Logradouro" value={logradouro} onChange={(e) => setLogradouro(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Número" value={numero} onChange={(e) => setNumero(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Complemento" value={complemento} onChange={(e) => setComplemento(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="Cidade" value={cidade} onChange={(e) => setCidade(e.target.value)} /></Grid>
          <Grid item xs={6} size={size}><TextField fullWidth label="CEP" value={cep} onChange={(e) => setCep(e.target.value)} /></Grid>
        </Grid> 

        <Box mt={4} textAlign="right">
          <Button variant="contained" color="primary" onClick={handleSubmit} disabled={createConsulta.isLoading}>
            Confirmar
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};
