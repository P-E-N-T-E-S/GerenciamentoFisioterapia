import React from "react";

import { Modal, Box, Grid, Typography, IconButton, TextField, Button, Checkbox, FormControlLabel } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';
import { useUpdateConsulta } from '../../../hooks/useConsultas';

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
  maxWidth: '95vw',
  maxHeight: '90vh',
  overflowY: 'auto',
};


export const DetalhesConsultaModal = ({ open, handleClose, consulta }) => {
    const size = { xs: 2, sm: 4, md: 6 };
    const [editMode, setEditMode] = React.useState(false);
    const [form, setForm] = React.useState({ ...consulta });
    const updateConsulta = useUpdateConsulta();

    React.useEffect(() => {
        // Extrai todos os campos necessários do objeto consulta
        const pacienteId = consulta.pacienteId || consulta.paciente?.id || consulta.paciente?.pacienteId?.id;
        const endereco = consulta.endereco || consulta.paciente?.endereco || {
            nome: '', logradouro: '', numero: '', complemento: '', cidade: '', cep: ''
        };
        setForm({
            ...consulta,
            pacienteId,
            endereco,
            valor: consulta.valor || '',
            descricao: consulta.descricao || '',
            materiaisUsados: consulta.materiaisUsados || [],
            dataVencimento: consulta.dataVencimento || '',
            clientePagou: consulta.clientePagou || false,
            observacoes: consulta.observacoes || '',
            tipo: consulta.tipo || '',
            metodoPagamento: consulta.metodoPagamento || '',
            consultaRealizada: consulta.consultaRealizada || false,
        });
        setEditMode(false);
    }, [consulta]);

    const handleChange = (field, value) => {
        setForm((prev) => ({ ...prev, [field]: value }));
    };

    const handleEnderecoChange = (field, value) => {
        setForm((prev) => ({ ...prev, endereco: { ...prev.endereco, [field]: value } }));
    };

    const handleSave = () => {
        const id = form.consultaId?.id || form.id;
        const pacienteId = form.pacienteId || form.paciente?.id;
        const dataHora = form.dataHora || (form.data && form.hora ? `${form.data}T${form.hora}` : undefined);
        // Mapeia materiaisUsados para garantir o formato correto [{nome, quantidade}]
        const materiaisUsados = Array.isArray(form.materiaisUsados)
            ? form.materiaisUsados.map(m => ({ nome: m.nome, quantidade: Number(m.quantidade) }))
            : [];
        // DEBUG: log do token e do corpo enviado
        const token = localStorage.getItem('token');
        console.log('[DetalhesConsultaModal] Token JWT:', token);
        console.log('[DetalhesConsultaModal] Corpo enviado:', {
            id,
            dataHora,
            pacienteId,
            descricao: form.descricao,
            valor: Number(form.valor),
            materiaisUsados,
        });
        const consultaData = {
            id,
            dataHora,
            pacienteId,
            descricao: form.descricao,
            endereco: {
                nome: form.endereco?.nome || '',
                logradouro: form.endereco?.logradouro || '',
                numero: form.endereco?.numero || '',
                complemento: form.endereco?.complemento || '',
                cidade: form.endereco?.cidade || '',
                cep: form.endereco?.cep ? parseInt(form.endereco.cep) : '',
            },
            valor: Number(form.valor),
            materiaisUsados,
            dataVencimento: form.dataVencimento || null,
            clientePagou: !!form.clientePagou
        };
        updateConsulta.mutate(
            { id, consultaData },
            {
                onSuccess: () => {
                    setEditMode(false);
                    handleClose();
                },
                onError: (error) => {
                    alert('Erro ao salvar: ' + (error?.response?.data?.message || error.message));
                }
            }
        );
    };

    const modalHeader = () => (
        <Grid container justifyContent="space-between" alignItems="center" mb={4}>
            <Typography variant="h6" fontWeight="bold">Consulta</Typography>
            <IconButton onClick={handleClose}><CloseIcon /></IconButton>
        </Grid>
    );

    const modalFooter = () => (
        <Grid container spacing={2} justifyContent="flex-end" mt={4}>
            {editMode ? (
                <>
                    <Grid item>
                        <Button variant='contained' color='primary' onClick={handleSave}>Salvar</Button>
                    </Grid>
                    <Grid item>
                        <Button variant='outlined' color='secondary' onClick={() => setEditMode(false)}>Cancelar</Button>
                    </Grid>
                </>
            ) : (
                <Grid item>
                    <Button variant='contained' startIcon={<EditIcon />} onClick={() => setEditMode(true)}>Editar</Button>
                </Grid>
            )}
            
        </Grid>
    );

    return (
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Grid container justifyContent="space-between" alignItems="center" mb={4}>
            <Typography variant="h6" fontWeight="bold">Detalhes da Consulta</Typography>
            <IconButton onClick={handleClose}><CloseIcon /></IconButton>
          </Grid>

          <Grid container spacing={3} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Data"
                type="date"
                value={form.dataHora ? String(form.dataHora).slice(0, 10) : ''}
                onChange={e => handleChange('dataHora', e.target.value + (form.dataHora ? form.dataHora.slice(10) : 'T00:00:00'))}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Horário"
                type="time"
                value={form.dataHora ? String(form.dataHora).slice(11, 16) : ''}
                onChange={e => handleChange('dataHora', (form.dataHora ? form.dataHora.slice(0, 10) : '') + 'T' + e.target.value + ':00')}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Descrição"
                value={form.descricao || ''}
                onChange={e => handleChange('descricao', e.target.value)}
                fullWidth
                multiline
                minRows={2}
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Valor"
                type="number"
                value={form.valor || ''}
                onChange={e => handleChange('valor', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Data de Vencimento"
                type="date"
                value={form.dataVencimento ? String(form.dataVencimento).slice(0, 10) : ''}
                onChange={e => handleChange('dataVencimento', e.target.value)}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            {/* Local */}
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Nome do Local"
                value={form.endereco?.nome || ''}
                onChange={e => handleEnderecoChange('nome', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Logradouro"
                value={form.endereco?.logradouro || ''}
                onChange={e => handleEnderecoChange('logradouro', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Número"
                value={form.endereco?.numero || ''}
                onChange={e => handleEnderecoChange('numero', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="Complemento"
                value={form.endereco?.complemento || ''}
                onChange={e => handleEnderecoChange('complemento', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item xs={6} size={size}>
              <TextField
                disabled={!editMode}
                label="Cidade"
                value={form.endereco?.cidade || ''}
                onChange={e => handleEnderecoChange('cidade', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <TextField
                disabled={!editMode}
                label="CEP"
                value={form.endereco?.cep || ''}
                onChange={e => handleEnderecoChange('cep', e.target.value)}
                fullWidth
              />
            </Grid>
            <Grid item size={size}>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={!!form.clientePagou}
                    disabled={!editMode}
                    onChange={e => handleChange('clientePagou', e.target.checked)}
                  />
                }
                label={<strong>Pagamento realizado</strong>}
              />
            </Grid>
          </Grid>

          {/* Footer */}
          <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ mt: 4 }}>
            {!editMode ? (
              <Button variant='contained' startIcon={<EditIcon />} onClick={() => setEditMode(true)}>Editar</Button>
            ) : (
              <Button variant='contained' color='success' startIcon={<SaveIcon />} onClick={handleSave}>
                Salvar
              </Button>
            )}
          </Grid>
        </Box>
      </Modal>
    );
}