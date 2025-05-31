import { api } from "./api";

// Listar todos os materiais
export const getAllMateriais = async () => {
    const response = await api.get('/material/listar');
    return response.data;
}

// “Criar” / Adicionar quantidade de um material
export const createMaterial = async (materialData) => {
    const response = await api.put('/material/adicionar', materialData);
    return response.data;
}

// Remover uma quantidade de um material
export const removeMaterial = async (materialData) => {
    const response = await api.put('/material/remover', materialData);
    return response.data;
}

// Atualizar um material existente
export const updateMaterial = async (id, materialData) => {
    const response = await api.put(`/material/atualizar/${id}`, materialData);
    return response.data;
}

// Deletar um material
export const deleteMaterial = async (id) => {
    const response = await api.delete(`/material/${id}`)
    return response.data;
}