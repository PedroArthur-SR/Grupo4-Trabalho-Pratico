package grupo4.backend.models.enums;

public enum StatusEmergencia {
    AGUARDANDO,
    INICIADO,
    CONCLUIDO;

    public boolean podeMudarPara(StatusEmergencia novo) {
        return switch (this) {
            case AGUARDANDO -> novo == INICIADO;
            case INICIADO   -> novo == CONCLUIDO;
            case CONCLUIDO  -> false;
        };
    }
}