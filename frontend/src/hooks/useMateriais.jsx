import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

const API = 'http://localhost:8081/materiais'; // ajuste conforme seu endpoint real
const getToken = () => localStorage.getItem('token');

// Listar materiais
export function useMateriais() {
  return useQuery({
    queryKey: ['materiais'],
    queryFn: async () => {
      const res = await axios.get(API, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      return res.data;
    },
  });
}

// Criar material
export function useCreateMaterial() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (material) => {
      const res = await axios.post(API, material, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      });
      return res.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['materiais']);
    },
  });
}

// Deletar material
export function useDeleteMaterial() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${API}/${id}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['materiais']);
    },
  });
}
