# Box videos - Acervo online de vídeos

---

O projeto possui o objetivo de ser um micro serviço de acervo de vídeos.

## Frameworks e Bibliotecas:

- Spring Boot 3.0
- Java 17
- Flyway
- PostGreSQL
- Arquitetura Hexagonal

## Configurações:

### Criando banco de dados no PostGreSQL

#### 1. Criar o usuário e informe a senha desejada:

Uma observação, a senha deve ser informado na variável de ambiente **PASSWORD_BD_DEV**

```bash
$ createuser box_videos -D -P
```

#### 2. Criar o banco de dados:

```bash
$ createdb box_videos -O box_videos
```

## Endpoints disponíveis:

Uma descrição dos endpoints da API

| HTTP METHOD | PATH                    | DESCRIÇÃO                                                           | POSSÍVEIS ERROS                                        |
|-------------|-------------------------|---------------------------------------------------------------------|--------------------------------------------------------|
| POST        | /categories             | Criar uma nova categoria                                            | 400 - categoria já existente                           |
| GET         | /categories             | Obtem todsas a categorias                                           | --                                                     |
| GET         | /categories/{id}/       | Obtem categoria por id                                              | 404 - quando categoria não encontrada                  |
| PUT         | /categories/{id}/       | Atualizar um categoria por id ou criar uma nova caso não encontrado | ---                                                    |
| PATCH       | /categories/{id}/       | Atualizar um categoria parcial por id                               | 404 - quando categoria não encontrada                  |
| DELETE      | /categories/{id}/       | exclui uma categoria por id                                         | 404 - quando categoria não encontrada                  |
| GET         | /categories/{id}/videos | obtem todos os videos com pelo id da categoria                      | 404 - quando categoria não encontrada                  |
| POST        | /videos                 | Criar um novo video                                                 | 400 - video ja existe ou falta campos a ser preenchido |
| GET         | /videos                 | Obtem todos os videos                                               | ---                                                    |
| GET         | /videos/{id}            | Obtem um video pelo id                                              | 404 - video não encontrado                             |
| GET         | /videos/?title=value    | Pesquisa videos pelo titulo                                         | ---                                                    |
| PUT         | /videos/{id}            | atualiza video pelo id ou criar video caso não encontrado           | ---                                                    |
| PATCH       | /videos/{id}            | atualiza video pelo id                                              | 404 - video não encontrado                             |
| DELETE      | /videos/{id}            | exclui video pelo id                                                | 404 - video não encontrado                             |

---

### Agradecimentos:

I. Alura
I. Comunidade Java no Discord
