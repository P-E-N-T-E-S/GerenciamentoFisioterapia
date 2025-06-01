// src/components/modals/material/EditMaterialModal.jsx
import React, { useState, useEffect } from 'react';
import {
  Modal, Box, Typography, Grid, TextField, Button, IconButton
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { useUpdateMaterial } from '../../../hooks/useMateriais';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
  minWidth: 400,
};

export const EditMaterialModal = ({ open, handleClose, material }) => {
  const [nome, setNome] = useState('');
  const [quantidade, setQuantidade] = useState('');
  const updateMaterial = useUpdateMaterial();

  useEffect(() => {
    if (material) {
      setNome(material.nome);
      setQuantidade(material.quantidade);
    }
  }, [material]);

  const handleSubmit = async () => {
    if (!nome || !quantidade) return;
    await updateMaterial.mutateAsync({
      id: material.id,
      materialData: { nome, quantidade: Number(quantidade) }
    });
    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Grid container justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight="bold">Editar material</Typography>
          <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>

        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Nome do material"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Quantidade"
              type="number"
              value={quantidade}
              onChange={(e) => setQuantidade(e.target.value)}
            />
          </Grid>
        </Grid>

        <Box mt={3} textAlign="right">
          <Button variant="contained" onClick={handleSubmit}>Salvar</Button>
        </Box>
      </Box>
    </Modal>
  );
};
