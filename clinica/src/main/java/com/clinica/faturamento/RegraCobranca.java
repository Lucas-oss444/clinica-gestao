package com.clinica.faturamento;

public class RegraCobranca {

    //variaveis de regraCobrança
    private double descontoConvenio;
    private double descontoRetorno;
    private double taxaCancelamento;

    //construtor de regra cobrança
    public RegraCobranca(double descontoConvenio, double descontoRetorno, double taxaCancelamento) {
        this.descontoConvenio = descontoConvenio;
        this.descontoRetorno = descontoRetorno;
        this.taxaCancelamento = taxaCancelamento;
    }

    //aqui ele le o valor e faz a logica para calcular os descontos, o 1 é considerado o valor inteiro - o valor do convenio, apos isso o resultado é o valor que será aplicado para o paciente
    public double aplicarDescontoConvenio(double valor) {
        return valor * (1 - descontoConvenio);
    }
     public double aplicarDescontoRetorno(double valor) {
        return valor * (1 - descontoRetorno);
    }

    //aqui ele retorna o valor que o paciente deve pagar com o  acrescimo da taxa de cancelamento
    public double calcularTaxaCancelamento(double valor) {
        return valor * taxaCancelamento;
    }

    //getters e setters de Regracobrança
    public double getDescontoConvenio() {
        return descontoConvenio;
    }

    public void setDescontoConvenio(double descontoConvenio) {
        this.descontoConvenio = descontoConvenio;
    }

    public double getDescontoRetorno() {
        return descontoRetorno;
    }

    public void setDescontoRetorno(double descontoRetorno) {
        this.descontoRetorno = descontoRetorno;
    }

    public double getTaxaCancelamento() {
        return taxaCancelamento;
    }

    public void setTaxaCancelamento(double taxaCancelamento) {
        this.taxaCancelamento = taxaCancelamento;
    }

}
