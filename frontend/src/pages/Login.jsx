import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css';
import axios from 'axios';
import { useAuth } from '../services/AuthContext';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

async function handleLogin(e) {
    e.preventDefault();
    try {
        const response = await axios.post("http://localhost:8080/auth/login", {
            username,
            password
        });

        const { token, cargo } = response.data;
        login(token, cargo); 

        alert("Login bem-sucedido!");

        if (cargo === "ROLE_ADMINISTRADOR") {
            navigate("/home");
        } else if (cargo === "ROLE_ASSISTENTE") {
            navigate("/pacientes");
        } else {
            navigate("/"); // rota padrão para outros cargos
        }

    } catch (error) {
        console.error("Erro ao fazer login:", error);
        alert("Usuário ou senha incorretos!");
    }
}

  const handleCadastro = () => {
    navigate('/cadastro'); // ajuste para a rota de cadastro
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Bem-vindo </h2>
        <p className="login-subtitle">Faça login para acessar o sistema</p>
        <input
          type="text"
          placeholder="Usuário"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="btn-login" onClick={handleLogin}>Entrar</button>
        <button className="btn-cadastro" onClick={handleCadastro}>Cadastrar</button>
      </div>
    </div>
  );
};

export default Login;