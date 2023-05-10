Feature: Desafio 1 API

  @TC-03
  Scenario: Update usuário
    Given url 'https://reqres.in/api/users/2'
    When method patch
    Then status 200
    And print response