Feature: Alerta de pagamento pendente para Doutora

  Scenario: Envio de notificação sobre pagamento pendente
    Dado que eu tenha uma consulta realizada, mas não paga na data atual
    Quando eu acessar o sistema
    Então eu devo ser avisado de que existe uma pendência de pagamento


Feature: Lembretes de Consultas Próximas

  Scenario: Receber lembrete com 1 dia de antecedência
    Given existe uma consulta agendada para "10/04/2025 14:30" no local "Clínica Central" com 1 dia de antecedência
    When eu acesso o sistema no dia "09/04/2025"
    Then devo ver um lembrete com:
      | Data        | Hora  | Local            |
      | 10/04/2025 | 14:30 | Clínica Central  |

  Scenario: Receber lembrete com 1 semana de antecedência
    Given existe uma consulta agendada para "10/04/2025 09:00" no local "Hospital Geral" com 1 semana de antecedência
    When eu acesso o sistema no dia "03/04/2025"
    Then devo receber um lembrete com:
      | Data        | Hora | Local          |
      | 10/04/2025 | 09:00 | Hospital Geral |

  Scenario: Não receber lembrete para datas fora do intervalo
    Given existe uma consulta agendada para "05/04/2025 16:00" no local "Domicílio" sem lembrete agendado
    When eu acesso o sistema no dia "01/04/2025"
    Then nenhum lembrete deve ser gerado