# Sejam bem vindos ao meu CRUD da Pokémon API
### O código faz diversas operações com a PokéAPI, como:
[] Criação de treinador.  
[] Pesquisa pelos treinadores criados.  
[] Update de informação do treinador.  
[] Adição de pokémon a lista do treinador.  
[] Consulta dos pokémons do treinador.  
[] Remoção de um dos pokémon da lista.  
[] Consulta detalhada das informações de um pokémon que o treinador possua.    
## Tecnologias e libs usadas:

*Postgresql*  
*ORMLite*  
*Spark*  

## Configuração do Postgres  
**Porta:** 32768  
**Nome do Banco:** app  
**Nome de usuário:** app  
**Senha:** app  

## Uso do código via Postman:  
### **Criação do treinador**  
Método POST  
Endpoint: localhost:8080/trainer/  
Com as seguintes informações no body no formato json, como no exemplo abaixo:  
{  
    "nickname": "John",  
    "firstName": "João",  
    "lastName": "Ketchum",  
    "email": "forever13@loser.com",  
    "team": "MYSTIC"  
}  

### **Consulta dos treinadores**  
Método GET  
Endpoint: localhost:8080/trainer/  
O retorno será uma lista de objetos com os treinadores encontrados no banco.

### **Consulta de treinador especifico**  
Método GET  
Endpoint: localhost:8080/trainer/{id}  
No lugar de {id} passe o id do treinador, como criado no cadastro.  
O retorno será todas as informações daquele treinador.  

### **Atualização de informações Treinador**
Método PUT  
Endpoint: localhost:8080/trainer/  
No body do arquivo, passe todas as informações do treinador a serem atualizadas, juntamente com o Id informado no cadastro. Conforme exemplo abaixo:  
{  
    "id": "id_do_usuario"  
    "nickname": "John",  
    "firstName": "João",  
    "lastName": "Ketchum",  
    "email": "forever13@loser.com",  
    "team": "MYSTIC"  
}  
O retorno será todas as informações atualizadas daquele treinador.  

### **Consulta dos Pokémon do treinador**  
Método GET  
Endpoint: localhost:8080/trainer/{id}/pokemon  
No lugar de {id} passe o id do treinador, como criado no cadastro.  
O retorno será a lista dos pokemons daquele treinador com seus numeros(id) e nomes.  

### **Adição de Pokémon do treinador**  
Método POST  
Endpoint: localhost:8080/trainer/{id}/pokemon  
No lugar de {id} passe o id do treinador, como criado no cadastro.  
Com as seguintes informações no body no formato json, como no exemplo abaixo:  
{  
    name: "pikachu"  
}  

### **Consulta das informações de um Pokémon do treinador**  
Método GET  
Endpoint: localhost:8080/trainer/{id}/pokemon/{pokemon_id}  
No lugar de {id} passe o id do treinador, como criado no cadastro.  
No lugar de {pokemon_id} passe o id do pokemon conforme pode ser consultado pelo método de consulta dos pokemon do treinador.  
O retorno serão todos os dados do pokémon vindo da PokéAPI.

### **Deletar um Pokémon do treinador**  
Método DELETE  
Endpoint: localhost:8080/trainer/{id}/pokemon/{pokemon_id}  
No lugar de {id} passe o id do treinador, como criado no cadastro.  
No lugar de {pokemon_id} passe o id do pokemon conforme pode ser consultado pelo método de consulta dos pokemon do treinador.  
Rodar essa rotina irá excluir o pokémon com o ID informado da lista de pokémons do treinador.


## ✒️ Autor
Gabriel Itaqui