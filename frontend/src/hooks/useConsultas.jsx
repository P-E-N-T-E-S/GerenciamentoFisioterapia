import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

const API = 'http://localhost:8081/consultas';
const getToken = () => localStorage.getItem('token');

// Listar consultas
export function useConsultas() {
  return useQuery({
    queryKey: ['consultas'],
    queryFn: async () => {
      const res = await axios.get(API, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      return res.data;
    },
  });
}

// Criar consulta
export function useCreateConsulta() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (consulta) => {
      const res = await axios.post(API, consulta, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      });
      return res.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['consultas']);
    },
  });
}

// Deletar consulta
export function useDeleteConsulta() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${API}/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['consultas']);
    },
  });
}
