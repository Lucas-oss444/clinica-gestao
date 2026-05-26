package com.clinica.agendamento;

import java.util.List;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

public class AgendamentoService {

    private List<Agendamento> agendamentos;

    public AgendamentoService(List<Agendamento> agendamentos) {
            this.agendamentos = agendamentos;
    }

    public boolean verificarConflito(Profissional profissional, Sala sala, String data, String hora) {
        for (Agendamento a : agendamentos) {
            if (a.getData().equals(data) && a.getHora().equals(hora)) {

                // mesmo profissional no mesmo horário
                if (a.getProfissional().getNome().equalsIgnoreCase(profissional.getNome())) {
                    System.out.println("Conflito: " + profissional.getNome() + " já tem agendamento às " + hora);
                        return true;   
                }

                // mesma sala no mesmo horário
                if (a.getSala().getNome().equalsIgnoreCase(sala.getNome())) {
                    System.out.println("Conflito: " + sala.getNome() + " já está ocupada às " + hora);
                        return true;
                }
            }
        }
        return false;
    }

    // adiciona agendamento se não houver conflito
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

    public List<Agendamento>getAgendamentos() {
        return agendamentos;
    }
}