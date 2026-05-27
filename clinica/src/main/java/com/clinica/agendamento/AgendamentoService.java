package com.clinica.agendamento;

import java.util.List;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

//classe que vai gerenciar os conflitos de agendamento
public class AgendamentoService {

    //criação de um ArrayList para armazenar os agendamentos
    private List<Agendamento> agendamentos;

    //construtor de agendamento
    public AgendamentoService(List<Agendamento> agendamentos) {
            this.agendamentos = agendamentos;
    }

    //metodo para verificar o conflito, o metodo é boolean, pois se ouver conflito retorna true, se não ouver conflito retorna false
    //percorrendo o ArrayList de agendamentos ele verifica se a um conflito de um mesmo profissional na data e no horario indicados conforme o objeto dentro do vetor
    //ele tambem verifica a ocupação de uma sala no mesmo horario e dia
    //se ambas verificações anteriores forem falsas o sistema retorna como falso, logo o resultado desse metodo vai ser essencial para os outros metodos
    public boolean verificarConflito(Profissional profissional, Sala sala, String data, String hora) {
        for (Agendamento a : agendamentos) {
            if (a.getData().equals(data) && a.getHora().equals(hora)) {

                // verificação para mesmo profissional no mesmo horário
                if (a.getProfissional().getNome().equalsIgnoreCase(profissional.getNome())) {
                    System.out.println("Conflito: " + profissional.getNome() + " já tem agendamento às " + hora);
                        return true;   
                }

                // verificação mesma sala no mesmo horário
                if (a.getSala().getNome().equalsIgnoreCase(sala.getNome())) {
                    System.out.println("Conflito: " + sala.getNome() + " já está ocupada às " + hora);
                        return true;
                }
            }
        }
        return false;
    }

    // Esse metodo é boolean, pois se ouver conflito o sistema gera uma mensagem de conflito e se não ouver conflito, o sistema registra o agendamento
    //utilizando o metodo anterior de criar verificar conflito, o adicionarAgendamento vai adicionar o agendamento no ArrayList criada anteriormente caso o resultado do metodo de verificação de conflito seja false
    public boolean adicionarAgendamento(Agendamento agendamento) {
        boolean conflito = verificarConflito(agendamento.getProfissional(), agendamento.getSala(), agendamento.getData(), agendamento.getHora());
        if (conflito) {
            System.out.println("Agendamento não realizado por conflito de horário!");
                return false;
        }else {
        agendamentos.add(agendamento);
            System.out.println("Agendamento realizado com sucesso!");
                return true;
        }
    }

    //getter do ArrayList agendamento
    public List<Agendamento>getAgendamentos() {
        return agendamentos;
    }
}