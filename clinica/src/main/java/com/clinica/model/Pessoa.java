package com.clinica.model;

//A classe pessoa é uma classe abstrata, pois ela so serve como classe "pai", para as classes profissional e paciente para ambas herdarem nome e idade.
public abstract class Pessoa {

    //variaveis de pessoa
    private String nome;
    private int idade;

    //construtor de pessoa
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    //getters e setters de pessoa
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    
    

}
