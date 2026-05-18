package com.clinica.agendamento;

import com.clinica.faturamento.RegraCobranca;
import com.clinica.model.Paciente;
import com.clinica.model.Profissional;
import com.clinica.model.Sala;

//consulta esta herdando as caracteristicas de agendamento
public class Consulta extends Agendamento {

    //variavel de consulta para verificar se o paciente esta retornando de uma consulta
    private boolean retorno;

    public Consulta(String data, String hora, Paciente paciente, Profissional profissional, Sala sala,
            boolean retorno) {
        super(data, hora, paciente, profissional, sala);
        this.retorno = retorno;
    }

    //função de calcular valor com a implementação de regraCobranca para variar os valores das multas
     @Override
    public double calcularValor() {
        
        RegraCobranca regra = new RegraCobranca(0.30, 0.50, 0.20);
        double valor = getProfissional().getValorConsulta();

        if (retorno) {
            return regra.aplicarDescontoRetorno(valor); // 50% de desconto
        } else if (!getPaciente().getConvenio().equalsIgnoreCase("Particular")) {
            return regra.aplicarDescontoConvenio(valor); // 30% de desconto
        }
        return valor; // particular paga valor cheio
    }

    //função de gerar recibo herdada da classe pai agendamento, função essa que agendamento  herdou da interaface cobravel
    @Override
    public String gerarRecibo() {
        
        return "RECIBO DE CONSULTA" + "\nPaciente: " +getPaciente().getNome() + "\nProfissional: " +getProfissional().getNome() + "\nEspecialidade: " + getProfissional().getEspecialidade() + "\nData: " +getData() + "\nHora: " +getHora() + "\nSala: " +getSala().getNome() + "\n Valor: R$" +calcularValor();
    }

    public boolean isRetorno() {
        return retorno;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }
}
