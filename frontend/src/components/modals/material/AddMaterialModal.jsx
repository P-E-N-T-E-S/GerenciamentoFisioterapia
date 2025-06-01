import React, { useState } from "react";
import './AddMaterialModal.css'
import { Modal, Box, Grid, IconButton, Typography, TextField, Button } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { useCreateMaterial } from "../../../hooks/useMateriais";

export const AddMaterialModal = ({ open, handleClose }) => {
    const [nome, setNome] = useState('');
    const [quantidade, setQuantidade] = useState('');
    const createMaterial = useCreateMaterial();

    const handleAdd = async () => {
        if (!nome || !quantidade) return;
        await createMaterial.mutateAsync({ nome, quantidade: Number(quantidade) });
        setNome('');
        setQuantidade('');
        handleClose();
    };

    return (
        <Modal open={open} onClose={handleClose}>
            <Box className='modal-container'>
                <Grid container className='modal-header'>
                    <Typography variant="h6" fontWeight="bold">Adicionar material</Typography>
                    <IconButton onClick={handleClose}><CloseIcon /></IconButton>
                </Grid>

                <Grid container direction={"column"} spacing={4} className='modal-content'>
                    <Grid item xs={6}>
                        <TextField fullWidth label="Nome do material" value={nome} onChange={e => setNome(e.target.value)} />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField fullWidth label="Quantidade" type="number" value={quantidade} onChange={e => setQuantidade(e.target.value)} />
                    </Grid>
                </Grid>

                <Grid container className='modal-footer'>
                    <Button variant="contained" color="primary" onClick={handleAdd}>Adicionar</Button>
                </Grid>
            </Box>
        </Modal>
    )
}