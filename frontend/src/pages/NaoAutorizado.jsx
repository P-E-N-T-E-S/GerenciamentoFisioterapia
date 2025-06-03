import React from 'react';
import { useNavigate } from 'react-router-dom';

const NaoAutorizado = () => {
    const navigate = useNavigate();

    return (
        <div className="not-authorized-container">
            <h1>401 - Não Autorizado</h1>
            <p>Você não tem permissão para acessar esta página.</p>
            <button onClick={() => navigate('/')} className="btn btn-primary">
                Voltar para Login
            </button>
        </div>
    );
};

export default NaoAutorizado;