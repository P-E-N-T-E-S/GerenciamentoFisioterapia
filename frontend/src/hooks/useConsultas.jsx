// import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
// import axios from 'axios';

// const API = 'http://localhost:8081/consultas';
// const getToken = () => localStorage.getItem('token');

// // Listar consultas
// export function useConsultas() {
//   return useQuery({
//     queryKey: ['consultas'],
//     queryFn: async () => {
//       const res = await axios.get(API, {
//         headers: { Authorization: `Bearer ${getToken()}` },
//       });
//       return res.data;
//     },
//   });
// }

// // Criar consulta
// export function useCreateConsulta() {
//   const queryClient = useQueryClient();
//   return useMutation({
//     mutationFn: async (consulta) => {
//       const res = await axios.post(API, consulta, {
//         headers: {
//           Authorization: `Bearer ${getToken()}`,
//           'Content-Type': 'application/json',
//         },
//       });
//       return res.data;
//     },
//     onSuccess: () => {
//       queryClient.invalidateQueries(['consultas']);
//     },
//   });
// }

// // Deletar consulta
// export function useDeleteConsulta() {
//   const queryClient = useQueryClient();
//   return useMutation({
//     mutationFn: async (id) => {
//       await axios.delete(`${API}/${id}`, {
//         headers: { Authorization: `Bearer ${getToken()}` },
//       });
//     },
//     onSuccess: () => {
//       queryClient.invalidateQueries(['consultas']);
//     },
//   });
// }

import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as consultaService from '../services/consultas.service';

// Listar todas as consultas
export function useConsultas() {
  return useQuery({
    queryKey: ['consultas'],
    queryFn: consultaService.getAllConsultas,
  });
}

// Listar consulta por ID
export function useConsultaById(id) {
  return useQuery({
    queryKey: ['consulta', id],
    queryFn: () => consultaService.getConsultaById(id),
    enabled: !!id,
  });
}

// Listar consultas por data
export function useConsultaByDate(date) {
  return useQuery({
    queryKey: ['consultasByDate', date],
    queryFn: () => consultaService.getConsultaByDate(date),
    enabled: typeof date === 'string' && date.length > 0,
  });
}

// Criar uma nova consulta
export function useCreateConsulta() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: consultaService.createConsulta,
    onSuccess: () => {
      queryClient.invalidateQueries(['consultas']);
    },
  });
}

// Atualizar uma consulta existente
export function useUpdateConsulta() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, consultaData }) => consultaService.updateConsulta(id, consultaData),
    onSuccess: (_, { id }) => {
      queryClient.invalidateQueries(['consultas']);
      queryClient.invalidateQueries(['consulta', id]);
    },
  });
}

// Deletar uma consulta
export function useDeleteConsulta() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: consultaService.deleteConsulta,
    onSuccess: () => {
      queryClient.invalidateQueries(['consultas']);
    },
  });
}