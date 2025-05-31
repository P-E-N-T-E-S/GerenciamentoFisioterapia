import { api } from './api';


export const register = async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
}

export const login = async (credentials) => {
    // credentials = { username: '...', password: '...' }
    const response = await api.post('/auth/login', credentials);
    const data = response.data;

    localStorage.setItem('token', data.token);
    return data;
}

// export const logout = () => {
//     localStorage.removeItem('token');
// }