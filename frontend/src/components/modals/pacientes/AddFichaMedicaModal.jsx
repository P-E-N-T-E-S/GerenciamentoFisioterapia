import { Button, Modal } from "@mui/material";
import React, { useState } from "react";

import { Box, Grid, IconButton, TextField, Typography } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

import { useAddFichaMedica } from "../../../hooks/usePacientes";

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


export const AddFichaMedicaModal = ({ open, handleClose, paciente }) => {
    console.log(paciente);
    const size = { xs: 2, sm: 4, md: 6 };

    // Atributos de ficha médica
    const [historicoMedico, setHistoricoMedico] = useState('');
    const [alergias, setAlergias] = useState('');
    const [observacoes, setObservacoes] = useState('');

    // Hook de adicionar ficha médica
    const addFichaMedica = useAddFichaMedica();
    const handleAddFicha = async () => {
        if (!historicoMedico || !alergias) {
            alert("Por favor, preencha todos os campos obrigatórios.");
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
                    onSuccess: () => {
                        setHistoricoMedico('');
                        setAlergias('');
                        setObservacoes('');
                        handleClose();
                    }
                }
            );
        } catch (error) {
            console.error("Erro ao adicionar ficha médica:", error);
            alert("Erro ao adicionar ficha médica. Por favor, tente novamente.");
        }
    }

    const modalHeader = () => {
        return (
            <Grid container justifyContent="space-between" alignItems="center" mb={4}>
                <Typography variant="h6" fontWeight="bold">Adicionar ficha médica</Typography>
                <IconButton onClick={handleClose}><CloseIcon /></IconButton>
            </Grid>
        )

    }

    const modalFooter = () => {
        return (
            <Box mt={4} textAlign="right">
                <Button variant="contained" color="primary" onClick={handleAddFicha} disabled={addFichaMedica.isLoading}>
                    Adicionar
                </Button>
            </Box>
        )

    }

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>

                {modalHeader()}

                <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 2, sm: 4, md: 6 }}>
                    <Grid item xs={6} size={size}>
                        <TextField 
                            fullWidth 
                            label="Histórico Médico" 
                            value={historicoMedico} 
                            onChange={(e) => setHistoricoMedico(e.target.value)}
                            multiline
                            required
                        />

                    </Grid>
                    <Grid item xs={6} size={size}>
                        <TextField 
                            fullWidth 
                            label="Alergias" 
                            value={alergias} 
                            onChange={(e) => setAlergias(e.target.value)} 
                            multiline
                            required
                        />
                    </Grid>
                    <Grid item xs={6} size={size}> 
                        <TextField 
                            fullWidth 
                            label="Observações" 
                            value={observacoes} 
                            onChange={(e) => setObservacoes(e.target.value)} 
                            multiline
                        />
                    </Grid>
                </Grid>

                {modalFooter()}

            </Box>
        </Modal>
    )
}