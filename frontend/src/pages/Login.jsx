import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [senha, setSenha] = useState('');
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username && senha) {
      navigate('/'); // ajuste aqui conforme sua rota
    } else {
      alert('Preencha todos os campos');
    }
  };

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
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <button className="btn-login" onClick={handleLogin}>Entrar</button>
        <button className="btn-cadastro" onClick={handleCadastro}>Cadastrar</button>
      </div>
    </div>
  );
};

export default Login;
