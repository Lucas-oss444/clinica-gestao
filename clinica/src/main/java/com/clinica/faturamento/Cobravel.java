package com.clinica.faturamento;

//a interface cobravel serve somente para implementar as funções de calculo de valor e recibo de cobrança em agendamento
//posteriormente essas funções serão utilizadas em consulta e procedimento
public interface Cobravel {

    //funções de cobravel
    double calcularValor();
    String gerarRecibo();
    
}
