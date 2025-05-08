import React from "react";
import './AddMaterialModal.css'

import { Modal, Box, Grid, IconButton, Typography, TextField, Button} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

export const AddMaterialModal = ({ open, handleClose}) => {
    
    
    return (
        <Modal open={open} onClose={handleClose}>
            <Box className='modal-container'>
                <Grid container className='modal-header'>
                  <Typography variant="h6" fontWeight="bold">Adicionar material</Typography>
                  <IconButton onClick={handleClose}><CloseIcon /></IconButton>
                </Grid>

                <Grid container direction={"column"} spacing={4} className='modal-content'>
                    <Grid item xs={6}>
                        <TextField fullWidth label="Nome do material" />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField fullWidth label="Quantidade" />
                    </Grid>
                </Grid>

                <Grid container className='modal-footer'>
                    <Button variant="contained" color="primary">Adicionar</Button>
                </Grid>  

            </Box>
        </Modal>
    )
}