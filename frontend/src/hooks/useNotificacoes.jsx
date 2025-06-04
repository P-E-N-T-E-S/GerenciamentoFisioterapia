import { useQuery } from '@tanstack/react-query';
import { notificacoesService } from '../services/notificacoes.service';

export function useNotificacoes() {
  return useQuery({
    queryKey: ['notificacoes'],
    queryFn: notificacoesService.getNotificacoes,
    refetchOnWindowFocus: false, // Evita refetch automático ao focar na janela
  });
}
