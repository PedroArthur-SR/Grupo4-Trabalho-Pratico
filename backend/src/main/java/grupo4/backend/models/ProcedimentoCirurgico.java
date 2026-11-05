package grupo4.backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

import grupo4.backend.models.enums.StatusProcedimento;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class ProcedimentoCirurgico extends Atendimento {

    private String descricao;
    private String tipoCirurgia;
    private String observacoesPreOperatorias;
    private String observacoesPosOperatorias;

    private LocalDate dataPrevista;
    private LocalTime horarioPrevisto;

    private LocalDate dataRealizada;
    private LocalTime horarioRealizado;

    @Enumerated(EnumType.STRING)
    private StatusProcedimento statusProcedimento;

    @ManyToOne
    @JoinColumn(name = "cirurgiao_id")
    private Cirurgiao cirurgiaoResponsavel;

    protected ProcedimentoCirurgico() {}

    public ProcedimentoCirurgico(
            String descricao,
            String tipoCirurgia,
            String observacoesPreOperatorias,
            LocalDate dataPrevista,
            LocalTime horarioPrevisto,
            Paciente paciente,
            Cirurgiao cirurgiaoResponsavel) {

        this.descricao = descricao;
        this.tipoCirurgia = tipoCirurgia;
        this.observacoesPreOperatorias = observacoesPreOperatorias;
        this.dataPrevista = dataPrevista;
        this.horarioPrevisto = horarioPrevisto;
        this.statusProcedimento = StatusProcedimento.AGENDADO;
        this.cirurgiaoResponsavel = cirurgiaoResponsavel;

        setPaciente(paciente);
    }

    public void iniciarProcedimento() {
        if (statusProcedimento != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                "O procedimento não está agendado."
            );
        }

        statusProcedimento = StatusProcedimento.EM_ANDAMENTO;

        dataRealizada = LocalDate.now();
        horarioRealizado = LocalTime.now();
    }

    public void concluirProcedimento(String observacoesPosOperatorias) {
        if (statusProcedimento != StatusProcedimento.EM_ANDAMENTO) {
            throw new IllegalStateException(
                "O procedimento precisa estar em andamento para ser concluído."
            );
        }

        if (observacoesPosOperatorias == null
                || observacoesPosOperatorias.isBlank()) {
            throw new IllegalArgumentException(
                "As observações pós-operatórias são obrigatórias."
            );
        }

        this.observacoesPosOperatorias = observacoesPosOperatorias;
        statusProcedimento = StatusProcedimento.CONCLUIDO;
    }

    public void cancelarProcedimento() {
        if (statusProcedimento != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                "Somente procedimentos agendados podem ser cancelados."
            );
        }

        statusProcedimento = StatusProcedimento.CANCELADO;
    }

    @Override
    public boolean validarCondicoes() {
        return descricao != null
                && !descricao.isBlank()
                && tipoCirurgia != null
                && !tipoCirurgia.isBlank()
                && dataPrevista != null
                && horarioPrevisto != null
                && getPaciente() != null
                && cirurgiaoResponsavel != null;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoCirurgia() {
        return tipoCirurgia;
    }

    public void setTipoCirurgia(String tipoCirurgia) {
        this.tipoCirurgia = tipoCirurgia;
    }

    public String getObservacoesPreOperatorias() {
        return observacoesPreOperatorias;
    }

    public void setObservacoesPreOperatorias(String observacoesPreOperatorias) {
        this.observacoesPreOperatorias = observacoesPreOperatorias;
    }

    public String getObservacoesPosOperatorias() {
        return observacoesPosOperatorias;
    }

    public void setObservacoesPosOperatorias(String observacoesPosOperatorias) {
        this.observacoesPosOperatorias = observacoesPosOperatorias;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalTime getHorarioPrevisto() {
        return horarioPrevisto;
    }

    public void setHorarioPrevisto(LocalTime horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
    }

    public LocalDate getDataRealizada() {
        return dataRealizada;
    }

    public LocalTime getHorarioRealizado() {
        return horarioRealizado;
    }

    public StatusProcedimento getStatusProcedimento() {
        return statusProcedimento;
    }

    public Cirurgiao getCirurgiaoResponsavel() {
        return cirurgiaoResponsavel;
    }

    public void setCirurgiaoResponsavel(Cirurgiao cirurgiaoResponsavel) {
        this.cirurgiaoResponsavel = cirurgiaoResponsavel;
    }
}