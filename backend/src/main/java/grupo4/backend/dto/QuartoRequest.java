
package grupo4.backend.dto;

import grupo4.backend.models.enums.TipoQuarto;

public record QuartoRequest(
    int numero,
    int andar,
    int capacidadeMaxima,
    TipoQuarto tipo
) {}
