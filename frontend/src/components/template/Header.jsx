import React, { useState } from "react";
import './Header.css';
import NotificationsNoneIcon from '@mui/icons-material/NotificationsNone';
import { Badge, IconButton, Popover, Box, Typography, CircularProgress, List, ListItem, ListItemText } from '@mui/material';
import { useNotificacoes } from '../../hooks/useNotificacoes';

export const Header = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const { data: notificacoes, isLoading } = useNotificacoes();

    const open = Boolean(anchorEl);
    const handleOpen = (event) => setAnchorEl(event.currentTarget);
    const handleClose = () => setAnchorEl(null);

    // Mostra badge se houver notificações não lidas (aqui, só conta todas)
    const badgeCount = notificacoes && notificacoes.length ? notificacoes.length : 0;

    return (
        <header className="header">
            <span>Seja bem-vindo!</span>
            <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
                <IconButton color="inherit" onClick={handleOpen} aria-label="notificações">
                    <Badge badgeContent={badgeCount} color="error" invisible={badgeCount === 0}>
                        <NotificationsNoneIcon fontSize="medium" />
                    </Badge>
                </IconButton>
                <span className="profile">👤</span>
                <Popover
                    open={open}
                    anchorEl={anchorEl}
                    onClose={handleClose}
                    anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                    transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                    PaperProps={{ sx: { minWidth: 340, maxWidth: 400, maxHeight: 400, p: 0 } }}
                >
                    <Box sx={{ p: 2, borderBottom: '1px solid #eee', fontWeight: 600 }}>
                        Notificações
                    </Box>
                    <Box sx={{ p: 2, minHeight: 80 }}>
                        {isLoading ? (
                            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: 80 }}>
                                <CircularProgress size={28} />
                            </Box>
                        ) : notificacoes && notificacoes.length > 0 ? (
                            <List dense>
                                {notificacoes.map((n, idx) => (
                                    <ListItem key={idx} alignItems="flex-start" sx={{ borderBottom: '1px solid #f0f0f0' }}>
                                        <ListItemText
                                            primary={n.mensagem}
                                            secondary={
                                                <>
                                                    <Typography component="span" variant="caption" color="text.secondary">
                                                        {n.criacao ? new Date(n.criacao).toLocaleString('pt-BR', { dateStyle: 'short', timeStyle: 'short' }) : ''}
                                                    </Typography>
                                                </>
                                            }
                                        />
                                    </ListItem>
                                ))}
                            </List>
                        ) : (
                            <Typography color="text.secondary" align="center" sx={{ mt: 2 }}>
                                Não há notificações.
                            </Typography>
                        )}
                    </Box>
                </Popover>
            </div>
        </header>
    )
}