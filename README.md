# DSCommerce

<div align="center">
 <h2> Sumário</h2>
  <a href="#visao">Visão geral do sistema</a> -
  <a href="#modelo-conceitual">Modelo conceitual</a> - 
  <a href="#caso-de-uso-geral">Casos de uso (visão geral)</a> - 
  <a href="#caso-de-uso-detalhamento">Casos de uso (detalhamento)</a> -
  <a href="#tec">Tecnologias utilizadas</a> -
  <a href="#inst-uso">Instruções de uso</a>
</div>

## [Visão](visao)
O sistema deve manter um cadastro de usuário, produtos e suas categorias. Cada
usuário possui nome, email, telefone, data de nascimento e uma senha de acesso. Os
dados dos produtos são: nome, descrição, preço e imagem. O sistema deve apresentar
um catálogo de produtos, os quais podem ser filtrados pelo nome do produto. A partir
desse catálogo, o usuário pode selecionar um produto para ver seus detalhes e para
decidir se o adiciona a um carrinho de compras. O usuário pode incluir e remover itens
do carrinho de compra, bem como alterar as quantidades de cada item. Uma vez que o
usuário decida encerrar o pedido, o pedido deve então ser salvo no sistema com o status
de "aguardando pagamento". Os dados de um pedido são: instante em que ele foi salvo,
status, e uma lista de itens, onde cada item se refere a um produto e sua quantidade no
pedido. O status de um pedido pode ser: aguardando pagamento, pago, enviado,
entregue e cancelado. Quando o usuário paga por um pedido, o instante do pagamento
deve ser registrado. Os usuários do sistema podem ser clientes ou administradores,
sendo que todo usuário cadastrado por padrão é cliente. Usuários não identificados
podem se cadastrar no sistema, navegar no catálogo de produtos e no carrinho de
compras. Clientes podem atualizar seu cadastro no sistema, registrar pedidos e visualizar
seus próprios pedidos. Usuários administradores tem acesso à área administrativa onde
pode acessar os cadastros de usuários, produtos e categorias.

## [Modelo Conceitual](modelo-conceitual)
- Cada item de pedido (OrderItem) corresponde a um produto no pedido, com uma
  quantidade. Sendo que o preço também é armazenado no item de pedido por
  questões de histórico (se o preço do produto mudar no futuro, o preço do item de
  pedido continua registrado com o preço real que foi vendido na época).
- Um usuário pode ter um ou mais "roles", que são os perfis de acesso deste usuário
  no sistema (client, admin).
  ![Modelo-conceitual](/img/modelo-conceitual.png)

## [Casos de uso (visão geral)](caso-de-uso-geral)
O escopo funcional do sistema consiste nos seguintes casos de uso:

| Caso de uso | Visão geral                                                                                             | Acesso |
|-------------|---------------------------------------------------------------------------------------------------------|-------------|
| Manter produtos | CRUD de produtos, podendo filtrar itens pelo nome                                                       | Somente Admin |
| Manter categorias | CRUD de categorias, podendo filtrar itens pelo nome                                                     | Somente Admin |
| Manter usuários | CRUD de usuários, podendo filtrar itens pelo nome                                                       | Somente Admin |
| Gerenciar carrinho | Incluir e remover itens do carrinho de compras, bem como alterar as quantidades do produto em cada item | Público |
| Consultar catálogo | Listar produtos disponíveis, podendo filtrar produtos pelo nome                                         | Público |
| Sign up | Cadastrar-se no sistema                                                                                 | Público |
| Login | Efetuar login no sistema                                                                                | Público |
| Registrar pedido | Salvar no sistema um pedido a partir dos dados do carrinho de compras informado                         | Usuário logado |
| Atualizar perfil | Atualizar o próprio cadastro                                                                            | Usuário logado |
| Visualizar pedidos | Visualizar os pedidos que o próprio usuário já fez                                                      | Usuário logado |
| Registrar pagamento | Salvar no sistema os dados do pagamento de um pedido                                                    | Usuário logado |
| Reportar pedidos | Relatório de pedidos, podendo ser filtrados por data                                                    | Somente Admin |
![Atores](/img/atores.png)
### Atores
| Ator | Responsabilidade                                                                                                                                               |
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Usuário anônimo | Pode realizar casos de uso das áreas públicas do sistema, como catálogo, carrinho de compras, login e sign up.                                                 |
| Cliente | Responsável por manter seu próprios dados pessoais no sistema, e pode visualizar histórico dos seus pedidos. Todo usuário  cadastrado por padrão é um Cliente. | 
| Admin | Responsável por acessar a área administrativa do sistema com cadastros e relatórios. Admin também pode fazer tudo que Cliente faz.                             |

## [Casos de uso (detalhamento)](caso-de-uso-detalhamento)
### Consultar catálogo
| Atores       | Usuário anônimo, Cliente, Admin                                  |
|--------------|------------------------------------------------------------------|
| Precondições | -                                                                |
| Precondições | -                                                                | 
| Visão geral  | Listar produtos disponíveis, podendo filtrar produtos pelo nome  |

| Cenário principal de sucesso                                                                                                                                                               | 
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. [OUT] O sistema informa uma listagem paginada de nome, imagem e preço dos produtos, ordenada por nome.<br/> 2. [IN] O usuário informa, opcionalmente, parte do nome de um produto<br/> 3. [OUT] O sistema informa a listagem atualizada | 

| Informações complementares                                                                                                    | 
|-------------------------------------------------------------------------------------------------------------------------------|
| O número padrão de registros por página deve ser 12. Como a listagem é paginada, o usuário pode navegar nas próximas páginas. | 

### Manter produtos
| Atores       | Admin                                             |
|--------------|---------------------------------------------------|
| Precondições | Usuário logado                                    |
| Precondições | -                                                 | 
| Visão geral  | CRUD de produtos, podendo filtrar itens pelo nome |

| Cenário principal de sucesso                                                                                                                                               | 
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. Executar caso de uso: Consultar catálogo.<br/>2. O admin seleciona uma das opções<br/>  2.1. Variante Inserir<br/>  2.2. Variante Atualizar<br/>  2.3. Variante Deletar | 

| Cenário alternativos: variantes                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | 
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 2.1. Variante Inserir <br/>2.1.1. [IN] O admin informa nome, preço, descrição e URL da imagem e categorias do  produto. <br/><br/> 2.2. Variante Atualizar <br/>  2.2.1. [IN] O admin seleciona um produto para editar. <br/> 2.2.2. [OUT] O sistema informa nome, preço, descrição, URL da imagem e categorias do  produto selecionado. <br/> 2.2.3. [IN] O admin informa novos valores para nome, preço, descrição, URL da imagem  e categorias do produto selecionado. <br/><br/> 2.3. Variante Deletar <br/> 2.3.1. [IN] O admin seleciona um produto para deletar. | 

| Cenário alternativos: exceções                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | 
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 2.1.1a. Dados inválidos <br/>2.1.1a.1. [OUT] O sistema informa os erros nos campos inválidos [1].<br/>  2.1.1a.2. Vai para passo 2.1.1.<br/><br/>  2.2.3a. Dados inválidos<br/>  2.2.3a.1. [OUT] O sistema informa os erros nos campos inválidos [1]. <br/> 2.2.3a.2. Vai para passo 2.2.1. <br/> 2.2.3b. Id não encontrado <br/> 2.2.3b.1. [OUT] O sistema informa que o id não existe. <br/> 2.2.3b.2. Vai para passo 2.2.1. <br/><br/> 2.3.1a. Id não encontrado <br/> 2.3.1a.1. [OUT] O sistema informa que o id não existe. <br/> 2.3.1a.2. Vai para passo 2.3.1. <br/><br/> 2.3.1b. Integridade referencial violada <br/> 2.3.1b.1. [OUT] O sistema informa que a deleção não pode ser feita porque viola a  integridade referencial dos dados. <br/> 2.3.1b.2. Vai para passo 2.3.1. | 

| Informações complementares                                                                                                                                                         | 
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [1] Validação dos dados:<br/> - Nome: deve ter entre 3 e 80 caracteres<br/> - Preço: deve ser positivo<br/> - Descrição: não pode ter menos que 10 caracteres<br/> - Deve haver pelo menos 1 categoria | 

### Login
| Atores       | Usuário anônimo          |
|--------------|--------------------------|
| Precondições | -                        |
| Precondições | Usuário logado           | 
| Visão geral  | Efetuar login no sistema |

| Cenário principal de sucesso                                                                                        | 
|---------------------------------------------------------------------------------------------------------------------|
| 1. [IN] O usuário anônimo informa suas credenciais (nome e senha).<br/> 2. [OUT] O sistema informa um token válido. | 

| Cenário alternativos: exceções                                                                                           | 
|--------------------------------------------------------------------------------------------------------------------------|
| 1a. Credenciais inválidas <br/> 1a.1. [OUT] O sistema informa que as credenciais são inválidas. <br/> 1a.2. Vai para passo 1. | 

### Gerenciar carrinho
| Atores       | Usuário anônimo                                                                                         |
|--------------|---------------------------------------------------------------------------------------------------------|
| Precondições | -                                                                                                       |
| Precondições | -                                                                                                       | 
| Visão geral  | Incluir e remover itens do carrinho de compras, bem como alterar as quantidades do produto em cada item |

| Cenário principal de sucesso                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 | 
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. Executar caso de uso: **Consultar catálogo**.<br/> 2. [IN] O Usuário anônimo seleciona um produto. <br/> 3. [OUT] O sistema informa nome, preço, descrição, imagem, e nomes das categorias  do produto selecionado. <br/> 4. [IN] O Usuário anônimo informa que deseja adicionar o produto ao carrinho. <br/> 5. [OUT] O sistema informa os dados do carrinho de compras [1]. <br/> 6. [IN] O Usuário anônimo informa, opcionalmente, que deseja incrementar ou decrementar a quantidade de um item no carrinho de compras. <br/> 7. [ OUT] O sistema informa os dados atualizados do carrinho de compras [1]. | 

| Informações complementares                                                                                                                                                                                                    | 
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [1] Dados do carrinho de compras:<br/>- lista de itens de carrinho <br/>- valor total do carrinho de compras<br/> Dados do item de carrinho:<br/>- id do produto<br/> - nome do produto<br/> - preço do produto<br/> - quantidade<br/> - subtotal | 

### Registrar pedido
| Atores       | Cliente                                                                          |
|--------------|----------------------------------------------------------------------------------|
| Precondições | - Usuário logado<br/>- Carrinho de compras não vazio                             |
| Precondições | Carrinho de compras vazio                                                        | 
| Visão geral  | Salvar no sistema um pedido a partir dos dados do carrinho de compras informado. |

| Cenário principal de sucesso                                                                                                                   | 
|------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. [IN] O cliente informa os dados do carrinho de compras [1].<br/> 2. [OUT] O sistema informa os dados do carrinho de compras[1] e o id do pedido. | 

| Informações complementares                                                                                                    | 
|-------------------------------------------------------------------------------------------------------------------------------|
| [1] Dados do carrinho de compras: vide caso de uso Gerenciar carrinho. | 

## [Tecnologias utilizadas](tecnologias-utilizadas)
As seguintes tecnologias foram utilizadas durante o desenvolvimento do sistema:
- Java 21
- Spring Boot
- Spring Data JPA
- H2
- Bean Validation
- Spring Security
- Maven

## [Instruções de Uso](inst-uso)
Para prosseguir com o passo a passo, é necessário que você tenha em sua máquina o Java 21, git e Intellij.

### Instalação
Abra o terminal, navegue até o diretório Desktop e rode o comando:
```bash
# Clone o repositório
git clone https://github.com/LucasFrancoBN/dscommerce.git
```
Agora basta você abrir o projeto na IDE Intellij, acessar o arquivo ```DscommerceApplication``` e rodar o projeto.
