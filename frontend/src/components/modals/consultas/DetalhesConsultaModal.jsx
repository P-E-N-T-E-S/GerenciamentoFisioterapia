import React from "react";

import { Modal, Box, Grid, Typography, IconButton, Button } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useUpdateConsulta } from '../../../hooks/useConsultas';
import { TextField, Checkbox, FormControlLabel } from "@mui/material";

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
            <Grid item>
                <Button variant='outlined' startIcon={<DeleteIcon />} color="error">Deletar</Button>
            </Grid>
        </Grid>
    );

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>
                {modalHeader()}
                <Grid container spacing={{ xs: 2, md: 3 }} mt={1} columns={{ xs: 4, sm: 8, md: 12 }}>
                    <Grid item size={size}>
                        <TextField
                            label="Paciente"
                            value={form.paciente?.nome || form.pacienteId || '-'}
                            disabled
                            fullWidth
                            size="small"
                        />
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Tipo de consulta"
                                value={form.tipo || ''}
                                onChange={e => handleChange('tipo', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Tipo de consulta:</strong><br />&nbsp;{form.tipo || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Data"
                                type="date"
                                value={form.dataHora ? String(form.dataHora).slice(0,10) : ''}
                                onChange={e => handleChange('dataHora', e.target.value + (form.dataHora ? form.dataHora.slice(10) : 'T00:00:00'))}
                                fullWidth
                                size="small"
                                InputLabelProps={{ shrink: true }}
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Data:</strong><br />&nbsp;{form.dataHora ? String(form.dataHora).slice(0,10) : '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Horário"
                                type="time"
                                value={form.dataHora ? String(form.dataHora).slice(11,16) : ''}
                                onChange={e => handleChange('dataHora', (form.dataHora ? form.dataHora.slice(0,10) : '') + 'T' + e.target.value + ':00')}
                                fullWidth
                                size="small"
                                InputLabelProps={{ shrink: true }}
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Horário:</strong> <br />&nbsp;{form.dataHora ? String(form.dataHora).slice(11,16) : '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Método de pagamento"
                                value={form.metodoPagamento || ''}
                                onChange={e => handleChange('metodoPagamento', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Método de pagamento:</strong> <br />&nbsp;{form.metodoPagamento || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Valor"
                                type="number"
                                value={form.valor || ''}
                                onChange={e => handleChange('valor', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Valor:</strong><br />&nbsp;{form.valor || '-'}</Typography>
                        )}
                    </Grid>
                    {/* Endereço */}
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Apelido do Endereço"
                                value={form.endereco?.nome || ''}
                                onChange={e => handleEnderecoChange('nome', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Apelido do Endereço:</strong><br />&nbsp;{form.endereco?.nome || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Logradouro"
                                value={form.endereco?.logradouro || ''}
                                onChange={e => handleEnderecoChange('logradouro', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Logradouro:</strong><br />&nbsp;{form.endereco?.logradouro || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Número"
                                value={form.endereco?.numero || ''}
                                onChange={e => handleEnderecoChange('numero', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Número:</strong><br />&nbsp;{form.endereco?.numero || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Complemento"
                                value={form.endereco?.complemento || ''}
                                onChange={e => handleEnderecoChange('complemento', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Complemento:</strong><br />&nbsp;{form.endereco?.complemento || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Cidade"
                                value={form.endereco?.cidade || ''}
                                onChange={e => handleEnderecoChange('cidade', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Cidade:</strong><br />&nbsp;{form.endereco?.cidade || '-'}</Typography>
                        )}
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="CEP"
                                value={form.endereco?.cep || ''}
                                onChange={e => handleEnderecoChange('cep', e.target.value)}
                                fullWidth
                                size="small"
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>CEP:</strong><br />&nbsp;{form.endereco?.cep || '-'}</Typography>
                        )}
                    </Grid>
                    {/* Pagamento e status */}
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
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={!!form.consultaRealizada}
                                    disabled={!editMode}
                                    onChange={e => handleChange('consultaRealizada', e.target.checked)}
                                />
                            }
                            label={<strong>Consulta realizada</strong>}
                        />
                    </Grid>
                    <Grid item size={size}>
                        {editMode ? (
                            <TextField
                                label="Observações"
                                value={form.observacoes || ''}
                                onChange={e => handleChange('observacoes', e.target.value)}
                                fullWidth
                                size="small"
                                multiline
                                minRows={2}
                            />
                        ) : (
                            <Typography noWrap variant='inherit'> <strong>Observações:</strong> <br />&nbsp;{form.observacoes || '-'}</Typography>
                        )}
                    </Grid>
                </Grid>
                {modalFooter()}
            </Box>
        </Modal>
    )
}