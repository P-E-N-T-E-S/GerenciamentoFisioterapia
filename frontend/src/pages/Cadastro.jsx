import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css';
import axios from 'axios';

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

  async function handleSubmit(e) {
    e.preventDefault();

    if (Object.values(formData).some(field => !field)) {
      alert('Preencha todos os campos');
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/auth/cadastro", formData);

      console.log(response.data);
      alert("Cadastro realizado com sucesso!");
      navigate('/'); 

    } catch (error) {
      console.error("Erro ao cadastrar:", error);
      alert("Erro ao realizar cadastro. Tente novamente.");
    }
  }

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