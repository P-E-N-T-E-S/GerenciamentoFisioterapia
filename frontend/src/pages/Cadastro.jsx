import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css'; // Use o mesmo CSS da tela de login, se quiser o mesmo estilo

const Cadastro = () => {
  const [formData, setFormData] = useState({
    login: '',
    nome: '',
    senha: '',
    email: '',
    cargo: ''
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ 
      ...formData, 
      [e.target.name]: e.target.value 
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Exemplo: validação simples
    if (Object.values(formData).some(field => !field)) {
      alert('Preencha todos os campos');
      return;
    }

    // Aqui você pode enviar os dados para o back-end
    console.log('Dados enviados:', formData);
    navigate('/'); // Redireciona para o login ou página inicial
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Cadastro</h2>
        <p className="login-subtitle">Preencha os dados para se cadastrar</p>

        <input type="text" name="login" placeholder="Login" value={formData.login} onChange={handleChange} />
        <input type="text" name="nome" placeholder="Nome" value={formData.nome} onChange={handleChange} />
        <input type="password" name="senha" placeholder="Senha" value={formData.senha} onChange={handleChange} />
        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} />
        <input type="text" name="cargo" placeholder="Cargo" value={formData.cargo} onChange={handleChange} />

        <button className="btn-login" onClick={handleSubmit}>Cadastrar</button>
      </div>
    </div>
  );
};

export default Cadastro;
