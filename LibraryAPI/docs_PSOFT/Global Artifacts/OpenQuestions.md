                                                
                                                | OPEN QUESTIONS | 

##                                                  PHASE 1

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US07

**Pergunta**: Gostaria de saber como o Bibliotecário(Librarian) 
          obtém a informação do livro que gostaria de registar (isbn, title, genre, description, author(s)).

**Resposta (1)**: Penso que essa pergunta nao e relevante pois a resposta nao deveria ter impacto algum na vossa implementacao.
              Voces nao vao integrar o metodo do bibliotecario encontrar essa informacao no vosso produto, 
              neste ponto em particular so precisam de saber quais (e de que tipo sao) os dados que ele quer introduzir.
              Para efeitos de teste podem ou inventar ou ir procurar online essa informacao.

**Resposta (2)**: O Bibliotecário já possui essa informação. serão dados de input para este caso de uso.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US07

**Pergunta**: Quais são os critério de aceitação (acceptance criteria) da us07?

**Resposta (1)**: 7. As Librarian, I want to register a book (isbn, title, genre, description, author(s))

* se tentar registar um livro com um ISBN já existente deve ser indicado um erro
* ISBN usamos o formato ISBN-10 ou ISBN-13
* titulo do livro é obrigatório e não pode comecar ou terminar em espaços
* descrição é opcional e deve suportar conteudo HTML
* género e autor são obrigatórios 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    Authentication

**Pergunta**: Gostaria de saber quais são as caracteristicas necessários para um User fazer autenticação no sistema? 
              E a outra pergunta seria, se a autenticação é um atributo da classe Librarian e do Reader?

**Resposta (1)**: Os utilizadores autenticam-se usando um par username/password. 
                  Em relação à pergunta "se a autenticação é um atributo da classe Librarian e do Reader?" 
                  não percebo a questão... mas quer os bibliotecários, quer os leitores são utilizadores do sistema.

**Pergunta** (2) : Bom dia.
No momento do registo do Reader, estes dados são gerados automaticamente ou são da escolha do anónimo no momento do registo?
Há regras para estes conceitos de autenticação? P.e. tamanhos mínimos ou máximos
O username ou password do utilizador podem ser alterados em algum momento pelo Reader após o registo?

**Resposta (2)** : bom dia,
o utilizador que se pretende registar irá introduzir o username e a password que deseja.
o username deve ser o email da pessoa e terá que ser único no sistema.
a password é um conjunto de caracteres alfanumericos com um minimo de 8 caracteres, pelo menos 1 maiuscula, pelo menos 1 minuscula e pelo menos 1 caracter especial ou algarismo

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Anonymous

**Pergunta**: Gostaria são as caracteristicas que descreve uma pessoa anonymous? 

**Resposta (1)**: Um utilizador anónimo é um utilizador que desconhecemos e não é importante saber nada nada sobre ele.
                  para o requisito "11. As anonymous I want to register as a reader" de notar que este anónimo se quer 
                  registar como leitor e como tal irá indicar as caracteristicas necessárias e deixar de ser anónimo no fim do registo

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            US15

**Pergunta**: Será possível fazer um empréstimo de vários livros a um leitor? Ou cada livro emprestado, corresponde a um só empréstimo?

**Resposta (1)**: Um empréstimo é apenas de um único livro

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US08-Update Book

**Pergunta**: Qual o dado que precisamos de introduzir para proceder à atualização dos dados de um livro?

**Resposta (1)**: À execção do ISBN todos os dados sao alteraveis.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                            Fine / Librarian Info / Genres

**Pergunta**: Como é que a fine é aplicada?
              O librarian tem a mesma user info que a do reader? (Name, E-mail, Phone Number, etc)
              Os genres dos livros são uma lista dada pelo cliente, é o librarian que a faz e vai adicionando ou são atribuidos de uma outra forma?

**Resposta (1)**: - Como é que a fine é aplicada?
                  A aplicação da multa é tratada fora do sistema. o sistema deve calcular o valor da multa com base no numero de dias de atraso e num valor por dia. 
                  Esse valor por dia deve ser facilmente parametrizavel nos sistema (ex., bootstrap ou ficheiro de propriedades)
                  - O librarian tem a mesma user info que a do reader? (Name, E-mail, Phone Number, etc) os bibliotecários são funcionários da biblioteca
                  e a sua informação pessoal é gerida no sistema de processamento de salários e não neste sistema.
                  - Os genres dos livros são uma lista dada pelo cliente, é o librarian que a faz e vai adicionando ou são atribuidos de uma outra forma? 
                  Os géneros de livros configurados na base de dados via bootstrapping (WP #0A)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                Atributos Classe Librarian

**Pergunta**: Gostaríamos de saber quais os atributos que a classe Librarian deve possuir. 

**Resposta (1)**: Nao querendo roubar a deixa ao professor das teoricas, mas se fosse eu o cliente a resposta seria "O que e uma classe, o que e um atributo, e porque e que haveria eu de saber isso?"
                  Esse tipo de detalhes tecnicos nao se devem de discutir com o cliente, pois a maior parte das vezes eles nao terao conhecimento tecnico suficiente para fazer decisoes informadas.
                  Caso vejam que as decisoes derivadas desses detalhes tem impactos funcionais relevantes, sao esses impactos que devem de discutir com os clientes.

**Resposta (2)**: Tal como dito acima, a questão está escrita de forma demasiado técnica. devem falar com o cliente evitando esse tipo de termos. 
                  Uma melhor formulação será "Que caracteristicas descrevem um X".
                  Neste caso o Bibliotecário é um utilizador do sistema e apenas necessitamos de saber o seu nome e email, obviamente juntamente com a sua informação de autenticação no sistema.

**Pergunta(2)**: Com que dados é feita a autenticação? Email e password?

**Resposta (3)**: Os utilizadores autenticam-se usando um par username/password.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    Emprestimos

**Pergunta**: 1. O tempo limite de entrega de um livro é expectável que mude?
              2. Um empréstimo corresponde a um livro ou pode corresponder a dois ou três? Se corresponder a um ou mais, a entrega pode ser feita parcialmente?
              3. É expectável notificar os "readers" que têm livros overdue?
              4. Relativamente a data de entrega, ficamos pelos dias ou temos de contabilizar as horas?

**Resposta (1)**: - O tempo limite de entrega de um livro é expectável que mude?
                  O numero de dias que um livro pode ser requisitado é um valor fixo que deve ser facilmente configuravel (ex., ficheiro de propriedades, bootstraping)

**Resposta (2)**: - Um empréstimo corresponde a um livro ou pode corresponder a dois ou três? Se corresponder a um ou mais, a entrega pode ser feita parcialmente? 
                  Um empréstimo é de um único livro.

**Resposta (3)**: - É expectável notificar os "readers" que têm livros overdue?
              De momento não está contemplada essa funcionalidade

**Resposta (4)**: - Relativamente a data de entrega, ficamos pelos dias ou temos de contabilizar as horas?
                  Apenas devem contabilizar dias

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US17

**Pergunta**: Deve ser possível que o leitor e o bibliotecário tenham acesso aos detalhes de um empréstimo através do seu número.
          Pode especificar quais são os detalhes que devem ser facultados? Existe alguma diferença nos detalhes a serem facultados, caso seja um leitor ou um bibliotecário a solicitar?

**Resposta (1)**: A ambos deve ser facultado:
                  -lending number
                  - book title
                  - lending date
                  - return date
                  - number of days till return date (if applicable)
                  - number of days in overdue (if applicable)
                  - fine amount (if applicable)

**Pergunta (2)**: O lending number é um id auto-gerado e auto-incrementado?

**Resposta (2)**: o lending number é um número criado pelo sistema e composto pelo ano de registo e um número sequencial, ex., 2023/1, 2024/19876

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Book

**Pergunta**: Como verificamos que um livro está disponível para ser requisitado?

**Resposta (1)**: O sistema que estão a desenvolver vai ser utilizado pelo bibliotecário que vai entregar fisicamente o livro ao leitor. o sistema nao necessita controlar o 
                  numero de exemplares de cada livro tem a sua disponibilidade

**Pergunta (2)**: Quais são os critérios de aceitação da descrição ? Existe um número mínimo e/ou máximo de caractéres ? Em relação ao titulo do livro , apenas devem ser permitidas letras? Existem palavras proibidas ?

**Resposta (2)**: maximo 4096 caracteres, pode conter qualquer caracter alfanumérico. não existem palavras proibidas.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                3.4 WP#3A - Readers

**Pergunta**: 
_NOME_:
É requisitado apenas o 1º e ultimo nome? 3, 4, 5 nomes?
São permitidos títulos? (Sr., Dr., …)
Apenas devem ser permitidas letras?
Deve ser apenas permitido o alfabeto latino? Devem ser permitido outros alfabetos (cirilico, grego, ...) ou sistemas de escrita (árabe, hebraico, ...)?
Pode ser deixado vazio?
Pode ter apenas espaços?
Existem algumas palavras proibidas?

_EMAIL_:
Um utilizador pode ter vários emails?
Um mesmo email pode pertencer a vários utilizadores?
É necessário validar se o email realmente existe?
Permite todos os dominios de email? Ou apenas um grupo (gmail.com, hotmail.com, isep.ipp.pt)? Se sim, quais?

_DATA DE NASCIMENTO_:
Existe uma idade mínima? (ex. nascido em 2024)
Existe uma idade máxima? (Ex. nascido em 1812)
A idade influencia algo? (Acesso a certas funcionalidades/livros, Recomendação de livros)

_NÚMERO DE TELEFONE_:
Um utilizador pode ter vários números de telefone?
Um mesmo número de telefone pode ser usado em vários leitores?
O número de telefone deve ser português ou admite outros países?
O número de telefone deve ser móvel ou pode ser fixo?

_GDPR CONSENT_:
Um simples sim/não é suficiente ou deve ter algum tipo de assinatura digital?
Que efeitos existem ao recusar? É permitido ao utilizador criar conta como leitor? Perde algumas funcionalidades?
Posso recusar apenas parcialmente?

_NÚMERO DE LEITOR_:
Deve ter um tamanho pré-definido (Ex. todos os nºs devem ter 8 caracteres)
Segue alguma regra (ex. certos números para certas bibliotecas/idades/…) ou é apenas auto-incremental?

--#--

**Resposta (1)**: 
_NOME_:
É requisitado apenas o 1º e ultimo nome? 3, 4, 5 nomes? - não necessitamos distinguir quantos nomes a pessoa tem
São permitidos títulos? (Sr., Dr., …) - nao há necessidade de capturar esta informação
Apenas devem ser permitidas letras? - qualquer caracter alfanumérico
Deve ser apenas permitido o alfabeto latino? Devem ser permitido outros alfabetos (cirilico, grego, ...) ou sistemas de escrita (árabe, hebraico, ...)? - basta considerar o alfabeto Latino
Pode ser deixado vazio? - não
Pode ter apenas espaços? - não
Existem algumas palavras proibidas? - sim. deve existir no sistema uma configuração de "palavras proibidas" que não são aceites no nome do Leitor

_EMAIL_:
Um utilizador pode ter vários emails? - não
Um mesmo email pode pertencer a vários utilizadores? - não
É necessário validar se o email realmente existe? - não. basta que esteja no formato correto
Permite todos os dominios de email? Ou apenas um grupo (gmail.com, hotmail.com, isep.ipp.pt)? Se sim, quais? - qualquer dominio

_DATA DE NASCIMENTO_:
Existe uma idade mínima? (ex. nascido em 2024) - Leitor deve ter pelo menos 12 anos
Existe uma idade máxima? (Ex. nascido em 1812) - não
A idade influencia algo? (Acesso a certas funcionalidades/livros, Recomendação de livros) - de momento esse controlo é feito fisicamente pelo bibliotecário e fora do sistema

_NÚMERO DE TELEFONE_:
Um utilizador pode ter vários números de telefone? - não
Um mesmo número de telefone pode ser usado em vários leitores? - sim
O número de telefone deve ser português ou admite outros países? - basta considerar numeros portugueses de momento
O número de telefone deve ser móvel ou pode ser fixo? - ambos

_GDPR CONSENT_:
Um simples sim/não é suficiente ou deve ter algum tipo de assinatura digital? - simples "sim/não"
Que efeitos existem ao recusar? É permitido ao utilizador criar conta como leitor? Perde algumas funcionalidades? - o utilizador tem que aceitar a politica de privacidade de dados. se recusar não se poderá registar no sistema
Posso recusar apenas parcialmente? - é possivel recusar o consentimento de partilha de informação com terceiros, bem como o consentimento para efeitos de marketing

_NÚMERO DE LEITOR_:
Deve ter um tamanho pré-definido (Ex. todos os nºs devem ter 8 caracteres) - não
Segue alguma regra (ex. certos números para certas bibliotecas/idades/…) ou é apenas auto-incremental? - é composto pelo ano de registo e um número sequencial, ex., 2023/1, 2024/19876

--#--

**Pergunta**: Como leitor posso alterar todos os meus dados? Se não, quais?

**Resposta (2)**: Como leitor posso alterar todos os meus dados? Se não, quais? - sim, à exceção do número de leitor

**Pergunta**: Como bibliotecário posso ver todos os dados do leitor?
                 Se o utilizador recusar (total ou parcialmente) o GDPR, o bibliotecário vê menos dados do leitor? Se sim, quais?

**Resposta (3)**: Como bibliotecário posso ver todos os dados do leitor? - não. a data de nascimento não deve ser visualizada, mas sim a idade do leitor
                  Se o utilizador recusar (total ou parcialmente) o GDPR, o bibliotecário vê menos dados do leitor? Se sim, quais? - não tem influência

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US03    

**Pergunta**: Gostava de saber se quando o bibliotecário cria a bio do autor, apenas é constituído por texto ou tem outro campo?
              Aproveito e pergunto sobre o número do autor, se este é apenas um numero e como este é formado?
 
**Resposta (1)**: A breve biografia do autor deve permitir conteudo HTML 
              O número do autor é um número sequencial gerado pelo sistema

**Pergunta(2)**: Quão breve deverá ser a biografia? Existe um limite de caracteres?

**Resposta (2)**: No máximo 4096 carcateres.

**Pergunta(3)**: Boa tarde, quais são os critério de aceitação (acceptance criteria) da us03?

**Resposta (3)**: Bom dia, Ao introduzir todos os dados obrigatórios no formato correto, o autor deve ficar registado no sistema
Ao introduzir algum dado no formato incorreto ou não introduzir um dado obrigatório deve ser dada indicação de erro
Apenas os utilizadores com permissões de librarian podem executar esta opção

**Pergunta(4)**: 

**Resposta (4)**:


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US04

**Pergunta**: Que informações do autor é que o bibliotecário pode alterar?

**Resposta (1)**: À exceção do "author number" pode alterar qualquer informação.

**Pergunta(2)**: "As Librarian I want to update an author’s data"
Que tipo de dados podem ser alterados? Apenas a "short bio"?

**Resposta (2)**: https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=28948#p36577 



--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP#2A – Books

**Pergunta**: Quais são os critérios de aceitação das us09(As Librarian or Reader I want to know the details of a book given its ISBN) e us10(. 
              As Librarian or Reader I want to search books by genre)?

**Resposta (1)**: devem ser mostrados todos os dados do livro (isbn, title, genre, description, author(s)),
devem ser mostrados todos os livros do género indicado. a pesquisa deve ser não exata, exemplo, se pesquisar por "fi" devem ser devolvidos os livros do género "ficcao" e do género "financas"

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US15

**Pergunta** (1) : Boa tarde quando o librarian for associar um livro a um leitor, o reader e book são selecionados de uma lista do sistema?

**Pergunta** (2) : A data de returno é escrita pelo librarian, como imput data?

**Resposta (1)** : o empréstimo será de um dos livroo da biblioteca e a um dos leitores da biblioteca. ambos previamente registados no sistema.

**Resposta (2)** : a data de retorno deve ser calculada pelo sistema

**Pergunta** (3) : a associação do emprestimo ao livro pode ser feito atraves do isbn? (usar o isbn como id do livro)

**Resposta (3)** : Bom dia O isbn é identificador do livro.



--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    Genre 

**Pergunta** (1) : Boa tarde, quais sao as caracteristicas necessárias para uma classe genre?

**Resposta (1)** : Bom dia
Essa pergunta não está escrita de uma forma que o cliente perceba. O cliente não sabe o que é uma classe…

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US05
**Pergunta** (1) : Boa tarde, quais são os critério de aceitação (acceptance criteria) da us05?

**Resposta (1)** : Bom dia , Se o utilizador introduzir um número de autor existente devem ser mostrados todos os dados do autor
Se o utilizador introduzir um número de autor inexistente deve ser indicado erro

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US06
**Pergunta** (1) : Boa tarde, quais são os critério de aceitação (acceptance criteria) da us06?

**Resposta (1)** : Bom dia, O utilizador introduz alguns caracteres e o sistema devolve a lista de todos os autores cujo nome começa pelas letras introduzidas

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    Genre- Book 

**Pergunta** (1) : Boa tarde, um livro pode ter mais que um género?
**Resposta (1)** : Bom dia Apenas um género

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US08

**Pergunta** (1) : Boa tarde, quais são os critérios de aceitação da US08?

**Resposta (1)** : Bom dia Podem alterar todos os dados do livro a exceção do isbn.
Deve ser possível “limpar” os dados não obrigatórios

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US05

**Pergunta** (1) : Boa tarde, na US05 é pedido que através do author number se obtenha todos os dados do autor, ou seja, nome e short bio?

**Resposta (1)** : Bom dia,sim

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                    US 12 - Update do Reader as a reader

**Pergunta** (1) : Relativamente ao US 12 - "As Reader I want to update my personal data, e.g., phone number"
Atributos do Reader --> Reader Number, name, email, dateOfBirth, phone number, GDPR consent
Qual será o(s) atributo(s) que o reader não poderá atualizar?

**Resposta (1)** : Bom dia, Não pode alterar o reader number apenas 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                            WP#4A – Lendings

**Pergunta** (1) : Boa tarde, quais são os critério de aceitação (acceptance criteria) das US15,16 e 17?

**Resposta (1)** : Bom dia essa informação já foi respondida em vários post. Se tiver alguma dúvida concreta por favor colocar novo post

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    ISBN

**Pergunta** (1) : Em relação ao ISBN , temos de validar o digito de verificação que faz parte da norma do ISBN-10 e 13?

**Resposta (1)** : Bom dia, Sim

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US03- Author 

**Pergunta** (1) : Boa tarde é obrigatório preencher as caracteristicas do autor (name, short_bio) ? Ou apenas uma delas?
Qual o minimo e o maximo de caracteres para um nome de um autor? 

**Resposta (1)** : Bom dia sao ambas obrigatórias
um nome de author tem um máximo de 150 caracteres

**Pergunta** (2) : Boa tarde,
Pode haver nomes repetidos entre autores?

**Resposta (2)** : Bom dia sim 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    US07- Create a Book

**Pergunta** (1) : Boa tarde,Podemos criar um livro com autores que ainda não foram criados? E estes autores deverão ser posteriormente guardados no sistema?

**Resposta (1)** : Bom dia Os autores devem ser criados previamente e depois selecionados aquando da criação do livro.
Notem que para os grupos que não estão a desenvolver o work package “author” esta resposta não se aplica

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP4A - US16 - Return Book

**Pergunta** (1) : Boa tarde,
1. Quando um utilizador pretende devolver um livro, que informação é que o sistema lhe deve pedir?
2. O valor da multa - e se passou o prazo de devolução - é calculado aquando da consulta do empréstimo / momento de devolução, ou é algo a ser calculado periodicamente?
3. O valor da multa é algo a ser persistido na base de dados? Dos empréstimos cujos livros foram devolvidos; ou de todos, incluindo os em aberto?
4. A multa tem um valor máximo?
respostas anteriores relevantes: https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=28894#p36559

**Resposta (1)** : 
1. deve ser dada a possibilidade de colocar um comentário/observações (opcional)
2. esta é uma decisão de implementação. de um ponto de vista funcional o importante é que quando se tenta devolver o livro o valor da multa seja o correto
3. sim. será posteriormente tratado por outra parte do sistema efetuar a cobrança (fora de âmbito) 
4. não 

**Pergunta** (2) : Boa noite,
1. A questão era no sentido de saber se o sistema pede ao reader o lending number ou o isbn para identificar o livro a devolver.
   1.1 O comentário fica afeto ao livro, ou ao empréstimo?

**Resposta (2)** : Bom dia
1. Deve existir as duas possibilidades
1.1 o comentário é sobre o empréstimo

**Pergunta** (3) : Qual é o comprimento máximo do comentário?

**Resposta (3)** : 1024 caracteres

**Pergunta** (4) : A US diz: As Reader I want to return a book. If the return is overdue I’m fined by the library.
No caso de haver 'fine' a aplicar, o sistema deve marcar o livro como devolvido na mesma?
O que deve ficar registado na base de dados neste caso?
O que deve ser mostrado ao utilizador no caso de atraso na devolução?

**Resposta (4)** : bom dia,

No caso de haver 'fine' a aplicar, o sistema deve marcar o livro como devolvido na mesma?
- sim

O que deve ficar registado na base de dados neste caso?
- ignorando o facto do cliente nao saber responder a esta pergunta quando formulado desta forma, o que se pretende com este caso de uso é registar que o livro foi devolvido, por quem, quando, em que condições e se foi ou não aplicada uma multa e de que valor

O que deve ser mostrado ao utilizador no caso de atraso na devolução?
- o valor da multa

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Book Title


**Pergunta** (3) : Em relação ao título, existe um número mínimo e/ou máximo de caracteres?

**Resposta (4)** : bom dia, máximo de 128 caracteres

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                Name (Author, Reader, Librarian)

**Pergunta** (1) : Boa tarde,
O cliente especificou que o nome do autor tem um limite máximo de 150 caractéres (https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29333#p37156)
1. Aplica-se a mesma regra aos nomes de Reader e Librarian?
O Reader tem uma lista de palavras que não podem constar no seu nome (https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=28876#p36562)
2. Aplica-se a mesma regra aos nomes de Author e Librarian?
   2.1. E as restantes regras que constam nesta resposta sobre o Reader, também se aplicam às outras entidades?


**Resposta (1)** : bom dia, sim, as regras para os nomes são as mesmas para o autor, leitor e bibliotecário

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US04

**Pergunta** (1) : Boa tarde, quais são os critério de aceitação (acceptance criteria) da us04?

**Resposta (1)** : Bom dia
Podem alterar qualquer dado do autor excepto o número de autor
Os dados introduzidos devem respeitar o formato correto
Deve ser possível “limpar” os dados opcionais


**Pergunta** (2) : Não existem dados opcionais de um Author segundo o seguinte tópico, certo? https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29333

**Resposta (2)** : bom dia, a foto do autor é opcional

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

##                                                  PHASE 2 


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                            PHASE 2 - 4.2 WP#2B - Books

**Pergunta** (1) : Boa tarde! Gostaria de saber os critérios de aceitação (Acceptance Criteria), das us7, us8, us9 e us10 do Books da fase 2.

**Resposta (1)** :
boa tarde,
7. As Librarian, I want to register a book with a book cover photo
   this is a refinement of the existing use case. the user may choose to add a photo of the book cover. the photo must be in the jpeg or png format and at most 20 KB
8. As Reader I want to search books by title
   ability to search by title entering the first letters of the title
9. As Librarian I want to know the Top 5 books lent
   returns the list of the 5 books that have been lent the most in the last year. it must return for each book, the number of times the book has been lent. the result must be sorted descending order.
10. As Librarian I want to know the Top 5 genres
    returns the 5 genres that the librarian possesses more books of. it must return the number of books per genre. the result must be sorted descending order

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP#3B - US11
**Pergunta** (1) : Boa tarde,
O que é que define o TOP 5 de readers? A quantidade de livros emprestados ao reader?

**Pergunta (2)** : Devem ser tidos em conta mais métodos de sorting para além do top5 por lendings (critério e/ou quantidade de resultados)? (possivel implementação desses métodos no futuro)

**Resposta (1)** : boa tarde,
sim, os "5 top readers" são os leitores que mais livros requisitaram no último ano.
não se preve outros métodos no futuro

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP#3B - US12

**Pergunta** (1) : Boa tarde,

Quais são os géneros a ser considerados para a lista de interesses? Os géneros disponíveis para os books?

**Resposta (1)** : boa tarde,
sim. apenas pode escolher géneros da lista definida no WP0A.2 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    Books

**Pergunta** (1) : Bom dia,
Podem existir livros iguais (mesmo título, género, etc) mas com ISBN diferentes?

**Resposta (1)** : bom dia,
sim embora muito raro

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP#4B-US23

**Pergunta** (1) : Bom dia,
A listagem de empréstimos em atraso diz respeito a livros que se encontram emprestados, livros que já foram devolvidos ou ambos?

**Resposta (1)** : bom dia,
livros emprestados e que ainda nao foram devolvidos

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                        WP #4B - Critérios de aceitação

**Pergunta** (1) : Boa tarde,
Quais são os critérios de aceitação destas USs?
23. As Librarian I want to list overdue lending sorted by their tardiness
14. As Librarian I want to know the average number of lending per genre of a certain month
15. As Librarian I want to know the Average lending duration

**Resposta (1)** : 


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                        WP #5 - Critérios de aceitação

**Pergunta** (1) : Boa tarde,

Quais são os critérios de aceitação destas USs?
16. As Librarian I want to know the number of lendings per month for the last 12 months, broken down by genre
17. As Librarian I want to know the Top 5 readers per genre of a certain period
18. As Librarian I want to know the Monthly lending per reader of a certain period
19. As Librarian I want to know the Average lending duration Per genre per month for a certain period

**Resposta (1)** : 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP #4B - Lending

**Pergunta** (1) :
Boa tarde,
23. As Librarian I want to list overdue lending sorted by their tardiness
-1. Não sei se é o local apropriado para a questão, mas qual é o número máximo de empréstimos que devem ser apresentados a cada consulta?

14. As Librarian I want to know the average number of lending per genre of a certain month
-2. Para esta US, também são incluídos tanto empréstimos em aberto como devolvidos?
-3. A média deve contemplar a parte decimal do valor? Se sim, com quantas casas decimais?

15. As Librarian I want to know the Average lending duration
-4. O resultado deve ser apresentado em número de dias?
-5. Com parte decimal, como na questão 3?

**Resposta (1)** : boa tarde,
23. As Librarian I want to list overdue lending sorted by their tardiness
-1. Não sei se é o local apropriado para a questão, mas qual é o número máximo de empréstimos que devem ser apresentados a cada consulta?
não há limite, mas devem suportar paginação no caso de operações com elevado número de resultados

14. As Librarian I want to know the average number of lending per genre of a certain month
-2. Para esta US, também são incluídos tanto empréstimos em aberto como devolvidos?
todos os emprestimos independente do seu estado
-3. A média deve contemplar a parte decimal do valor? Se sim, com quantas casas decimais?
sim. 1 casa decimal

15. As Librarian I want to know the Average lending duration
-4. O resultado deve ser apresentado em número de dias?
sim
-5. Com parte decimal, como na questão 3?
sim. 1 casa decimal

**Pergunta** (2) : Mais uma questão sobre esta US:
14. As Librarian I want to know the average number of lending per genre of a certain month
    O que é que é realmente pretendido? A média diária de empréstimos por género num dado mês? Ou a média mensal de empréstimos por género num mês, ao longo de vários anos?

**Resposta (2)** : Boa tarde
Pretende-se saber para esse mês , em média, quantos livros de um dado género foram emprestados por dia 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                    WP#3B-Readers US13

**Pergunta** (1) : Bom dia,
Qual deveria ser o resultado da pesquisa de livros sugeridos, caso o leitor não tenha inserido uma lista de interesses?

**Resposta (1)** : Boa tarde,
Devem usar os top 5 genéros mais requisitados

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            Foto

**Pergunta** (1) : https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29649#p37808
Referente ao que é falado sobre a foto, o mesmo se aplica ao Wp do author/reader? 

**Resposta (1)** : Sim 

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        WP #5 - US18

**Pergunta** (1) : As Librarian I want to know the Monthly lending per reader of a certain period.
Pretende-se uma lista de médias, ou a média de um utilizador em específico?

**Resposta (1)** : Boa tarde
Pretende-se a média de empréstimos. Ou seja, quantos livros foram emprestados num dado mês tendo em conta a totalidade de leitores registados na biblioteca

**Pergunta** (2) : Não sei se percebi. A conta é média( total de empréstimos numa data / utilizadores registados a essa data), entre o início e o fim de um mês?

**Resposta (2)** : sim

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                        Varias questoes relacionadas com Author

**Pergunta** (1) : 1)
As Reader I want to know the books of an Author
As Reader I want to know the co-authors of an author and their respective books
Como irá ser feita a pesquisa? Pelo nome do autor? Pelo authors number?

**Resposta (1)** : devem pensar em termos de recursos rest e em subrecursos, não em “pesquisas” 

**Pergunta** (2) : 2)
As Librarian I want to register an author with an optional photo
É esperado alterar o update, feito na fase 1, para que seja possivel os authors já criados colocarem uma foto deles?

**Resposta (2)** : sim

**Pergunta** (3) : Quando se refere "em termos de recursos rest e em subrecursos" quer dizer o que? Podemos assumir para ambos os casos que o cliente ja tem o link para o autor?

**Resposta (3)** : Exato.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        WP #1B - Authors

**Pergunta** (1) :
3. As Librarian I want to register an author with an optional photo
4. As Reader I want to know the books of an Author
5. As Reader I want to know the co-authors of an author and their respective books
6. As Reader I want to know the Top 5 authors (which have the most lent books)

Boa noite, gostaria de saber os critérios de aceitação. Obrigado

**Resposta (1)** :
3. As Librarian I want to register an author with an optional photo
os mesmos critérios do caso de uso do WP1 contemplando agora a possibilidade de adicionar uma imagem. ver no forum as perguntas relacionadas que já foram respondidas
4. As Reader I want to know the books of an Author
deve retornar a lista de livros desse autor ou uma lista vazia. devem ter em consideração paginação se a lista for demasiado longa
5. As Reader I want to know the co-authors of an author and their respective books
deve retornar uma lista com os autores que escreveram livros em conjunto com um dado autor. para cada coautor deve ser retornada alista de livros escritos em conjunto. devem ter em consideração paginação se a lista for demasiado longa
6. As Reader I want to know the Top 5 authors (which have the most lent books)
deve retornar os 5 autores com o maior numero de livros requisitados no ultimo ano

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            Authors top 5
**Pergunta** (1) :
As Reader I want to know the Top 5 authors (which have the most lent books)
O que é esperador retornar? Top 5 dos autores por ordem e ao lado o total de lendings de cada um deles?

**Resposta (1)** : sim

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                Us 7. As Librarian, I want to register a book with a book cover photo3

**Pergunta** (1) :
Boa tarde,
Na US 7. "As Librarian, I want to register a book with a book cover photo", deveremos retornar a imagem quando fazemos um pedido de listagem de um ou varios livros ou apenas deveremos guardar a imagem no sistema?

**Resposta (1)** :
deve haver possibilidade de consultar a imagem de um dado livro. nao necessitam retornar a imagem nas listagens de livros

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            US 12 - Lista de Interesses

**Pergunta** (1) : Boa tarde.
Quando um utilizador adiciona interesses à sua lista, deverá ser permitido inserir apenas géneros já existentes no sistema, ou o utilizador pode inserir livremente palavras-chave?

Se for permitida a inserção livre, existe uma lista de palavras ou interesses proibidos?

**Resposta (1)** : apenas generos existentes

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            US 11 - Informação do Leitor

**Pergunta** (1) : Ao ver o top 5 leitores, que informações devem ser exibidas para cada leitor?
Deverá ser mostrado apenas o número do leitor ou as informações disponíveis ao pesquisar um leitor na iteração anterior?

**Resposta (1)** :
todas as informações que sao retornadas quando se consulta um leitor


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                            WP #1B - Authors - US05

**Pergunta** (1) :
As Reader I want to know the co-authors of an author and their respective books.
Os co-authors devem ser guardados nos livros? Se sim, quando o livro é criado para além dos atributos da WP anterior temos de criar o livro com mais este atributo?

**Resposta (1)** : o requisito "7. As Librarian, I want to register a book (isbn, title, genre, description, author(s))" do WP 1 já devia ter permitido o registo de livros com vários autores. não se trata de um novo atributo "coautor"

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US 13 - Sugestões de Livros

**Pergunta** (1) : De que forma devem ser sugeridos os livros relacionados com a lista de interesse?
Devem ser apresentados os livros mais requisitados, escolhidos aleatoriamente ou por outro critério?
Além disso, qual é o número máximo de livros que devem ser sugeridos por lista?


**Resposta (1)** : pode ser sugestão aleatória dentro dos livros dos interesses indicados pelo leitor


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Bonus US - Critérios de aceitação

**Pergunta** (1) : Boa tarde,

Quais são os critérios de aceitação destas USs?
26. Augment the reader profile with a funny quote based on the date of birth of the reader
27. As Reader I want to search books by author
28. As Librarian I want to search Readers by phone number
29. As Librarian I want to search Readers by email
30. As Librarian I want to know the Average lending duration Per book


**Resposta (1)** : 
26. Augment the reader profile with a funny quote based on the date of birth of the reader
ao retornar os dados do leitor incluir um novo atributo que contém uma citação/facto interessante que tenha acontecido no dia de aniversário do leitor (considerar ano/mês)

27. As Reader I want to search books by author
pesquisa de livros pelo autor cujo nome comece pelo valor introduzido pelo utilizador

28. As Librarian I want to search Readers by phone number
pesquisa de leitores pelo numero de telefone. pesquisa exata.

29. As Librarian I want to search Readers by email
pesquisa de leitores pelo email. pesquisa exata.

30. As Librarian I want to know the Average lending duration Per book
para um dado livro, retornar a duração média dos seus empréstimos no ultimo ano


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        US 26 - Funny Quotes

**Pergunta** (1) :
Boa tarde.
Relativamente à funcionalidade das funny quotes de acordo com o mes ou ano de nascimento do reader, é necessario ser ilustrada as informação de todos os readers e adicionalmente a funnyquote de cada reader ou so quer esta funcionalidade relativamente a um specific reader à parte? 


**Resposta (1)** : Apenas de um dado reader quando se obter o perfil do mesmo

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                                US 14

**Pergunta** (1) : Pretende-se receber uma resposta com todos os gêneros e respetiva media de empréstimos num determinado mês, ou apenas a media de empréstimos num dado mês e gênero? 

**Resposta (1)** : De todos os géneros


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                WP#2B - Books - As Librarian I want to know the Top 5 genres

**Pergunta** (1) :
Boa tarde,
É pretendido:
1. top 5 genres dos livros da biblioteca.
2. top 5 genres dos livros dos lendings (ativos ou não).
   Obrigado pela atenção.


**Resposta (1)** : Top 5 dos géneros com mais livros na biblioteca

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                WP #5 - Critérios de aceitação

**Pergunta** (1) : Boa tarde,
Quais são os critérios de aceitação destas USs?
16. As Librarian I want to know the number of lendings per month for the last 12 months, broken down by genre
17. As Librarian I want to know the Top 5 readers per genre of a certain period
18. As Librarian I want to know the Monthly lending per reader of a certain period
19. As Librarian I want to know the Average lending duration Per genre per month for a certain period

**Resposta (1)** :

16- lista ordenada por mês e por género com o número total de empréstimos desse género nesse mês
17- lista ordenada dos 5 leitores com mais empréstimos para um dado período
18- lista com o número de empréstimos de um dado leitor para um dado período
19 a lista ordenada por mês e género com a duração média dos empréstimos nesse mês para esse género, para um dado período

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        WP #5 - Reporting 

**Pergunta** (1) : Boa tarde! Gostaria de saber os critérios de aceitação (Acceptance Criteria), da us17 do reporting da fase 2

**Resposta (1)** :

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------






