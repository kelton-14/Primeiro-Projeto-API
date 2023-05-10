Feature: Desafio 1 API

  @TC02
  Scenario: Criação de usuários
    Given url 'https://reqres.in/api/users'
    When method post
    Then status 201
    And print response
