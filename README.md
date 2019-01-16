# rest-starwars

Este projeto foi desenvolvido utilizando Java e MongoDB.

Para o deploy deste projeto, deverá ser utilizado o servidor Wildfly.

URL base: /apsw/planetas

A API possui autenticação utilizando JWT (Json Web Token).

Para utilizar os recursos GET, será necessário apenas chamar o serviço sem necessidade de autenticação. Os serviços do tipo GET são:

Lista planetas:
Utilizado para retornar todos os planetas cadastrados na base:
/apsw/planetas/lista

Busca por nome:
Faz uma pesquisa utilizando o nome do planeta:
/apsw/planetas/buscaPorNome/{nomePlaneta}

Busca por ID:
Faz uma pesquisa utilizando o Id atribuído.
/apsw/planetas/buscaPorId/{IdPlaneta}

Para o serviço de tipo POST, deverá ser fornecido no header, os parâmetros username e password, ambos deverão ter o valor "admin".

Adicionar Planetas:
/apsw/planetas
Deverá ser enviado no corpo o nome do planeta, clima e terreno para que seu cadastro possa ser feito. Estes deverão ser enviados em formato json.

Para o serviço de tipo @DELETE,  deverá ser fornecido no header, os parâmetros username e password, ambos deverão ter valor "admin".

A remoção pode ser feita utilizando o Id do planeta ou o nome do mesmo.

Remove utilizando o Id atribuído.
/apsw/planetas/removePorId/{IdPlaneta}

Remove utilizando o nome.
/aspw/planetas/removePorNome/{nomePlaneta}

Todos as requisições descritas, são devidamente tratadas e retornam um HTTP Code e no caso de um erro, o HTTP Code de erro é retornado juntamente de uma mensagem explicativa.
