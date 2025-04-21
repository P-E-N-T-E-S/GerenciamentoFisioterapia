Feature: Envio de notificação de pagamento para Cliente

  Scenario: Envio de notificação com 1 dia de antecedência
    Given que eu sou um paciente e realizei uma consulta com a Dra
    When a data atual for um dia antes da data de vencimento do pagamento
    Then devo ser alertado que o pagamento vencerá amanhã

  Scenario: Envio de notificação com uma semana de antecedência
    Given que eu sou um paciente e realizei uma consulta com a Dra
    When a data atual for uma semana antes da data de vencimento do pagamento
    Then devo ser alertado que o pagamento vencerá em uma semana