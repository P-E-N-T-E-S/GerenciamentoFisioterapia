import React from "react";

import { Modal, Box, Grid, Typography, IconButton, Button } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';


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


export const DetalhesConsultaModal = ({ open, handleClose, consulta }) => {
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
            <Grid container spacing={2} justifyContent="flex-end" mt={4}>
              <Grid item>
                <Button variant='contained' startIcon={<EditIcon />}>Editar</Button>
              </Grid>
              <Grid item>
                <Button variant='outlined' startIcon={<DeleteIcon />} color="error">Deletar</Button>
              </Grid>
            </Grid>
        )
    }

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>

                {modalHeader()}

                <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>

                    {/* Add content to display consulta details here */}
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Paciente:</strong><br />&nbsp;{consulta.paciente || '-'}</Typography>
                    </Grid>
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Tipo de consulta:</strong><br />&nbsp;{consulta.tipo || '-'}</Typography>
                    </Grid>
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Data:</strong><br />&nbsp;{consulta.data || '-'}</Typography>
                    </Grid>
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Horário:</strong> <br />&nbsp;{consulta.hora || '-'} </Typography>
                    </Grid>
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Método de pagamento:</strong> <br />&nbsp;{consulta.metodoPagamento || '-'}
                        </Typography>
                    </Grid>
                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Valor:</strong><br />&nbsp;{consulta.valor || '-'}</Typography>
                    </Grid>

                    <Grid size={size} container spacing={2} flexDirection={"column"}>
                        <Typography sx={{ display: "flex", alignItems: "center", gap: "16px" }}>
                            <input type="checkbox" checked={consulta.pagamentoRealizado || false} readOnly style={{ transform: "scale(1.5)" }} />
                            <strong>Pagamento realizado:</strong> 
                        </Typography>

                        <Typography sx={{ display: "flex", alignItems: "center", gap: "16px" }}>
                            <input type="checkbox" checked={consulta.consultaRealizada || false} readOnly style={{ transform: "scale(1.5)" }} />
                            <strong>Consulta realizada:</strong>
                        </Typography>
                    </Grid>

                    <Grid item size={size}>
                        <Typography variant="body1"> <strong>Observações:</strong> <br />&nbsp;{consulta.observacoes || '-'}</Typography>
                    </Grid>
                </Grid>

                {modalFooter()}
            </Box>
        </Modal>
    )
}