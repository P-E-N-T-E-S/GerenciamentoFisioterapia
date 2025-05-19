package com.implantodontia.infraestrutura.persistencia;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class JpaMapeador extends ModelMapper {

    JpaMapeador() {
        var configuracao = getConfiguration();
        configuracao.setFieldMatchingEnabled(true);
        configuracao.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        addConverter(new AbstractConverter<Usuario, UsuarioJPA>() {
            @Override
            protected UsuarioJPA convert(Usuario source) {
                UsuarioJPA usuarioJPA = new UsuarioJPA();
                usuarioJPA.setLogin(source.getLogin());
                usuarioJPA.setSenha((source.getSenha()));
                usuarioJPA.setCargo(source.getCargo().getCodigo());
                usuarioJPA.setEmail(source.getEmail());
                return usuarioJPA;
            }
        });

        addConverter(new AbstractConverter<UsuarioJPA, Usuario>() {

            @Override
            protected Usuario convert(UsuarioJPA source) {
<<<<<<< Updated upstream
                return new Usuario(source.getLogin(), source.getEmail(), source.getSenha(), Cargo.fromIdentificador(source.getCargo()));
=======
                return new Usuario(
                        source.getLogin(),
                        source.getEmail(),
                        source.getSenha(),
                        Cargo.fromIdentificador(source.getCargo())
                );
            }
        });

        addConverter(new AbstractConverter<Paciente, PacienteJPA>() {
            @Override
            protected PacienteJPA convert(Paciente source) {
                PacienteJPA pacienteJPA = new PacienteJPA();
                pacienteJPA.setId(source.getPacienteId().getId());
                pacienteJPA.setCpf(source.getCpf().getCodigo());
                pacienteJPA.setNome(source.getNome());
                pacienteJPA.setContato(source.getContato());
                pacienteJPA.setMedicoResponsavel(source.getMedicoResponsavel());

                // Endereço
                pacienteJPA.setLogradouro(source.getEndereco().getLogradouro());
                pacienteJPA.setNumero(source.getEndereco().getNumero());
                pacienteJPA.setComplemento(source.getEndereco().getComplemento());
                pacienteJPA.setCidade(source.getEndereco().getCidade());
                pacienteJPA.setCep(source.getEndereco().getCep());

                if (source.getFichaMedica() != null) {
                    FichaMedicaJPA fichaJPA = new FichaMedicaJPA();
                    fichaJPA.setAlergias(source.getFichaMedica().getAlergias());
                    fichaJPA.setHistoricoMedico(source.getFichaMedica().getHistoricoMedico());
                    fichaJPA.setObservacoes(source.getFichaMedica().getObservacoes());
                    fichaJPA.setUltimaAtualizacao(source.getFichaMedica().getUltimaAtualizacao());
                    fichaJPA.setPaciente(pacienteJPA); // Relacionamento reverso
                    pacienteJPA.setFichaMedica(fichaJPA);
                }

                return pacienteJPA;
            }
        });

        addConverter(new AbstractConverter<PacienteJPA, Paciente>() {
            @Override
            protected Paciente convert(PacienteJPA source) {
                Endereco endereco = new Endereco(
                        source.getLogradouro(),
                        source.getNumero(),
                        source.getComplemento(),
                        source.getCidade(),
                        source.getCep()
                );

                Paciente paciente = new Paciente(
                        new PacienteId(source.getId()),
                        new Cpf(source.getCpf()),
                        endereco,
                        source.getNome(),
                        source.getContato(),
                        source.getMedicoResponsavel()
                );

                if (source.getFichaMedica() != null) {
                    FichaMedica ficha = new FichaMedica(new PacienteId(source.getId()));
                    ficha.preencherDadosClinicos(
                            source.getFichaMedica().getHistoricoMedico(),
                            source.getFichaMedica().getAlergias(),
                            source.getFichaMedica().getUltimaAtualizacao()
                    );
                    ficha.adicionarObservacao(source.getFichaMedica().getObservacoes());
                    paciente.setFichaMedica(ficha);
                }

                return paciente;
>>>>>>> Stashed changes
            }
        });
    }
}
