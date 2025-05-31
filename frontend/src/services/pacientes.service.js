import { api } from './api.service';

// Listar todos os pacientes
export const getAllPacients = async () => {
    const response = await api.get('/pacientes/listar');
    return response.data;
}

// Listar paciente por ID
export const getPacientById = async (id) => {
    const response = await api.get(`/pacientes/${id}`);
    return response.data;
}

// Criar um novo paciente
export const createPacient = async (pacientData) => {
    const response = await api.post('/pacientes', pacientData);
    return response.data;
}

// Atualizar um paciente
// export const updatePacient = async (id, pacientData) => {
//     const response = await api.put(`/pacientes/${id}`, pacientData);
//     return response.data;
// }

// Deletar um paciente
// export const deletePacient = async (id) => {
//     const response = await api.delete(`/pacientes/${id}`);
//     return response.data;
// }