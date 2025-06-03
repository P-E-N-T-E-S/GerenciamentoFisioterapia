import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState(() => {
        const token = localStorage.getItem('token');
        const cargo = localStorage.getItem('cargo');
        return { token, cargo };
    });

    const login = (token, cargo) => {
        localStorage.setItem('token', token);
        localStorage.setItem('cargo', cargo);
        setAuth({ token, cargo });
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('cargo');
        setAuth({ token: null, cargo: null });
    };

    return (
        <AuthContext.Provider value={{ auth, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);