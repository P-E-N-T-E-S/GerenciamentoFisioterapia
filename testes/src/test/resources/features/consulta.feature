Feature: Alerta de pagamento pendente para Doutora

  Scenario: Envio de notificação sobre pagamento pendente
    Dado que eu tenha uma consulta realizada, mas não paga na data atual
    Quando eu acessar o sistema
    Então eu devo ser avisado de que existe uma pendência de pagamento
