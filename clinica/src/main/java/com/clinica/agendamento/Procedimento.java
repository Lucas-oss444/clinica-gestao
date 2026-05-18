package com.clinica.agendamento;

import com.clinica.faturamento.RegraCobranca;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

// Procedimento esta herdando as caracteristicas de agendamento
public class Procedimento extends Agendamento {

    //variaveis de procedimento para verificar o tipo de procedimento e calcular o valor desse procedimento
    private String tipoProcedimento;
    private double valorProcedimento;

    public Procedimento(String data, String hora, Paciente paciente, Profissional profissional, Sala sala,
            String tipoProcedimento, double valorProcedimento) {
        super(data, hora, paciente, profissional, sala);
        this.tipoProcedimento = tipoProcedimento;
        this.valorProcedimento = valorProcedimento;
    }

    //função de calcular valor com a implementação de regraCobranca para variar os valores das multas
    @Override
    public double calcularValor() {
        RegraCobranca regra = new RegraCobranca(0.30, 0.50, 0.20);
        
        if (!getPaciente().getConvenio().equalsIgnoreCase("Particular")) {
            return regra.aplicarDescontoConvenio(valorProcedimento); // 30% de desconto
        }
        return valorProcedimento; // particular paga valor cheio
    }

    //gerar recibo de procedimento
    @Override
    public String gerarRecibo() {
        return "RECIBO DE PROCEDIMENTO" + "\nPaciente: " +getPaciente().getNome() + "\nProfissional: " +getProfissional().getNome() + "\nEspecialidade: " + getProfissional().getEspecialidade() + "\nData: " +getData() + "\nHora: " +getHora() + "\nSala: " +getSala().getNome() + "\n Valor: R$" +calcularValor();
    }

    //getters de procedimento
    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    public double getValorProcedimento() {
        return valorProcedimento;
    }
}
