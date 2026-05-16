package com.clinica.model;

import java.util.ArrayList;
import java.util.List;

//profissional herda de nome e idade de pessoa(pai)
public class Profissional extends Pessoa{

    //variaveis de profissional
    private String especialidade;
    private int duracaoAtendimentos; //em minutos
    private double valorConsulta;
    private List<String> horariosDisponiveis;

    //construtor de profissionais
    public Profissional(String nome, int idade, String especialidade, double valorConsulta, int duracaoAtendimentos) {
        super(nome, idade);
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.duracaoAtendimentos = duracaoAtendimentos;
        this.horariosDisponiveis = new ArrayList<>();
            
      
    }

    //getters e setters de profissional
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public int getDuracaoAtendimentos() {
        return duracaoAtendimentos;
    }

    public void setDuracaoAtendimentos(int duracaoAtendimentos) {
        this.duracaoAtendimentos = duracaoAtendimentos;
    }

        public List<String> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    // adiciona um horário
    public void adicionarHorario(String horario) {
        horariosDisponiveis.add(horario);
    }

    // remove um horário quando agendado
    public void removerHorario(String horario) {
        horariosDisponiveis.remove(horario);
    }

    // verifica se está disponível
    public boolean estaDisponivel(String horario) {
        return horariosDisponiveis.contains(horario);
    }



    

    
    

}
