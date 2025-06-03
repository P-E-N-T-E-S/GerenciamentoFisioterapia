# SEGUNDA ENTREGA

## Histórias Implementadas

### H1 - Controle de Materiais
- Cadastro e atualização da quantidade de materiais utilizados em consultas.
- Aviso quando o estoque está baixo.

### H2 - Histórico de Procedimentos
- Armazenamento de dados detalhados de procedimentos.
- Filtros por tipo e período.

### H3 - Ficha Médica
- Ficha clínica com dados detalhados dos pacientes.
- Exportação da ficha em PDF.
- Campo de observações para fisioterapeuta.

### H4 - Cadastro de Pacientes por Médicos Parceiros
- Médicos parceiros podem cadastrar pacientes.
- Notificação para a fisioterapeuta.

### H5 - Alertas de Pagamento Pendente
- Notificações periódicas (6h, 12h e 18h) de pendências de pagamento.
- Visualização do tempo de atraso e justificativas.

### H6 - Lembrete de Agendamento
- Notificações com 1 dia e 1 semana de antecedência sobre consultas agendadas.

### H7 - Lembrete de Pagamento
- Notificações para pacientes sobre vencimento de pagamentos.

### H8 - Calendário Dinâmico
- Visualização mensal e bimestral de agendamentos.
- Reagendamento com justificativa.
- Cancelamento de consultas.
- Classificação por tipo de consulta com cores.

### Extra - Dashboard Financeiro e de Procedimentos
- Visualização de lucros mensais e anuais.
- Procedimentos mais realizados.
- Orçamentos realizados.
- Médicos e clínicas que mais indicam pacientes.

### Em Limbo
#### H4 (Paciente) - Agendamento de Orçamento
- Agendamento e reagendamento de orçamento pelo paciente.

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

### 5. Acessar a Aplicação
- Endereço: [http://localhost:8080](http://localhost:8080)

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
