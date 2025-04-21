Feature: Histórico de Procedimentos
  Como usuário do sistema
  Quero poder filtrar procedimentos por diferentes critérios
  Para gerenciar melhor o histórico de atendimentos

  Scenario: Nenhum procedimento em data específica
    Given não existem procedimentos cadastrados
    When eu busco procedimentos na data "29/08/2020"
    Then o sistema retorna uma lista vazia

  Scenario: Filtrar procedimentos por tipo
    Given existem procedimentos cadastrados
    When eu filtro por tipo "INTRA_OPERATORIO"
    Then o sistema retorna 1 procedimento
    And o local do procedimento é "Hospital Geral"

  Scenario: Filtrar procedimentos por mês e ano
    Given existem procedimentos cadastrados
    When eu filtro pelo mês 3 e ano 2024
    Then o sistema retorna 1 procedimento
    And o tipo do procedimento é "CONSULTA_PADRAO"