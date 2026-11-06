package grupo4.backend.dto;

import grupo4.backend.models.enums.Especialidade;

public record ProfissionalRequest(
    String tipo,
    String nome,
    String registroProfissional,
    String telefone,
    String email,
    Especialidade especialidade,
    String areaAtuacaoCirurgica
) {
}