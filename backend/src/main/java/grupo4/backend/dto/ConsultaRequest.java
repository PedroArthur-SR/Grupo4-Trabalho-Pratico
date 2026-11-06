
package grupo4.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import grupo4.backend.models.enums.Especialidade;

public record ConsultaRequest(
    String tipo,
    LocalDate data,
    LocalTime horario,
    String motivo,
    Integer pacienteId,
    Integer profissionalId,
    Especialidade especialidade
) {}
