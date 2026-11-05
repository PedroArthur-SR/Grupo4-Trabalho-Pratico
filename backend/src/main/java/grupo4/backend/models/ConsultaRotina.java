package grupo4.backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

import grupo4.backend.models.enums.StatusConsulta;
import jakarta.persistence.Entity;

@Entity
public class ConsultaRotina extends Consulta {

    protected ConsultaRotina() {}

    public ConsultaRotina(
            LocalDate data,
            LocalTime horario,
            String motivo,
            Paciente paciente,
            MedicoClinicoGeral profissionalResponsavel) {

        super(
            data,
            horario,
            motivo,
            null,
            StatusConsulta.AGENDADA,
            profissionalResponsavel
        );

        setPaciente(paciente);
        if (!validarCondicoes()) {
        throw new IllegalArgumentException(
            "Dados inválidos para consulta de rotina."
    );
}
    }

     @Override
    public boolean validarCondicoes() {
        return getData() != null
                && getHorario() != null
                && getMotivo() != null
                && !getMotivo().isBlank()
                && getPaciente() != null
                && getProfissionalResponsavel() instanceof MedicoClinicoGeral;
    }
}