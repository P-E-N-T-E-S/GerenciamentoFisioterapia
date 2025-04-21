Feature: Alerta de pagamento pendente para Doutora
    Scenario: Envio de notificação sobre pagamento pendente
    Given que eu tenha uma consulta realizada, mas não paga na data atual
    When eu acessar o sistema em um dado dia
    Then eu devo ser avisado de que existem pendencias de pagamento

