# Introdução
Projeto backend Java com SpringBoot para leitura de arquivo CSV e cálculo de intervalos de prêmios.

Ao iniciar a aplicação será feito a leitura do arquivo CSV "movielist.csv" contido
na pasta resources e carregado para memória utilizando o banco de dados H2.

A aplicação permite todas as operações CRUD de um movie, além de calcular os intervalos entre 2 prêmios.

Para execução dos testes unitários, basta rodar o MovieServiceTest, que fará uma leitura do mesmo arquivo CSV para calcular os intervalos.
É possível alterar os valores dos asserts para comparar com outras massas de dados.

# Requisitos

Para build e executar a aplicação localmente você precisa de:
1.	JDK 8

# Executando a aplicação localmente
Execute o seguinte comando:

`mvn spring-boot:run
`

Caso desejar, foi disponibilizado uma collection do postman na pasta resources para chamadas locais. 
