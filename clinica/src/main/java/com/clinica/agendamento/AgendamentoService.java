package com.clinica.agendamento;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clinica.faturamento.Recibo;
import com.clinica.faturamento.RegraCobranca;
import com.clinica.model.Paciente;

public class AgendamentoService {

    private static final int LIMITE_ATIVOS = 10;

    private final List<Agendamento> agendamentos;
    private final FilaEspera filaEspera;
    private final RegraCobranca regraCobranca;

    public AgendamentoService() {
        this.agendamentos = new ArrayList<>();
        this.filaEspera = new FilaEspera();
        this.regraCobranca = new RegraCobranca(0.30, 0.50, 0.20);
    }

    public String agendar(Agendamento novoAgendamento) {
        if (contarAtivos() >= LIMITE_ATIVOS) {
            filaEspera.adicionarPaciente(novoAgendamento.getPaciente());
            return "Limite de 10 agendamentos ativos atingido. Paciente enviado para fila de espera.";
        }

        if (!novoAgendamento.getProfissional().estaDisponivel(novoAgendamento.getHora())) {
            filaEspera.adicionarPaciente(novoAgendamento.getPaciente());
            return "Horário não está na disponibilidade do profissional. Paciente enviado para fila de espera.";
        }

        if (temConflito(novoAgendamento)) {
            filaEspera.adicionarPaciente(novoAgendamento.getPaciente());
            return "Conflito detectado (profissional ou sala já ocupados neste horário). Paciente enviado para fila de espera.";
        }

        agendamentos.add(novoAgendamento);
        novoAgendamento.getProfissional().removerHorario(novoAgendamento.getHora());
        return "Agendamento realizado com sucesso.";
    }

    public String cancelarAgendamento(int indice, boolean foraDoPrazo) {
        if (indice < 0 || indice >= agendamentos.size()) {
            return "Índice inválido.";
        }

        Agendamento agendamento = agendamentos.get(indice);
        if (!"agendado".equalsIgnoreCase(agendamento.getStatus())) {
            return "Somente agendamentos com status AGENDADO podem ser cancelados.";
        }

        agendamento.setStatus("cancelado");
        agendamento.getProfissional().adicionarHorario(agendamento.getHora());

        double taxa = 0.0;
        if (foraDoPrazo) {
            taxa = regraCobranca.calcularTaxaCancelamento(agendamento.calcularValor());
        }

        Paciente proximo = filaEspera.chamarProximo();
        if (proximo != null) {
            return "Agendamento cancelado. Taxa: R$ " + String.format("%.2f", taxa)
                    + ". Horário liberado para fila de espera (próximo: " + proximo.getNome() + ").";
        }

        return "Agendamento cancelado. Taxa: R$ " + String.format("%.2f", taxa) + ".";
    }

    public Recibo finalizarAtendimento(int indice, String dataEmissao) {
        if (indice < 0 || indice >= agendamentos.size()) {
            return null;
        }

        Agendamento agendamento = agendamentos.get(indice);
        if (!"agendado".equalsIgnoreCase(agendamento.getStatus())) {
            return null;
        }

        agendamento.setStatus("finalizado");
        double valorFinal = agendamento.calcularValor();
        return new Recibo(agendamento, valorFinal, dataEmissao);
    }

    public boolean temConflito(Agendamento novoAgendamento) {
        for (Agendamento atual : agendamentos) {
            if (!"agendado".equalsIgnoreCase(atual.getStatus())) {
                continue;
            }

            boolean mesmoDia = atual.getData().equalsIgnoreCase(novoAgendamento.getData());
            boolean mesmaHora = atual.getHora().equalsIgnoreCase(novoAgendamento.getHora());

            if (mesmoDia && mesmaHora) {
                boolean mesmoProfissional = atual.getProfissional().getNome()
                        .equalsIgnoreCase(novoAgendamento.getProfissional().getNome());
                boolean mesmaSala = atual.getSala().getNumero() == novoAgendamento.getSala().getNumero();
                if (mesmoProfissional || mesmaSala) {
                    return true;
                }
            }
        }
        return false;
    }

    public int contarAtivos() {
        int total = 0;
        for (Agendamento agendamento : agendamentos) {
            if ("agendado".equalsIgnoreCase(agendamento.getStatus())) {
                total++;
            }
        }
        return total;
    }

    public int contarCancelamentos() {
        int total = 0;
        for (Agendamento agendamento : agendamentos) {
            if ("cancelado".equalsIgnoreCase(agendamento.getStatus())) {
                total++;
            }
        }
        return total;
    }

    public int contarRetornos() {
        int total = 0;
        for (Agendamento agendamento : agendamentos) {
            if (agendamento instanceof Consulta consulta && consulta.isRetorno()) {
                total++;
            }
        }
        return total;
    }

    public int contarAtendimentosFinalizados() {
        int total = 0;
        for (Agendamento agendamento : agendamentos) {
            if ("finalizado".equalsIgnoreCase(agendamento.getStatus())) {
                total++;
            }
        }
        return total;
    }

    public double calcularReceitaTotal() {
        double total = 0.0;
        for (Agendamento agendamento : agendamentos) {
            if ("finalizado".equalsIgnoreCase(agendamento.getStatus())) {
                total += agendamento.calcularValor();
            }
        }
        return total;
    }

    public Map<String, Double> receitaPorEspecialidade() {
        Map<String, Double> receita = new LinkedHashMap<>();
        for (Agendamento agendamento : agendamentos) {
            if ("finalizado".equalsIgnoreCase(agendamento.getStatus())) {
                String especialidade = agendamento.getProfissional().getEspecialidade();
                double valorAtual = receita.getOrDefault(especialidade, 0.0);
                receita.put(especialidade, valorAtual + agendamento.calcularValor());
            }
        }
        return receita;
    }

    public Map<String, Integer> atendimentosPorProfissional() {
        Map<String, Integer> totalPorProfissional = new LinkedHashMap<>();
        for (Agendamento agendamento : agendamentos) {
            String nome = agendamento.getProfissional().getNome();
            int atual = totalPorProfissional.getOrDefault(nome, 0);
            totalPorProfissional.put(nome, atual + 1);
        }
        return totalPorProfissional;
    }

    public String profissionalMaisDemandado() {
        String nomeMaisDemandado = "Sem dados";
        int maior = 0;

        for (Map.Entry<String, Integer> item : atendimentosPorProfissional().entrySet()) {
            if (item.getValue() > maior) {
                maior = item.getValue();
                nomeMaisDemandado = item.getKey();
            }
        }
        return nomeMaisDemandado;
    }

    public double taxaOcupacao() {
        return (contarAtivos() * 100.0) / LIMITE_ATIVOS;
    }

    public int horariosOciosos() {
        return LIMITE_ATIVOS - contarAtivos();
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public FilaEspera getFilaEspera() {
        return filaEspera;
    }
}
