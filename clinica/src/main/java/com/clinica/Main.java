package com.clinica;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.clinica.agendamento.Agendamento;
import com.clinica.agendamento.AgendamentoService;
import com.clinica.agendamento.Consulta;
import com.clinica.agendamento.Procedimento;
import com.clinica.faturamento.Recibo;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;
import com.clinica.persistencia.CsvHandler;
import com.clinica.relatorio.RelatorioClinica;

public class Main {
    public static void main(String[] args) {
        List<Paciente> pacientes = new ArrayList<>();
        List<Profissional> profissionais = new ArrayList<>();
        List<Sala> salas = new ArrayList<>();
        AgendamentoService agendamentoService = new AgendamentoService();

        CsvHandler csv = new CsvHandler();
        pacientes = csv.carregarPacientes();
        profissionais = csv.carregarProfissionais();
        salas = csv.carregarSalas();

if (pacientes.isEmpty() || profissionais.isEmpty() || salas.isEmpty()) {
    carregarDadosIniciais(pacientes, profissionais, salas);
}

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            mostrarMenu();
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
            opcao = scanner.nextInt();
            scanner.nextLine();
            } else {
            System.out.println("Opção inválida.");
            scanner.nextLine();
    }

            switch (opcao) {
                case 1:
                    System.out.println("");
                    cadastrarPaciente(scanner, pacientes);
                    break;
                case 2:
                    cadastrarProfissional(scanner, profissionais);
                    break;
                case 3:
                    agendarConsulta(scanner, pacientes, profissionais, salas, agendamentoService);
                    break;
                case 4:
                    agendarProcedimento(scanner, pacientes, profissionais, salas, agendamentoService);
                    break;
                case 5:
                    listarAgendamentos(agendamentoService.getAgendamentos());
                    break;
                case 6:
                    cancelarAgendamento(scanner, agendamentoService);
                    break;
                case 7:
                    finalizarAtendimento(scanner, agendamentoService);
                    break;
                case 8:
                    agendamentoService.getFilaEspera().exibirFila();
                    break;
                case 9:
                    exibirRelatorio(agendamentoService);
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        csv.salvarPacientes(pacientes);
        csv.salvarProfissionais(profissionais);
        csv.salvarSalas(salas);
        System.out.println("Dados salvos com sucesso!");

        scanner.close();
    }

    private static void carregarDadosIniciais(List<Paciente> pacientes, List<Profissional> profissionais,
            List<Sala> salas) {
        pacientes.add(new Paciente("Silvio ", 70, "123.456.789.10", "12343425", "unimed", "pedra no rim", true));
        pacientes.add(new Paciente("Savio ", 20, "867.334.123.11", "124563456", "unimed", "nenhum", false));

        Profissional prof1 = new Profissional("Dra. Ana", 40, "Cardiologia", 200.0, 30);
        prof1.adicionarHorario("08:00");
        prof1.adicionarHorario("09:00");
        profissionais.add(prof1);

        Profissional prof2 = new Profissional("Dr. Carlos", 50, "Ortopedia", 250.0, 45);
        prof2.adicionarHorario("10:00");
        prof2.adicionarHorario("14:00");
        profissionais.add(prof2);

        salas.add(new Sala(7, "sala de cirurgia", false));
        salas.add(new Sala(8, "sala de oftalmologia", true));
    }

    private static void mostrarMenu() {
        System.out.println("\n   CLÍNICA - MENU   ");
        System.out.println("1- Cadastrar paciente");
        System.out.println("2- Cadastrar profissional");
        System.out.println("3- Agendar consulta");
        System.out.println("4- Agendar procedimento");
        System.out.println("5- Listar agendamentos");
        System.out.println("6- Cancelar agendamento");
        System.out.println("7- Finalizar atendimento");
        System.out.println("8- Mostrar fila de espera");
        System.out.println("9- Relatório resumido");
        System.out.println("0- Sair");
    }

    private static void cadastrarPaciente(Scanner scanner, List<Paciente> pacientes) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("CPF/código: ");
        String cpf = scanner.nextLine();
        System.out.print("Contato: ");
        String contato = scanner.nextLine();
        System.out.print("Convênio (ou Particular): ");
        String convenio = scanner.nextLine();
        System.out.print("Histórico básico: ");
        String historico = scanner.nextLine();
        System.out.print("Prioridade? (s/n): ");
        boolean prioridade = scanner.nextLine().trim().equalsIgnoreCase("s");

        pacientes.add(new Paciente(nome, idade, cpf, contato, convenio, historico, prioridade));
        System.out.println("Paciente cadastrado com sucesso");
    }

    private static void cadastrarProfissional(Scanner scanner, List<Profissional> profissionais) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();
        System.out.print("Valor da consulta: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Duração padrão (minutos): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        Profissional profissional = new Profissional(nome, idade, especialidade, valor, duracao);
        System.out.print("Quantos horários disponíveis deseja cadastrar agora? ");
        int qtdHorarios = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < qtdHorarios; i++) {
            System.out.print("Horário " + (i + 1) + " (ex: 08:00): ");
            profissional.adicionarHorario(scanner.nextLine());
        }

        profissionais.add(profissional);
        System.out.println("Profissional cadastrado com sucesso");
    }

    private static void agendarConsulta(
            Scanner scanner,
            List<Paciente> pacientes,
            List<Profissional> profissionais,
            List<Sala> salas,
            AgendamentoService agendamentoService) {

        if (pacientes.isEmpty() || profissionais.isEmpty() || salas.isEmpty()) {
            System.out.println("Cadastre paciente, profissional e sala antes de agendar");
            return;
        }

        Paciente paciente = escolherPaciente(scanner, pacientes);
        Profissional profissional = escolherProfissional(scanner, profissionais);

        Sala sala = escolherSala(scanner, salas);

        if (paciente == null || profissional == null || sala == null) {
            System.out.println("Seleção inválida.");
            return;
        }

        System.out.print("Data (dd/MM/yyyy): ");
        String data = scanner.nextLine();
        System.out.print("Hora (HH:mm): ");
        String hora = scanner.nextLine();
        System.out.print("É retorno? (s/n): ");
        boolean retorno = scanner.nextLine().trim().equalsIgnoreCase("s");

        Consulta consulta = new Consulta(data, hora, paciente, profissional, sala, retorno);
        String resposta = agendamentoService.agendar(consulta);
        System.out.println(resposta);
    }

    private static void agendarProcedimento(
            Scanner scanner,
            List<Paciente> pacientes,
            List<Profissional> profissionais,
            List<Sala> salas,
            AgendamentoService agendamentoService) {

        if (pacientes.isEmpty() || profissionais.isEmpty() || salas.isEmpty()) {
            System.out.println("Cadastre paciente, profissional e sala antes de agendar.");
            return;
        }

        Paciente paciente = escolherPaciente(scanner, pacientes);
        Profissional profissional = escolherProfissional(scanner, profissionais);
        Sala sala = escolherSala(scanner, salas);

        if (paciente == null || profissional == null || sala == null) {
            System.out.println("Seleção inválida");
            return;
        }

        System.out.print("Data (dd/MM/yyyy): ");
        String data = scanner.nextLine();
        System.out.print("Hora (HH:mm): ");
        String hora = scanner.nextLine();
        System.out.print("Tipo do procedimento: ");
        String tipoProcedimento = scanner.nextLine();
        System.out.print("Valor do procedimento: ");
        double valorProcedimento = Double.parseDouble(scanner.nextLine());

        Procedimento procedimento = new Procedimento(
                data,
                hora,
                paciente,
                profissional,
                sala,
                tipoProcedimento,
                valorProcedimento);

        String resposta = agendamentoService.agendar(procedimento);
        System.out.println(resposta);
    }

    private static Paciente escolherPaciente(Scanner scanner, List<Paciente> pacientes) {
        System.out.println("\nPacientes:");
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            System.out.println(i + "  " + paciente.getNome() + "  " + paciente.getCpf() + " ");
        }
        System.out.print("Escolha o índice do paciente: ");
        int indice = Integer.parseInt(scanner.nextLine());
        if (indice < 0 || indice >= pacientes.size()) {
            return null;
        }
        return pacientes.get(indice);
    }

    private static Profissional escolherProfissional(Scanner scanner, List<Profissional> profissionais) {
        System.out.println("\nProfissionais:");
        for (int i = 0; i < profissionais.size(); i++) {
            Profissional profissional = profissionais.get(i);
            System.out.println(i + " - " + profissional.getNome() + " / " + profissional.getEspecialidade()
                    + " / horários: " + profissional.getHorariosDisponiveis());
        }
        System.out.print("Escolha o índice do profissional: ");
        int indice = Integer.parseInt(scanner.nextLine());
        if (indice < 0 || indice >= profissionais.size()) {
            return null;
        }
        return profissionais.get(indice);
    }

    private static Sala escolherSala(Scanner scanner, List<Sala> salas) {
        System.out.println("\nSalas:");
        for (int i = 0; i < salas.size(); i++) {
            Sala sala = salas.get(i);
            System.out.println(i + " - " + sala.getNome() + " (número " + sala.getNumero() + ")");
        }
        System.out.print("Escolha o índice da sala: ");
        int indice = Integer.parseInt(scanner.nextLine());
        if (indice < 0 || indice >= salas.size()) {
            return null;
        }
        return salas.get(indice);
    }

    private static void listarAgendamentos(List<Agendamento> agendamentos) {
        if (agendamentos.isEmpty()) {
            System.out.println("nenhum agendamento encontrado ;)");
            return;
        }

        System.out.println("\n    AGENDAMENTOS    ");
        for (int i = 0; i < agendamentos.size(); i++) {
            Agendamento agendamento = agendamentos.get(i);
            System.out.println(i + " - " + agendamento.getClass().getSimpleName()
                    + " | Paciente: " + agendamento.getPaciente().getNome()
                    + " | Profissional: " + agendamento.getProfissional().getNome()
                    + " | Data: " + agendamento.getData()
                    + " | Hora: " + agendamento.getHora()
                    + " | Sala: " + agendamento.getSala().getNumero()
                    + " | Status: " + agendamento.getStatus());
        }
    }

    private static void cancelarAgendamento(Scanner scanner, AgendamentoService agendamentoService) {
        listarAgendamentos(agendamentoService.getAgendamentos());
        if (agendamentoService.getAgendamentos().isEmpty()) {
            return;
        }

        System.out.print("Digite o índice para cancelar: ");
        int indice = Integer.parseInt(scanner.nextLine());
        System.out.print("Cancelamento está dentro ou fora do prazo? (s/n): ");
        boolean foraDoPrazo = scanner.nextLine().trim().equalsIgnoreCase("s");

        String resposta = agendamentoService.cancelarAgendamento(indice, foraDoPrazo);
        System.out.println(resposta);
    }

    private static void finalizarAtendimento(Scanner scanner, AgendamentoService agendamentoService) {
        listarAgendamentos(agendamentoService.getAgendamentos());
        if (agendamentoService.getAgendamentos().isEmpty()) {
            return;
        }

        System.out.print("Digite o índice para finalizar: ");
        int indice = Integer.parseInt(scanner.nextLine());
        System.out.print("Data de emissão do recibo: ");
        String dataEmissao = scanner.nextLine();

        Recibo recibo = agendamentoService.finalizarAtendimento(indice, dataEmissao);
        if (recibo == null) {
            System.out.println("Não foi possível finalizar ");
            return;
        }
        recibo.emitirRecibo();
    }

    private static void exibirRelatorio(AgendamentoService agendamentoService) {
        RelatorioClinica relatorio = new RelatorioClinica(agendamentoService);
            relatorio.gerarRelatorioCompleto();
    }
}