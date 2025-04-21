Feature: Cadastro de Pacientes

  @CadastroValido
  Scenario: Cadastro bem-sucedido com lista
    Given o sistema está preparado para cadastros
    When cadastro um novo paciente válido com nome "João Silva", contato "11999998888" e médico "Dr. Carlos"
    Then o paciente é adicionado à lista de pacientes
    And uma notificação é gerada para a fisioterapeuta

  @CadastroInvalido
  Scenario: Tentativa de cadastro inválido
    Given o sistema está preparado para cadastros
    When tento cadastrar um paciente inválido com nome "Maria", contato "" e médico "Dra. Ana"
    Then o sistema exibe o erro "Contato obrigatório"