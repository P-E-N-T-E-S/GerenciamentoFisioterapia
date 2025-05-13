import { Button, Modal } from "@mui/material";
import React from "react";

import { Box, Grid, IconButton, TextField, Typography } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

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
    const size = { xs: 2, sm: 4, md: 6 };

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
                <Button variant="contained" color="primary">Adicionar</Button>
            </Box>
        )

    }

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>

                {modalHeader()}

                <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Cirurgião" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Cirurgia" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Hospital" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Data da Cirurgia" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Alergias" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Cirurgias anteriores" /></Grid>
                    <Grid item xs={6} size={size}><TextField fullWidth label="Anotações" /></Grid>
                </Grid>

                {modalFooter()}

            </Box>
        </Modal>
    )
}