import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from './AuthContext.jsx';

const PrivateRoute = ({ allowedCargos }) => {
    const { auth } = useAuth();

    if (!auth.token) {
        return <Navigate to="/login" />;
    }

    if (allowedCargos && !allowedCargos.includes(auth.cargo)) {
        return <Navigate to="/unauthorized" />;
    }

    return <Outlet />;
};

export default PrivateRoute;