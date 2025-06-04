# SEGUNDA ENTREGA

## Histórias Implementadas

### H1 - Controle de Materiais
- Cadastro e atualização da quantidade de materiais utilizados em consultas.
- Sinalização da quantidade de materiais armazenados.

### H2 - Histórico de Consultas
- Armazenamento de dados detalhados de consultas.
- Filtros para listagem de consultas (Hoje, Futuras, Histórico Geral).

### H3 - Ficha Médica
- Ficha médica com dados detalhados dos pacientes.
- Exportação da ficha em PDF.
- Campo de observações para fisioterapeuta.

### H4 - Cadastro de Pacientes por Médicos Parceiros
- Médicos parceiros podem cadastrar pacientes.

### H5 - Controle Financeiro
- Exibição de Faturamento Total.
- Sinalização de pagamento pendente de consulta.

### H6 - Lembrete de Pagamento (EmailService.java)
- Notificações via e-mail para pacientes sobre vencimento de pagamentos.

### H7 - Calendário Dinâmico
- Visualização mensal de agendamentos.
- Sinalização de dias do mês que possuem consultas.

### H8 - Dashboard Financeiro e de Materiais
- Orçamentos por consulta.
- Gráfico de barras exibindo quantidade de materiais armazenados.

### H9 - Busca e Filtro de Pacientes
- Buscar pacientes por nome e filtrar por status ou outros critérios na página de Pacientes.

### H10 - Lembrete Consultas do Dia
- Card que exibe consultas agendadas para o dia atual, com nome do paciente e horário.



---

## Padrões de Projeto Utilizados

### Observer Pattern
- `NotificacaoService`
- `AgendamentoObserver`
- `ConsoleObserver`
- `NotificacaoSubject`

### Iterator Pattern
- `ProcedimentoIterator`
- `ProcedimentoPorDataIterator`
- `ProcedimentoPorTipoIterator`

### Strategy Pattern
- `EstrategiaPorFiltroProcedimento`
- `EstrategiaOrganizacaoCalendario`

### Template Method Pattern
- `FichaMedica`
- `FichaMedicaImplanta`

---

## Como Executar o Projeto

### 1. Pré-requisitos
- Java 21
- Maven
- Docker e Docker Compose
- NodeJs

### 2. Subir as Dependências
```bash
docker-compose up -d
```
- **MySQL**: Porta `3307`
- **RabbitMQ**: Porta `5672` (Interface de gerenciamento: `http://localhost:15672`)

### 3. Compilar o Projeto
```bash
mvn clean install
```

### 4. Rodar a Aplicação
```bash
mvn spring-boot:run -pl dominio
```

### 5. Acessar a API
- Endereço: [http://localhost:8080](http://localhost:8080)

## Executar Frontend

### 7. Acesse pasta /frontend

### 8. Instale as dependências
```bash
npm install
```

### 9. Inicie a Aplicação React
```bash
npm run start
```

### 10. Acessar o Frontend
- Endereço: [http://localhost:3000](http://localhost:3000)

---

## Informações Adicionais

### Banco de Dados MySQL
- Host: `localhost`
- Porta: `3307`
- Usuário: `fisio`
- Senha: `fisio`
- Banco: `Fisio`

### RabbitMQ
- Interface: [http://localhost:15672](http://localhost:15672)
- Usuário: `guest`
- Senha: `guest`

### Observações
1. Verifique se as portas `3307`, `5672` e `15672` estão livres.
2. Caso haja erro de conexão com o banco, verifique se o contêiner do MySQL está em execução.
3. O projeto é modularizado (ex: `dominio`, `infraestrutura`, `persistencia`).
