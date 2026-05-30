package com.clinica.persistencia;

import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {

    //caminho para a pasta que vai armazenar os dados
    private static final String PASTA = "dados/";
    private static final String PACIENTES_CSV = PASTA + "pacientes.csv";
    private static final String PROFISSIONAIS_CSV = PASTA + "profissionais.csv";
    private static final String SALAS_CSV = PASTA + "salas.csv";

    //metodo par asalvar os pacientes no csv
    public void salvarPacientes(List<Paciente> pacientes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PACIENTES_CSV))) {
            for (Paciente p : pacientes) {
                writer.println(p.getNome() + "," + p.getIdade() + "," + p.getCpf() + "," +
                               p.getContato() + "," + p.getConvenio() + "," +
                               p.getHistorico() + "," + p.isPrioridade());
            }
            System.out.println("Pacientes salvos!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    //metodo para carregar os pacientes do csv
    public List<Paciente> carregarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        File arquivo = new File(PACIENTES_CSV);
        if (!arquivo.exists()) return pacientes;

        try (BufferedReader reader = new BufferedReader(new FileReader(PACIENTES_CSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] d = linha.split(",");
                pacientes.add(new Paciente(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4], d[5], Boolean.parseBoolean(d[6])));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return pacientes;
    }

    //metodo para carregar profissionais no csv
    public void salvarProfissionais(List<Profissional> profissionais) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PROFISSIONAIS_CSV))) {
            for (Profissional prof : profissionais) {
                writer.println(prof.getNome() + "," + prof.getIdade() + "," +
                               prof.getEspecialidade() + "," + prof.getValorConsulta() + "," +
                               prof.getDuracaoAtendimentos() + "," +
                               String.join(";", prof.getHorariosDisponiveis()));
            }
            System.out.println("Profissionais salvos!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar profissionais: " + e.getMessage());
        }
    }

    //metodo para carregar profissionais do csv
    public List<Profissional> carregarProfissionais() {
        List<Profissional> profissionais = new ArrayList<>();
        File arquivo = new File(PROFISSIONAIS_CSV);
        if (!arquivo.exists()) return profissionais;

        try (BufferedReader reader = new BufferedReader(new FileReader(PROFISSIONAIS_CSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] d = linha.split(",");
                Profissional prof = new Profissional(d[0], Integer.parseInt(d[1]), d[2], Double.parseDouble(d[3]), Integer.parseInt(d[4]));
                if (d.length > 5 && !d[5].isEmpty()) {
                    for (String horario : d[5].split(";")) {
                        prof.adicionarHorario(horario);
                    }
                }
                profissionais.add(prof);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar profissionais: " + e.getMessage());
        }
        return profissionais;
    }

    //metodo para carregar as salas no csv
    public void salvarSalas(List<Sala> salas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALAS_CSV))) {
            for (Sala s : salas) {
                writer.println(s.getNumero() + "," + s.getNome() + "," + s.isDisponivel());
            }
            System.out.println("Salas salvas!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar salas: " + e.getMessage());
        }
    }

    //metodos para carregar as salas do csv
    public List<Sala> carregarSalas() {
        List<Sala> salas = new ArrayList<>();
        File arquivo = new File(SALAS_CSV);
        if (!arquivo.exists()) return salas;

        try (BufferedReader reader = new BufferedReader(new FileReader(SALAS_CSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] d = linha.split(",");
                salas.add(new Sala(Integer.parseInt(d[0]), d[1], Boolean.parseBoolean(d[2])));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar salas: " + e.getMessage());
        }
        return salas;
    }
}