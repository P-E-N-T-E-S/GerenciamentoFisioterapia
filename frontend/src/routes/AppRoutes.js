import { Routes, Route } from 'react-router-dom';
import Pacientes from '../pages/Pacientes';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Material from '../pages/Material';
import Consultas from '../pages/Consultas';
import Calendario from '../pages/Calendario';


const AppRoutes = () => (
    <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />

        <Route element={<PrivateRoute allowedCargos={["ROLE_ADMINISTRADOR"]} />}>
            <Route path="/home" element={<Home />} />
            <Route path="/material" element={<Material />} />
            <Route path="/pacientes" element={<Pacientes />} />
            <Route path="/consultas" element={<Consultas />} />
            <Route path="/calendario" element={<Calendario />} />
        </Route>

        <Route element={<PrivateRoute allowedCargos={["ROLE_ASSISTENTE"]} />}>
            <Route path="/pacientes" element={<Pacientes />} />
        </Route>
    </Routes>
);

export default AppRoutes;