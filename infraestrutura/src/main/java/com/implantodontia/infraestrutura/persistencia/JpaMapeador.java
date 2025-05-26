package com.implantodontia.infraestrutura.persistencia;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.infraestrutura.persistencia.core.administracao.material.MaterialJPA;
import com.implantodontia.infraestrutura.persistencia.core.administracao.paciente.PacienteJPA;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente.FichaMedicaJPA;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class JpaMapeador extends ModelMapper {

    public JpaMapeador() {
        Configuration config = getConfiguration();
        config.setFieldMatchingEnabled(true);
        config.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        configurarUsuarioMapeamento();
        configurarPacienteMapeamento();
        configurarFichaMedicaMapeamento();
    }

    // ============================
    // Usuario ↔ UsuarioJPA
    // ============================
    private void configurarUsuarioMapeamento() {
        addConverter(new AbstractConverter<Usuario, UsuarioJPA>() {
            @Override
            protected UsuarioJPA convert(Usuario source) {
                if (source == null) return null;
                UsuarioJPA usuarioJPA = new UsuarioJPA();
                usuarioJPA.setLogin(source.getLogin());
                usuarioJPA.setSenha(source.getSenha());
                usuarioJPA.setCargo(source.getCargo().getCodigo());
                usuarioJPA.setEmail(source.getEmail());
                return usuarioJPA;
            }
        });

        addConverter(new AbstractConverter<UsuarioJPA, Usuario>() {
            @Override
            protected Usuario convert(UsuarioJPA source) {
                if (source == null) return null;
                return new Usuario(
                        source.getLogin(),
                        source.getEmail(),
                        source.getSenha(),
                        Cargo.fromIdentificador(source.getCargo())
                );
            }
        });
    }

    // ============================
    // Paciente ↔ PacienteJPA
    // ============================
    private void configurarPacienteMapeamento() {
        addConverter(new AbstractConverter<Paciente, PacienteJPA>() {
            @Override
            protected PacienteJPA convert(Paciente source) {
                if (source == null) return null;

                PacienteJPA pacienteJPA = new PacienteJPA();
                pacienteJPA.setId(source.getPacienteId().getId());
                pacienteJPA.setCpf(source.getCpf().getCodigo());
                pacienteJPA.setNome(source.getNome());
                pacienteJPA.setContato(source.getContato());
                pacienteJPA.setMedicoResponsavel(source.getMedicoResponsavel());

                // Endereço
                Endereco endereco = source.getEndereco();
                if (endereco != null) {
                    pacienteJPA.setLogradouro(endereco.getLogradouro());
                    pacienteJPA.setNumero(endereco.getNumero());
                    pacienteJPA.setComplemento(endereco.getComplemento());
                    pacienteJPA.setCidade(endereco.getCidade());
                    pacienteJPA.setCep(endereco.getCep());
                }

                // Ficha Médica
                FichaMedica ficha = source.getFichaMedica();
                if (ficha != null) {
                    FichaMedicaJPA fichaJPA = new FichaMedicaJPA();
                    fichaJPA.setAlergias(ficha.getAlergias());
                    fichaJPA.setHistoricoMedico(ficha.getHistoricoMedico());
                    fichaJPA.setObservacoes(ficha.getObservacoes());
                    fichaJPA.setUltimaAtualizacao(ficha.getUltimaAtualizacao());

                    fichaJPA.setPaciente(pacienteJPA); // Relacionamento reverso

                    pacienteJPA.setFichaMedica(fichaJPA);
                }

                return pacienteJPA;
            }
        });

        addConverter(new AbstractConverter<PacienteJPA, Paciente>() {
            @Override
            protected Paciente convert(PacienteJPA source) {
                if (source == null) return null;

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

                // Ficha Médica
                FichaMedicaJPA fichaJPA = source.getFichaMedica();
                if (fichaJPA != null) {
                    FichaMedica ficha = new FichaMedica(new PacienteId(source.getId()));
                    ficha.preencherDadosClinicos(
                            fichaJPA.getHistoricoMedico(),
                            fichaJPA.getAlergias(),
                            fichaJPA.getUltimaAtualizacao()
                    );
                    ficha.adicionarObservacao(fichaJPA.getObservacoes());

                    paciente.setFichaMedica(ficha);
                }

                return paciente;
            }
        });
    }

    private void configurarFichaMedicaMapeamento() {
        addConverter(new AbstractConverter<FichaMedica, FichaMedicaJPA>() {
            @Override
            protected FichaMedicaJPA convert(FichaMedica source) {
                if (source == null) return null;

                FichaMedicaJPA fichaMedicaJPA = new FichaMedicaJPA();
                fichaMedicaJPA.setAlergias(source.getAlergias());
                fichaMedicaJPA.setHistoricoMedico(source.getHistoricoMedico());
                fichaMedicaJPA.setObservacoes(source.getObservacoes());
                fichaMedicaJPA.setUltimaAtualizacao(source.getUltimaAtualizacao());

                if (source.getPacienteId() != null) {
                    PacienteJPA pacienteJPA = new PacienteJPA();
                    pacienteJPA.setId(source.getPacienteId().getId());
                    fichaMedicaJPA.setPaciente(pacienteJPA);
                }

                return fichaMedicaJPA;
            }
        });

        addConverter(new AbstractConverter<FichaMedicaJPA, FichaMedica>() {
            @Override
            protected FichaMedica convert(FichaMedicaJPA source) {
                if (source == null) return null;

                Long pacienteId = source.getPaciente() != null ? source.getPaciente().getId() : null;
                PacienteId pacienteIdObj = pacienteId != null ? new PacienteId(pacienteId) : null;

                return new FichaMedica(
                        pacienteIdObj,
                        source.getAlergias(),
                        source.getHistoricoMedico(),
                        source.getObservacoes(),
                        source.getUltimaAtualizacao()
                );
            }
        });
    }

    private void configurarMaterialMapeamento() {
        addConverter(new AbstractConverter<Material, MaterialJPA>() {
            @Override
            protected MaterialJPA convert(Material source) {
                if (source == null) return null;

                MaterialJPA materialJPA = new MaterialJPA();
                materialJPA.setNome(source.getNome());
                materialJPA.setQuantidade(source.getQuantidade());

                return materialJPA;
            }
        });

        addConverter(new AbstractConverter<MaterialJPA, Material>() {
            @Override
            protected Material convert(MaterialJPA source) {
                if (source == null) return null;
                var nome = source.getNome();
                var quantidade = source.getQuantidade();
                var material = new Material(quantidade, nome);
                return material;
            }
        });
    }

}
