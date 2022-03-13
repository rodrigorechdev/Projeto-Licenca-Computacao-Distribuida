- O código contém um Hash <b>gerenciador de contratações</b>. A chave desse hash é o código da licença e o valor do hash é o objeto Contratação. As contratações são feitas e desfeitas manipulando esse hash conforme abaixo:
	- O hash inicia contendo um elemento para cada licenca(a lista de licencas estão no ConfigValores.java), a chave de cada elemento é a chava da licença e o valor será um objeto de Contratação com atributo status = DISPONÍVEL
	- O objeto contratação guarda o usuário contratante, o instante que a contracao foi feita(caso esteja contratato) e o estado da contratacao, que pode ser:
		- DISPONIVEL: está disponível para contratação
		- EM_USO: indisponível para contratação pois já está em uso por outro usuário
		- EM_VENCIMENTO: indisponível para contratação pois passou do tempo de vencimento (1 minuto), porém ainda não está DISPONIVEL pois o servidor espera 2 minutos para torna-la disponível. Quando está nesse estado, o usuário que contratou a licença ainda pode usar o endpoint de renovar contratação para alterar esse estado para EM_USO
- Há cada 10 segundos uma thread com o método GerenciarValidadeContratacao roda, esse método procura no hash do <b>gerenciador de contratações</b> se existe alguma licença EM_USO que passou do tempo de uso(1 minuto), caso tenha passado o estado da contratação é alterado para EM_VENCIMENTO.
Ele também analisa se contratações com estados EM_VENCIMENTO já venceram a mais de 2 minutos. Caso sim, o estado da contratacao passa a ser DISPONIVEL, logo ela está disponível para ser contratada.

- Endpoints: 
	- GET /licenca: Valida se usuário recebido do usuário existe, caso exista, busca se existe alguma licença cujo estado de contratação seja DISPONIVEL. Caso a validação seja bem sucedida, define o status de contratacao como EM_USO, a data que a contratação foi feita e o id do usuário que a contratou. Em seguida retorna ao usuário o código da licença contratada
	- PATCH /licenca/renovar: Valida se usuario e licença recebidos do usuário existem e valida se usuário informada contratou a licença informada. Caso a validação seja bem sucedida, atualiza a data de contratação para a data atual. Dessa forma o usuário tem mais 1 minuto de uso disponível.
	- DELETE /licenca/devolver: Valida se usuario e licença recebidos do usuário existem e valida se usuário informada contratou a licença informada. Caso a validação seja bem sucedida, muda o status do objeto de contratação da licença informada para DISPONIVEL

