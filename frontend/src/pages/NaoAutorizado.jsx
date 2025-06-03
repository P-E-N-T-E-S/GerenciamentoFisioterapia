import React from 'react';
import { useNavigate } from 'react-router-dom';

const NaoAutorizado = () => {
    const navigate = useNavigate();

    return (
        <div className="not-authorized-container">
            <h1>401 - Não Autorizado</h1>
            <p>Você não tem permissão para acessar esta página.</p>
            <button onClick={() => navigate('/home')} className="btn btn-primary">
                Voltar para a Home
            </button>
        </div>
    );
};

export default NaoAutorizado;