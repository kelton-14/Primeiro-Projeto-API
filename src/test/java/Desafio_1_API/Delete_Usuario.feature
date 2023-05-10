Feature: Desafio 1 API

  @TC-04
  Scenario: Delete Usuario
    Given url 'https://reqres.in/api/users/2'
    When method delete
    Then status 204
    And print response
