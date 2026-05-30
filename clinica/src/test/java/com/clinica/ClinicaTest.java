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

public class ClinicaTest {

    Paciente silvio;
    Paciente savio;
    Profissional prof1;
    Profissional prof2;
    Sala sala1;
    Sala sala2;
    Consulta consulta;
    Procedimento procedimento;
    AgendamentoService service;

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

        service = new AgendamentoService();
    }

    // testa se o valor da consulta com convenio esta correto
    @Test
    public void testCalcularValorConsultaComConvenio() {
        assertEquals(140.0, consulta.calcularValor());
    }

    // testa se o valor do procedimento com convenio esta correto
    @Test
    public void testCalcularValorProcedimentoComConvenio() {
        assertEquals(105.0, procedimento.calcularValor());
    }

    // testa se o recibo da consulta esta sendo gerado
    @Test
    public void testGerarReciboConsulta() {
        assertTrue(consulta.gerarRecibo().contains("RECIBO DE CONSULTA"));
        assertTrue(consulta.gerarRecibo().contains("Silvio"));
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
        fila.adicionarPaciente(savio);
        fila.adicionarPaciente(silvio);
        assertEquals("Silvio", fila.chamarProximo().getNome().trim());
    }

    // testa se a fila esta vazia
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

    // testa se agendamento sem conflito e adicionado
    @Test
    public void testAgendamentoSemConflito() {
        String resposta = service.agendar(consulta);
        assertEquals("Agendamento realizado com sucesso.", resposta);
    }

    // testa se agendamento com conflito e rejeitado
    @Test
    public void testAgendamentoComConflito() {
    service.agendar(consulta); // agenda e remove 08:00 do prof1
    
    // adiciona o horário de volta para simular outro agendamento
    prof1.adicionarHorario("08:00");
    
    // tenta agendar na mesma sala no mesmo horário
    Consulta consulta2 = new Consulta("29/05/2026", "08:00", savio, prof1, sala1, false);
    String resposta = service.agendar(consulta2);
    assertTrue(resposta.contains("Conflito detectado"));
}

    // testa se cancelamento funciona corretamente
    @Test
    public void testCancelarAgendamento() {
        service.agendar(consulta);
        String resposta = service.cancelarAgendamento(0, false);
        assertTrue(resposta.contains("cancelado"));
    }

    // testa se finalizacao funciona corretamente
    @Test
    public void testFinalizarAtendimento() {
        service.agendar(consulta);
        Recibo recibo = service.finalizarAtendimento(0, "29/05/2026");
        assertNotNull(recibo);
        assertEquals(140.0, recibo.getValorFinal());
    }

    // testa se receita total esta sendo calculada corretamente
    @Test
    public void testCalcularReceitaTotal() {
        service.agendar(consulta);
        service.finalizarAtendimento(0, "29/05/2026");
        assertEquals(140.0, service.calcularReceitaTotal());
    }
}