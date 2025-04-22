Feature: Visualização do calendário

    Scenario: Visualizar consultas agendadas no calendário
        Given que eu tenha 3 consultas agendadas nos dias 10, 17 e 28 de Abril de 2025
        When eu acessar o meu calendário de consultas
        Then o sistema deve exibir essas 3 consultas nos seus respectivos dias no calendário de Abril de 2025