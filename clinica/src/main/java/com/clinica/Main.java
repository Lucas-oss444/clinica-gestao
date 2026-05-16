package com.clinica;

import java.util.ArrayList;
import java.util.List;

import com.clinica.agendamento.Consulta;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

public class Main {
    public static void main(String[] args) {
       
        //lista para guardar varios pacientes e profissionais para testes iniciais
        List<Paciente> pacientes = new ArrayList<>();
        List<Profissional> profissionais = new ArrayList<>();

        pacientes.add(new Paciente("Silvio ", 70, "123.456.789.10", "12343425", "unimed", "pedra no rim", true));
        pacientes.add(new Paciente("Savio ", 20, "867.334.123.11", "124563456", "unimed", "nenhum", false));

        //adicionando profissionais para testes iniciais
        Profissional prof1 = new Profissional("Dra. Ana", 40, "Cardiologia", 200.0, 30);
        prof1.adicionarHorario("08:00");
        prof1.adicionarHorario("09:00");
        profissionais.add(prof1);

        Profissional prof2 = new Profissional("Dr. Carlos", 50, "Ortopedia", 250.0, 45);
        prof2.adicionarHorario("10:00");
        prof2.adicionarHorario("14:00");
        profissionais.add(prof2);

        //adicionando salas para testes iniciais
        List<Sala> salas = new ArrayList<>();
        salas.add(new Sala(7, "sala de cirurgia", false));
        salas.add(new Sala(8, "sala de oftalmologia", true));

        //printando informações para testes iniciais
        System.out.println("=== SALAS ===");
        for (Sala s : salas) {
            System.out.println(s.getNome() + " disponivel: " + s.isDisponivel());
        }

        System.out.println("=== PACIENTES ===");
        for (Paciente p : pacientes) {
            System.out.println(p.getNome() + " - " + p.getCpf());
        }

         System.out.println("\n=== PROFISSIONAIS ===");
        for (Profissional prof : profissionais) {
            System.out.println(prof.getNome() + " - " + prof.getEspecialidade());
            System.out.println("Horários: " + prof.getHorariosDisponiveis());
        }

        // Criando uma consulta
        Consulta consulta = new Consulta("29/05/2026", "08:00", pacientes.get(0), profissionais.get(0), salas.get(0), false);

        // Testando
        System.out.println(consulta.gerarRecibo());
        System.out.println("Valor: R$ " + consulta.calcularValor());








    }
}