package com.clinica.faturamento;

import com.clinica.agendamento.Agendamento;

//essa classe é responsavel por representar o recibo gerado apos um atendimento
public class Recibo {

    //variaveis de recibo
    private Agendamento agendamento;
    private double valorFinal;
    private String dataEmissao;

    //construtor de recibo
    public Recibo(Agendamento agendamento, double valorFinal, String dataEmissao) {
        this.agendamento = agendamento;
        this.valorFinal = valorFinal;
        this.dataEmissao = dataEmissao;
    }

    //metodo para exibir o recibo
    public void emitirRecibo() {
        System.out.println("=== RECIBO ===");
        System.out.println("Paciente: " +agendamento.getPaciente().getNome());
        System.out.println("Profissional: " +agendamento.getProfissional().getNome());
        System.out.println("Data do atendimento: " +agendamento.getData());
        System.out.println("Hora: " +agendamento.getHora());
        System.out.println("Sala: " +agendamento.getSala().getNome());
        System.out.println("Data de emissão: " +dataEmissao);
        System.out.println("Valor final: R$ " +valorFinal);
        System.out.println("==============");
    }

    //getters e setters das variaveis de recibo
    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }
}
