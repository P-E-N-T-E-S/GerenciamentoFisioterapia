// import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
// import * as pacienteService from '../services/pacienteService';
// import axios from 'axios';


// const API = 'http://localhost:8081/pacientes'; // ajuste conforme sua API
// const getToken = () => localStorage.getItem('token');

// // Listar pacientes
// export function usePacientes() {
//   return useQuery({
//     queryKey: ['pacientes'],
//     queryFn: async () => {
//       const res = await axios.get(API, {
//         headers: { Authorization: `Bearer ${getToken()}` },
//       });
//       return res.data;
//     },
//   });
// }

// // Criar paciente
// export function useCreatePaciente() {
//   const queryClient = useQueryClient();
//   return useMutation({
//     mutationFn: async (paciente) => {
//       const res = await axios.post(API, paciente, {
//         headers: {
//           Authorization: `Bearer ${getToken()}`,
//           'Content-Type': 'application/json',
//         },
//       });
//       return res.data;
//     },
//     onSuccess: () => {
//       queryClient.invalidateQueries(['pacientes']);
//     },
//   });
// }

// // Deletar paciente por ID
// export function useDeletePaciente() {
//   const queryClient = useQueryClient();
//   return useMutation({
//     mutationFn: async (id) => {
//       await axios.delete(`${API}/${id}`, {
//         headers: { Authorization: `Bearer ${getToken()}` },
//       });
//     },
//     onSuccess: () => {
//       queryClient.invalidateQueries(['pacientes']);
//     },
//   });
// }

import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as pacienteService from '../services/pacienteService';

// Listar todos os pacientes
export function usePacientes() {
  return useQuery({
    queryKey: ['pacientes'],
    queryFn: pacienteService.getAllPacientes
  });
}

// Buscar paciente por ID
export function usePacienteById(pacienteId) {
  return useQuery({
    queryKey: ['paciente', pacienteId],
    queryFn: () => pacienteService.getPacienteById(pacienteId),
    enabled: !!pacienteId
  });
}

// Buscar paciente por nome
export function usePacienteByName(name) {
  return useQuery({
    queryKey: ['pacientesByName', name],
    queryFn: () => pacienteService.getPacienteByName(name),
    enabled: typeof name === 'string' && name.length > 0
  });
}

// Criar um novo paciente
export function useCreatePaciente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: pacienteService.createPaciente,
    onSuccess: () => {
      queryClient.invalidateQueries(['pacientes']);
    }
  });
}

// Atualizar um paciente
export function useUpdatePaciente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, pacienteData }) => pacienteService.updatePaciente(id, pacienteData),
    onSuccess: (_, { id }) => {
      queryClient.invalidateQueries(['pacientes']);
      queryClient.invalidateQueries(['paciente', id]);
    }
  });
}

// Deletar um paciente
export function useDeletePaciente() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: pacienteService.deletePaciente,
    onSuccess: () => {
      queryClient.invalidateQueries(['pacientes']);
    }
  });
}