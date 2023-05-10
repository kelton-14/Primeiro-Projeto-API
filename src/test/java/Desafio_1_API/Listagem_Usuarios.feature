Feature: Desafio 1 API

  @CT01
  Scenario: Listagem de usuários
    Given url 'https://reqres.in/api/users'
    When method get
    Then status 200
    And print response
