# Desafio 03
Projeto para execução do Desafio 3 do programa de bolsas Compass.UOL.  
  
Ambiente: Java (JDK 17), banco de dados MongoDB Cloud, e RabbitMQ
  
## Contexto 
O desafio consiste em desenvolver dois microsserviços rodando em uma maquina virtual AWS.

Os microsserviços devem consumir um ao outro para realizar a criação de eventos e a venda de tickets.

Os microsserviços são:
- API Event Manager: Responsavel por manipular os eventos e consome API Ticket Manager para verificar se existem tickets vendidos para tal evento antes da exclusão ou atualização;
- API Ticket Manager: Responsavel por manipular os tickets e consome API Event Manager para realizar o cadastro de tickets em determinados eventos.  

### Swagger
Foi disponibilizada documentação da API com OpenAPI e Swagger. Para consulta, executar os passos para testes e execução e acessar:
- API Event Manager: http://localhost:8080/swagger-ui/index.html  
- API Ticket Manager: http://localhost:8081/swagger-ui/index.html  
