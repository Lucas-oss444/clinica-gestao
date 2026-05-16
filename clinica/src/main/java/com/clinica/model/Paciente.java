package com.clinica.model;

//paciente herda nome e idade de pessoa(pai)
public class Paciente extends Pessoa{

    //variaveis de paciente
    private String cpf;
    private String contato;
    private String convenio;
    private String historico;
    private boolean prioridade;
    
    //construtor de paciente
    public Paciente(String nome, int idade, String cpf, String contato, String convenio, String historico, boolean prioridade) {
        super(nome, idade);
        this.cpf = cpf;
        this.contato = contato;
        this.convenio = convenio;
        this.historico = historico;
        this.prioridade = prioridade;
    }

    //getters e stters de paciente
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public boolean isPrioridade() {
        return prioridade;
    }

    public void setPrioridade(boolean prioridade) {
        this.prioridade = prioridade;
    }
    

    

    

}
