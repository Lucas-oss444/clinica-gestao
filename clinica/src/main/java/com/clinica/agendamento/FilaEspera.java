package com.clinica.agendamento;

import com.clinica.model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class FilaEspera {

    //declarando arraylist para objeto paciente
    private List<Paciente> fila;

    //construtor de fila de espera
    public FilaEspera() {
        this.fila = new ArrayList<>();
    }

    //função para adicionar paciente na fila e verificar se é prioritario
    public void adicionarPaciente(Paciente paciente) {
        if (paciente.isPrioridade()) {
            fila.add(0, paciente); // prioritário vai para o início da fila
        } else {
            fila.add(paciente);// normal vai para o final da fila
        }
         System.out.println(paciente.getNome() + " adicionado à fila de espera.");
    }

    //metodo para retornar o objeto paciente
    public Paciente chamarProximo() {
        if (fila.isEmpty()) {
            System.out.println("Fila de espera vazia");
            return null;//verifica se a fila esta vazia
        }else {
        Paciente proximo = fila.remove(0);//apos o paciente do topo da fila ser chamado, ele é removido da fila de espera
        System.out.println("Chamando: " +proximo.getNome());
        return proximo;
        }
    }

   //metodo para exibir a fila de espera
    public void exibirFila() {
        if (fila.isEmpty()) {
            System.out.println("Fila de espera vazia");
            return;//verifica se a fila de espera esta vazia
        }else {
        System.out.println("FILA DE ESPERA");
        for(int i = 0; i < fila.size(); i++){
            Paciente p = fila.get(i);
            String prioridade = p.isPrioridade() ? " [PRIORITÁRIO]" : "[NÃO PRIORITARIO]";//verificando se o paciente é prioritario com "?" servindo como uma versão mais simples de "if"
            System.out.println((i + 1) + ". " + p.getNome() + prioridade);//printando numero da fila e se é prioritario

        }
        }
    }

    //getters para saber o tamanho da fila 
    public int getTamanho() {
        return fila.size();
    }

    //metodo para saber se a fila esta vazia
    public boolean estaVazia() {
        return fila.isEmpty();
    }
}
