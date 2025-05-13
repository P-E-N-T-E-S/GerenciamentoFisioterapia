import React from "react";

import { Box, Modal, Grid, Typography, IconButton, Button, TextField, MenuItem } from "@mui/material";
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
  

export const AddConsultaModal = ({ open, handleClose, pacientesExistentes }) => {
    const size = { xs: 2, sm: 4, md: 6 };

    const modalHeader = () => {
        return (
            <Grid container justifyContent="space-between" alignItems="center" mb={4}>
                <Typography variant="h6" fontWeight="bold">Adicionar consulta</Typography>
                <IconButton onClick={handleClose}><CloseIcon /></IconButton>
            </Grid>
        )
    }

    const modalFooter = () => {
        return (
            <Box mt={4} textAlign="right">
                <Button variant="contained" color="primary">Confirmar</Button>
            </Box>
        )
    }

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>

                {modalHeader()}

                {/* modal-content */}
                <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
                    <Grid size={size}>
                        <TextField select fullWidth label="Paciente">
                            {pacientesExistentes.map( (paciente, index) => (
                                    <MenuItem key={index} value={paciente}>{paciente}</MenuItem>
                                ))}
                        </TextField>  
                    </Grid>

                    <Grid size={size}>
                        <TextField select fullWidth label="Tipo de consulta">
                            <MenuItem value="consulta">Consulta</MenuItem>
                            <MenuItem value="exame">Exame</MenuItem>
                            <MenuItem value="retorno">Retorno</MenuItem>
                        </TextField>
                    </Grid>
                    <Grid size={size}><TextField fullWidth label="Data" /> </Grid>
                    <Grid size={size}><TextField fullWidth label="Horário" /> </Grid>
                    <Grid size={size}>
                        <TextField select fullWidth label="Método de pagamento">
                            <MenuItem value="cartao">Cartão</MenuItem>
                            <MenuItem value="dinheiro">Dinheiro</MenuItem>
                            <MenuItem value="pix">Pix</MenuItem>
                        </TextField>
                    </Grid>
                    <Grid size={size}><TextField fullWidth label="Valor" /> </Grid>

                    <Grid size={size} container spacing={3} flexDirection={"column"}>
                        <Box>
                            <label style={{ display: "flex", alignItems: "center", gap: "16px" }}>
                                <input type="checkbox" style={{ transform: "scale(1.5)" }} />
                                Pagamento Realizado
                            </label>
                        </Box>
                        <Box>
                            <label style={{ display: "flex", alignItems: "center", gap: "16px" }}>
                                <input type="checkbox" style={{ transform: "scale(1.5)" }} />
                                Consulta Realizada
                            </label>
                        </Box>
                    </Grid>

                    <Grid size={size}><TextField fullWidth label="Observações" /> </Grid>
                </Grid>
                        

                {modalFooter()}

            </Box>
        </Modal>
    )
}