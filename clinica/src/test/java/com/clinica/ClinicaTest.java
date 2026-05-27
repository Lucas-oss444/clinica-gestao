package com.clinica;

import com.clinica.agendamento.Agendamento;
import com.clinica.agendamento.AgendamentoService;
import com.clinica.agendamento.Consulta;
import com.clinica.agendamento.FilaEspera;
import com.clinica.agendamento.Procedimento;
import com.clinica.faturamento.Recibo;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ClinicaTest {

    // objetos usados nos testes
    Paciente silvio;
    Paciente savio;
    Profissional prof1;
    Profissional prof2;
    Sala sala1;
    Sala sala2;
    Consulta consulta;
    Procedimento procedimento;

    // metodo executado antes de cada teste para configurar os objetos
    @BeforeEach
    public void setup() {
        silvio = new Paciente("Silvio", 70, "123.456.789.10", "12343425", "unimed", "pedra no rim", true);
        savio = new Paciente("Savio", 20, "867.334.123.11", "124563456", "unimed", "nenhum", false);

        prof1 = new Profissional("Dra. Ana", 40, "Cardiologia", 200.0, 30);
        prof1.adicionarHorario("08:00");
        prof1.adicionarHorario("09:00");

        prof2 = new Profissional("Dr. Carlos", 50, "Ortopedia", 250.0, 45);
        prof2.adicionarHorario("10:00");
        prof2.adicionarHorario("14:00");

        sala1 = new Sala(7, "sala de cirurgia", false);
        sala2 = new Sala(8, "sala de oftalmologia", true);

        consulta = new Consulta("29/05/2026", "08:00", silvio, prof1, sala1, false);
        procedimento = new Procedimento("29/05/2026", "10:00", savio, prof2, sala2, "Raio-X", 150.0);
    }

    // testa se o valor da consulta com convenio esta correto
    @Test
    public void testCalcularValorConsultaComConvenio() {
        assertEquals(140.0, consulta.calcularValor());
    }

    // testa se o recibo da consulta esta sendo gerado
    @Test
    public void testGerarReciboConsulta() {
        assertTrue(consulta.gerarRecibo().contains("RECIBO DE CONSULTA"));
        assertTrue(consulta.gerarRecibo().contains("Silvio"));
        assertTrue(consulta.gerarRecibo().contains("Dra. Ana"));
    }

    // testa se o valor do procedimento com convenio esta correto
    @Test
    public void testCalcularValorProcedimentoComConvenio() {
        assertEquals(105.0, procedimento.calcularValor());
    }

    // testa se o recibo do procedimento esta sendo gerado
    @Test
    public void testGerarReciboProcedimento() {
        assertTrue(procedimento.gerarRecibo().contains("RECIBO DE PROCEDIMENTO"));
        assertTrue(procedimento.gerarRecibo().contains("Savio"));
    }

    // testa se prioritario vai para o inicio da fila
    @Test
    public void testFilaEsperaPrioridade() {
        FilaEspera fila = new FilaEspera();
        fila.adicionarPaciente(savio);   // não prioritário → vai para o final
        fila.adicionarPaciente(silvio);  // prioritário → vai para o início
        assertEquals("Silvio", fila.chamarProximo().getNome().trim());
    }

    // testa se a fila esta vazia apos chamar todos
    @Test
    public void testFilaEsperaVazia() {
        FilaEspera fila = new FilaEspera();
        assertTrue(fila.estaVazia());
    }

    // testa se o recibo esta sendo emitido corretamente
    @Test
    public void testEmitirRecibo() {
        Recibo recibo = new Recibo(consulta, consulta.calcularValor(), "29/05/2026");
        assertEquals(140.0, recibo.getValorFinal());
    }

    // testa se o agendamento sem conflito e adicionado
    @Test
    public void testAgendamentoSemConflito() {
        List<Agendamento> agendamentos = new ArrayList<>();
        AgendamentoService service = new AgendamentoService(agendamentos);
        assertTrue(service.adicionarAgendamento(consulta));
    }

    // testa se o agendamento com conflito e rejeitado
    @Test
    public void testAgendamentoComConflito() {
        List<Agendamento> agendamentos = new ArrayList<>();
        AgendamentoService service = new AgendamentoService(agendamentos);
        service.adicionarAgendamento(consulta);

        // segundo agendamento com mesmo profissional e horário
        Consulta consulta2 = new Consulta("29/05/2026", "08:00", savio, prof1, sala2, false);
        assertFalse(service.adicionarAgendamento(consulta2));
    }
}