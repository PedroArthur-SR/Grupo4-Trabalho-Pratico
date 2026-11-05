package grupo4.backend.models.enums;

public enum StatusProcedimento {
    AGENDADO,
    EM_ANDAMENTO,
    CONCLUIDO,
    CANCELADO;

    public boolean podeMudarPara(StatusProcedimento novo) {
        return switch (this) {
            case AGENDADO     -> novo == EM_ANDAMENTO || novo == CANCELADO;
            case EM_ANDAMENTO -> novo == CONCLUIDO;
            case CONCLUIDO, CANCELADO -> false;
        };
    }
}
