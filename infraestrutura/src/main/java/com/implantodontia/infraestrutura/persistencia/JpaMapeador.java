package com.implantodontia.infraestrutura.persistencia;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaImplanta;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPA;
import com.implantodontia.infraestrutura.persistencia.core.material.MaterialJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente.PacienteJPA;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaoconsulta.GestaoConsultaJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.fichamedica.FichaMedicaJPA;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JpaMapeador extends ModelMapper {

    public JpaMapeador() {
        Configuration config = getConfiguration();
        config.setFieldMatchingEnabled(true);
        config.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        configurarUsuarioMapeamento();
        configurarPacienteMapeamento();
        configurarFichaMedicaMapeamento();
        configurarConsultaMapeamento();
        configurarMaterialMapeamento();
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
        
        addConverter(new AbstractConverter<Endereco, EnderecoJPA>() {
            @Override
            protected EnderecoJPA convert(Endereco source) {
                if (source == null) return null;
                EnderecoJPA enderecoJPA = new EnderecoJPA();
                enderecoJPA.setNome(source.getNome());
                enderecoJPA.setLogradouro(source.getLogradouro());
                enderecoJPA.setNumero(source.getNumero());
                enderecoJPA.setComplemento(source.getComplemento());
                enderecoJPA.setCidade(source.getCidade());
                enderecoJPA.setCep(source.getCep());
                return enderecoJPA;
            }
        });

        addConverter(new AbstractConverter<EnderecoJPA, Endereco>() {

            @Override
            protected Endereco convert(EnderecoJPA source) {
                return new Endereco(source.getNome(), source.getLogradouro(), source.getNumero(), source.getComplemento(), source.getCidade(), source.getCep());
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

                pacienteJPA.setEndereco(map(endereco, EnderecoJPA.class));

                // Ficha Médica
                FichaMedica ficha = source.getFichaMedica();
                if (ficha != null) {
                    FichaMedicaJPA fichaJPA = new FichaMedicaJPA();
                    fichaJPA.setId(ficha.getId());
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

                Endereco endereco = map(source.getEndereco(), Endereco.class);

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
                    FichaMedica ficha = new FichaMedicaImplanta(new PacienteId(source.getId()), fichaJPA.getId());
                    ficha.preencherDadosClinicosJPA(
                            fichaJPA.getHistoricoMedico(),
                            fichaJPA.getAlergias(),
                            fichaJPA.getUltimaAtualizacao()
                    );
                    ficha.adicionarObservacaoJPA(fichaJPA.getObservacoes(), fichaJPA.getUltimaAtualizacao());

                    paciente.setFichaMedica(ficha);
                }

                return paciente;
            }
        });
    }

    private void configurarFichaMedicaMapeamento() {
        addConverter(new AbstractConverter<FichaMedicaImplanta, FichaMedicaJPA>() {
            @Override
            protected FichaMedicaJPA convert(FichaMedicaImplanta source) {
                if (source == null) return null;

                FichaMedicaJPA fichaMedicaJPA = new FichaMedicaJPA();
                fichaMedicaJPA.setId(source.getId());
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

        addConverter(new AbstractConverter<FichaMedicaJPA, FichaMedicaImplanta>() {
            @Override
            protected FichaMedicaImplanta convert(FichaMedicaJPA source) {
                if (source == null) return null;

                Long pacienteId = source.getPaciente() != null ? source.getPaciente().getId() : null;
                PacienteId pacienteIdObj = pacienteId != null ? new PacienteId(pacienteId) : null;

                FichaMedicaImplanta ficha = new FichaMedicaImplanta(pacienteIdObj, source.getId());
                ficha.preencherDadosClinicosJPA(source.getHistoricoMedico(), source.getAlergias(), source.getUltimaAtualizacao());

                return ficha;
            }
        });
    }


    private void configurarConsultaMapeamento() {
        addConverter(new AbstractConverter<Consulta, GestaoConsultaJPA>() {
            @Override
            protected GestaoConsultaJPA convert(Consulta source) {
                GestaoConsultaJPA gestaoConsultaJPA = new GestaoConsultaJPA();
                gestaoConsultaJPA.setId(source.getConsultaId().getId());
                gestaoConsultaJPA.setDataHora(source.getDataHora());
                gestaoConsultaJPA.setDescricao(source.getDescricao());
                gestaoConsultaJPA.setClientePagou(source.isClientePagou());
                gestaoConsultaJPA.setDataVencimento(source.getDataVencimento());
                gestaoConsultaJPA.setEndereco(map(source.getLocal(), EnderecoJPA.class));
                gestaoConsultaJPA.setValor(source.getValor());

                if (source.getMateriais() != null) {
                    List<MaterialJPA> materialJPA = source.getMateriais().stream().map(m -> map(m, MaterialJPA.class)).toList();
                    gestaoConsultaJPA.setMateriais(materialJPA);
                }

                if (source.getPaciente() != null) {
                    PacienteJPA pacienteJPA = map(source.getPaciente(), PacienteJPA.class);
                    gestaoConsultaJPA.setPaciente(pacienteJPA);
                }

                return gestaoConsultaJPA;
            }

        });

        addConverter(new AbstractConverter<GestaoConsultaJPA, Consulta>() {
            @Override
            protected Consulta convert(GestaoConsultaJPA source) {
                if (source == null) return null;

                Paciente paciente = null;

                if (source.getPaciente() != null) {
                    paciente = map(source.getPaciente(), Paciente.class);
                }

                List<Material> material = null;

                if (source.getMateriais() != null) {
                    material = source.getMateriais().stream().map(m -> map(m, Material.class)).toList();
                }
                return new Consulta(
                        new ConsultaId(source.getId()),
                        source.getDataHora(),
                        paciente,
                        source.getDataVencimento(),
                        source.isClientePagou(),
                        source.getDescricao(),
                        material,
                        map(source.getEndereco(), Endereco.class),
                        source.getValor()
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
                return new Material(quantidade, nome);
            }
        });
    }

}
