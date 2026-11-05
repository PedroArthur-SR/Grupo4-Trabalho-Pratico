package grupo4.backend.models;
import grupo4.backend.models.enums.StatusConsulta; 
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
public abstract class Consulta extends Atendimento{
    private LocalDate data;
    private LocalTime horario;
    private String motivo;
    private String observacoesMedicas;
    @Enumerated(EnumType.STRING)
    private StatusConsulta statusConsulta;
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissionalResponsavel;

    protected Consulta(){}

    
    public Consulta(LocalDate data, LocalTime horario, String motivo, String observacoesMedicas,
            StatusConsulta statusConsulta, ProfissionalSaude profissionalResponsavel) {
        this.data = data;
        this.horario = horario;
        this.motivo = motivo;
        this.observacoesMedicas = observacoesMedicas;
        this.statusConsulta = statusConsulta;
        this.profissionalResponsavel = profissionalResponsavel;
    }

    public boolean alterarStatus(StatusConsulta novoStatus) {
        if (novoStatus == null || statusConsulta == null) {
            return false;
        }

        if (!statusConsulta.podeMudarPara(novoStatus)) {
            return false;
        }

        this.statusConsulta = novoStatus;
        return true;
    }
    public void cancelar() {
        if (!alterarStatus(StatusConsulta.CANCELADA)) {
            throw new IllegalStateException("Não foi possível cancelar a consulta.");
        }
    }
    public void realizar(String observacoes) {
        if (statusConsulta != StatusConsulta.EM_ATENDIMENTO) {
            throw new IllegalStateException("A consulta não está em atendimento.");
        }

        if (observacoes == null || observacoes.isBlank()) {
            throw new IllegalArgumentException("As observações são obrigatórias.");
        }

        if (alterarStatus(StatusConsulta.CONCLUIDA)) {
            this.observacoesMedicas = observacoes;
        }
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
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public String getObservacoesMedicas() {
        return observacoesMedicas;
    }
    public void setObservacoesMedicas(String observacoesMedicas) {
        this.observacoesMedicas = observacoesMedicas;
    }
    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }
    public ProfissionalSaude getProfissionalResponsavel() {
        return profissionalResponsavel;
    }
    public void setProfissionalResponsavel(ProfissionalSaude profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }

}
