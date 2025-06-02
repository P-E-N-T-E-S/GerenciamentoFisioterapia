import { api } from './api';

// Listar todos os pacientes
export const getAllPacientes = async () => {
    const response = await api.get('/pacientes/listar');
    return response.data;
}

// Listar paciente por ID
export const getPacienteById = async (id) => {
    const response = await api.get(`/pacientes/${id}`);
    return response.data;
}

// Listar pacientes por nome
export const getPacienteByName = async (name) => {
    const response = await api.get('/pacientes/nome', {
        params: { nome: name }
    });
    return response.data;
}

// Criar um novo paciente
export const createPaciente = async (pacientData) => {
    const response = await api.post('/pacientes', pacientData);
    return response.data;
}

// Atualizar um paciente
export const updatePaciente = async (id, pacientData) => {
    const response = await api.put(`/pacientes/${id}`, pacientData);
    return response.data;
}

// Deletar um paciente
export const deletePaciente = async (id) => {
    const response = await api.delete(`/pacientes/${id}`);
    return response.data;
}