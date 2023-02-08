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
 
1. Criar o usuário e informe a senha desejada:
    - Uma observação, a senha deve ser informado na variável de ambiente **PASSWORD_BD_DEV**

```bash
$ createuser box_videos -D -P
```

2. Criar o banco de dados: 
```bash
$ createdb box_videos -O box_videos
```

## Endpoints disponíveis:

	Uma descrição dos endpoints da API
	
	
	
| HTTP METHOD | PATH | DESCRIÇÃO | POSSÍVEIS ERROS |
|-------------|------|-----------|-----------------|
| POST | /categories | Criar uma nova categoria | 400 - categoria já existente|