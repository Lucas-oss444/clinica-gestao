package com.clinica.model;

public class Sala {

    //variaveis da classe sala
    private int numero;
    private String nome;
    private boolean disponivel;

    //construtor da classe sala
    public Sala(int numero, String nome, boolean disponivel) {
        this.numero = numero;
        this.nome = nome;
        this.disponivel = disponivel;
    }

    //getters e setters da classe sala
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

}
