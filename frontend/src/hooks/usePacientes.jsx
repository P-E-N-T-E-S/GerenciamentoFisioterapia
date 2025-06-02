import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import * as pacienteService from '../services/pacientes.service';

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

// Adicionar Ficha Médica
export function useAddFichaMedica() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (fichaData) => pacienteService.addFichaMedica(fichaData),
    onSuccess: (_, fichaData) => {
      queryClient.invalidateQueries(['paciente', fichaData.pacienteId]);
      queryClient.invalidateQueries(['pacientes']);
    }
  })
}