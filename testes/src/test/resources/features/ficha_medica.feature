Feature: Gerenciamento de Ficha Médica

Scenario: Exportação de ficha médica
  Given que existe um paciente com ficha médica no sistema
  When eu pedir para exportar a ficha desse paciente
  Then eu devo ser capaz de baixar esse arquivo no meu computador
  And o arquivo deve conter todos os dados clínicos do paciente

Scenario: : Atualização de observações
  Given que haja um paciente no sistema, com ficha
  When eu registrar uma consulta realizada com esse paciente
  Then eu devo ser alertado para fazer anotações na ficha médica dele
  And devo poder adicionar observações na ficha