import { api } from './api';

// Listar todas as consultas
export const getAllConsultas = async () => {
    const response = await api.get('/consultas/listar');
    return response.data;
}

// Listar consulta por ID
export const getConsultaById = async (id) => {
    const response = await api.get(`/consultas/${id}`);
    return response.data;
}

// Listar consultas por data
export const getConsultaByDate = async (date) => {
    const response = await api.get('/consultas/data', {
        params: { data: date }
    });
    return response.data;
}

// Criar uma nova consulta
export const createConsulta = async (consultaData) => {
    const response = await api.post('/consultas', consultaData);
    return response.data;
}

// Atualizar uma consulta existente
// export const updateConsulta = async (id, consultaData) => {
//     const response = await api.put(`/consultas/${id}`, consultaData);
//     return response.data;
// }

// Deletar uma consulta
export const deleteConsulta = async (id) => {
    const response = await api.delete(`/consultas/${id}`);
    return response.data;
}
