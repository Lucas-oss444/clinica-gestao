# Clínica Gestão — Sistema de Gestão de Clínica Multidisciplinar

Sistema desenvolvido em Java para digitalizar os processos de uma clínica multidisciplinar, incluindo cadastro de pacientes e profissionais, agendamentos, faturamento e relatórios analíticos.

---

## Funcionalidades

- Cadastro de pacientes com histórico, convênio e prioridade
- Cadastro de profissionais com especialidade, horários e valor de consulta
- Agendamento de consultas e procedimentos com controle de conflitos
- Fila de espera para pacientes prioritários
- Faturamento com regras de cobrança por tipo de atendimento
- Emissão de recibos detalhados
- Relatórios de ocupação, cancelamentos e receita por especialidade

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Maven** — gerenciamento de dependências
- **JUnit 5** — testes unitários
- **CSV** — persistência de dados

---

## Conceitos de POO Aplicados

| Conceito | Onde é aplicado |
|---|---|
| Herança | `Paciente` e `Profissional` estendem `Pessoa`; `Consulta` e `Procedimento` estendem `Agendamento` |
| Polimorfismo | `calcularValor()` e `gerarRecibo()` implementados de formas diferentes em `Consulta` e `Procedimento` |
| Interface | `Cobravel` implementada por `Agendamento` |
| Encapsulamento | Atributos privados com getters e setters em todas as classes |
| Composição | `Agendamento` compõe `Paciente`, `Profissional` e `Sala` |
| Classe Abstrata | `Pessoa` e `Agendamento` são classes abstratas |

---

## Estrutura do Projeto

```
clinica-gestao/
├── dados/
│   ├── pacientes.csv
│   ├── profissionais.csv
│   └── salas.csv
├── clinica/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/clinica/
│   │   │           ├── model/
│   │   │           │   ├── Pessoa.java
│   │   │           │   ├── Paciente.java
│   │   │           │   ├── Profissional.java
│   │   │           │   └── Sala.java
│   │   │           ├── agendamento/
│   │   │           │   ├── Agendamento.java
│   │   │           │   ├── Consulta.java
│   │   │           │   ├── Procedimento.java
│   │   │           │   ├── FilaEspera.java
│   │   │           │   └── AgendamentoService.java
│   │   │           ├── faturamento/
│   │   │           │   ├── Cobravel.java
│   │   │           │   ├── Recibo.java
│   │   │           │   └── RegraCobranca.java
│   │   │           ├── relatorio/
│   │   │           │   └── RelatorioClinica.java
│   │   │           ├── persistencia/
│   │   │           │   └── CsvHandler.java
│   │   │           └── Main.java
│   │   └── test/
│   │       └── java/
│   │           └── com/clinica/
│   │               └── ClinicaTest.java
│   └── pom.xml
└── README.md
```

---

## Como Executar

### Pré-requisitos

- Java JDK 17 ou superior
- Maven instalado **ou** VS Code com extensão Java Extension Pack

### Clonando o repositório

```bash
git clone https://github.com/Lucas-oss444/clinica-gestao.git
cd clinica-gestao
```

### Executando pelo VS Code

1. Abra a pasta do projeto no VS Code
2. Abra o arquivo `Main.java`
3. Clique no botão **Run ▶️** no canto superior direito

### Executando pelo terminal com Maven

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.clinica.Main"
```

---

## 🧪 Executando os Testes

```bash
mvn test
```

---

## 👥 Integrantes do Grupo

| Nome | GitHub |
|---|---|
| Lucas   | [@Lucas-oss444](https://github.com/Lucas-oss444) |
| Gustavo | [@Galencar14](https://github.com/Galencar14)     |
| Alycia  | [@Alycia-Clara](https://github.com/Alycia-Clara) |
| Marcello| [@Marcello2007](https://github.com/Marcello2007) |

---

## Entrega

Projeto desenvolvido para a disciplina de **Programação Orientada a Objetos**.
Data de apresentação: **29/05/2026**
