package grupo4.backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

import grupo4.backend.models.enums.Prioridade;
import grupo4.backend.models.enums.StatusEmergencia;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class AtendimentoEmergencia extends Atendimento {

    private LocalDate data;
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private StatusEmergencia statusEmergencia;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissionalResponsavel;

    protected AtendimentoEmergencia() {}

    public AtendimentoEmergencia(
            LocalDate data,
            LocalTime horario,
            Prioridade prioridade,
            Paciente paciente,
            ProfissionalSaude profissionalResponsavel) {

        this.data = data;
        this.horario = horario;
        this.prioridade = prioridade;
        this.statusEmergencia = StatusEmergencia.AGUARDANDO;
        this.profissionalResponsavel = profissionalResponsavel;

        setPaciente(paciente);
    }

    public void iniciarAtendimento() {
        if (statusEmergencia != StatusEmergencia.AGUARDANDO) {
            throw new IllegalStateException(
                "O atendimento não está aguardando."
            );
        }

        statusEmergencia = StatusEmergencia.INICIADO;
    }

    public void concluirAtendimento() {
        if (statusEmergencia != StatusEmergencia.INICIADO) {
            throw new IllegalStateException(
                "O atendimento precisa estar iniciado para ser concluído."
            );
        }

        statusEmergencia = StatusEmergencia.CONCLUIDO;
    }

    @Override
    public boolean validarCondicoes() {
        return data != null
                && horario != null
                && prioridade != null
                && getPaciente() != null
                && profissionalResponsavel != null;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusEmergencia getStatusEmergencia() {
        return statusEmergencia;
    }

    public ProfissionalSaude getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public void setProfissionalResponsavel(
            ProfissionalSaude profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }
}