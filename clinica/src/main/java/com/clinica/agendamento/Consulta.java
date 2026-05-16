package com.clinica.agendamento;

import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

//consulta esta herdando as caracteristicas de agendamento
public class Consulta extends Agendamento {

    //variavel de consulta para verificar se o paciente esta retornando de uma consulta
    private boolean retorno;

    public Consulta(String data, String hora, Paciente paciente, Profissional profissional, Sala sala,
            boolean retorno) {
        super(data, hora, paciente, profissional, sala);
        this.retorno = retorno;
    }

    //calculo para valor, se o paciente estiver retornando de uma consulta o valor da consulta é multiplicado por 0,5 
    // e se caso ele esteja com um plano particular o valor é multiplicado por 0,7
    @Override
    public double calcularValor() {

        double valor = getProfissional().getValorConsulta();
        if (retorno) {
            valor = valor * 0.5;
        }else if (getPaciente().getConvenio().equals("Particular")) {
            valor = valor * 0.7;
        }
        return valor;
    }

    //função de gerar recibo herdada da classe pai agendamento, função essa que agendamento  herdou da interaface cobravel
    @Override
    public String gerarRecibo() {
        
        return "RECIBO DE CONSULTA" + "\nPaciente: " +getPaciente().getNome() + "\nProfissional: " +getProfissional().getNome() + "\nEspecialidade: " + getProfissional().getEspecialidade() + "\nData: " +getData() + "\nHora: " +getHora() + "\nSala: " +getSala().getNome() + "\n Valor: R$" +calcularValor();
    }

    


    

}
