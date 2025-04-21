Feature: Envio de notificação de pagamento
  Scenario: Envio de notificação com 1 dia de antecedência
    Given que eu sou um paciente e realizei uma consulta com a Dra
    When a data atual for de um dia anterior a data de vencimento do pagamento
    Then devo ser alertado alertado que o pagamento vencerá amanhã
