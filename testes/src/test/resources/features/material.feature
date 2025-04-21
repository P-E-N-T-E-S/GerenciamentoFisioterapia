Feature: Controle do material de trabalho

    Scenario: Cadastrando um material já presente no sistema
    Given eu já possuo o material "Tapes" registrado no sistema com 5 unidades
    When eu registrar "Tapes" com uma quantidade de 15
    Then Eu deverei ter um total de 20 unidades de "Tapes"

    Scenario: Utilização do material
    Given Uma consulta foi registrada no sistema
    And eu tenha 15 unidades do material "Band-aid" registrado
    When eu informar que foram precisas 3 unidades de "Band-aid" para a consulta
    Then o sistema deverá retornar que ainda existem 12 "Band-aid" no estoque

    Scenario: Aviso de Esvaimento
    Given eu tenho apenas 5 "Gaze" no sistema
    When eu informar que utilizei 3 "Gaze" em minha ultima consulta
    Then devo ser alertado que eu preciso adquirir mais "Gaze"
