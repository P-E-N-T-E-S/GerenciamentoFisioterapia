import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

const API = 'http://localhost:8081/pacientes'; // ajuste conforme sua API
const getToken = () => localStorage.getItem('token');

// Listar pacientes
export function usePacientes() {
  return useQuery({
    queryKey: ['pacientes'],
    queryFn: async () => {
      const res = await axios.get(API, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      return res.data;
    },
  });
}

// Criar paciente
export function useCreatePaciente() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (paciente) => {
      const res = await axios.post(API, paciente, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      });
      return res.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['pacientes']);
    },
  });
}

// Deletar paciente por ID
export function useDeletePaciente() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${API}/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['pacientes']);
    },
  });
}
