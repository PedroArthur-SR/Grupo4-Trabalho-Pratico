package grupo4.backend.models;

import grupo4.backend.models.enums.Especialidade;
import grupo4.backend.models.enums.StatusConsulta;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class ConsultaEspecializada extends Consulta {

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    protected ConsultaEspecializada() {}

    public ConsultaEspecializada(
            LocalDate data,
            LocalTime horario,
            String motivo,
            Paciente paciente,
            MedicoEspecialista profissionalResponsavel,
            Especialidade especialidade) {

        super(
            data,
            horario,
            motivo,
            null,
            StatusConsulta.AGENDADA,
            profissionalResponsavel
        );

        this.especialidade = especialidade;
        setPaciente(paciente);
    }

    @Override
    public boolean validarCondicoes() {
        return getData() != null
                && getHorario() != null
                && getMotivo() != null
                && !getMotivo().isBlank()
                && getPaciente() != null
                && getProfissionalResponsavel() instanceof MedicoEspecialista
                && especialidade != null
                && especialidade.equals(
                    ((MedicoEspecialista) getProfissionalResponsavel()).getEspecialidade()
                );
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}