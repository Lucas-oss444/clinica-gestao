package com.clinica.relatorio;

import com.clinica.agendamento.AgendamentoService;

import java.util.Map;

public class RelatorioClinica {

    private AgendamentoService agendamentoService;

    public RelatorioClinica(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    public void gerarRelatorioGeral() {
        System.out.println("\n=============================");
        System.out.println("      RELATÓRIO GERAL        ");
        System.out.println("=============================");
        System.out.println("Atendimentos finalizados: " + agendamentoService.contarAtendimentosFinalizados());
        System.out.println("Cancelamentos: " + agendamentoService.contarCancelamentos());
        System.out.println("Retornos: " + agendamentoService.contarRetornos());
        System.out.println("Horários ociosos: " + agendamentoService.horariosOciosos());
        System.out.println("Taxa de ocupação: " + String.format("%.2f", agendamentoService.taxaOcupacao()) + "%");
        System.out.println("Receita total: R$ " + String.format("%.2f", agendamentoService.calcularReceitaTotal()));
        System.out.println("Profissional mais demandado: " + agendamentoService.profissionalMaisDemandado());
    }

    public void gerarRelatorioPorEspecialidade() {
        System.out.println("\n=============================");
        System.out.println("   RECEITA POR ESPECIALIDADE ");
        System.out.println("=============================");
        Map<String, Double> receita = agendamentoService.receitaPorEspecialidade();
        if (receita.isEmpty()) {
            System.out.println("Sem dados.");
            return;
        }
        for (Map.Entry<String, Double> item : receita.entrySet()) {
            System.out.println("- " + item.getKey() + ": R$ " + String.format("%.2f", item.getValue()));
        }
    }

    public void gerarRelatorioPorProfissional() {
        System.out.println("\n=============================");
        System.out.println(" ATENDIMENTOS POR PROFISSIONAL");
        System.out.println("=============================");
        Map<String, Integer> atendimentos = agendamentoService.atendimentosPorProfissional();
        if (atendimentos.isEmpty()) {
            System.out.println("Sem dados.");
            return;
        }
        for (Map.Entry<String, Integer> item : atendimentos.entrySet()) {
            System.out.println("- " + item.getKey() + ": " + item.getValue() + " atendimento(s)");
        }
    }

    public void gerarRelatorioCompleto() {
        gerarRelatorioGeral();
        gerarRelatorioPorEspecialidade();
        gerarRelatorioPorProfissional();
    }
}