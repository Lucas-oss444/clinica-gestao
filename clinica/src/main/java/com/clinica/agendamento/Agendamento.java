package com.clinica.agendamento;

import com.clinica.faturamento.Cobravel;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

//agendamento é abstrato, pois ele serve como classe pai para a consulta e procedimento e implementa os metodos da interface cobravel
public abstract class Agendamento implements Cobravel{

    //variaveis de agendamento
    private String data;
    private String hora;
    private String status;
    private Paciente paciente;
    private Profissional profissional;
    private Sala sala;

    //construtor de agendamento
    public Agendamento(String data, String hora, Paciente paciente, Profissional profissional,
            Sala sala) {
        this.data = data;
        this.hora = hora;
        this.status = "agendado";
        this.paciente = paciente;
        this.profissional = profissional;
        this.sala = sala;
    }

    //getters e setters de agendamento
    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getStatus() {
        return status;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Sala getSala() {
        return sala;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //funções de agendamento implementados da interface cobravel
    public abstract double calcularValor();
    public abstract String gerarRecibo();

    

}
