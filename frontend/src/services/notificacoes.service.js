import { api } from './api';

export const notificacoesService = {
  async getNotificacoes() {
    const { data } = await api.get('/notificacoes');
    return data;
  },
};
