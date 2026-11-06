
package grupo4.backend.dto;

import java.time.LocalDate;

public record InternacaoRequest(
    LocalDate dataEntrada,
    LocalDate dataPrevistaAlta,
    String observacoes,
    Integer pacienteId,
    Integer profissionalId,
    Integer quartoId
) {}
