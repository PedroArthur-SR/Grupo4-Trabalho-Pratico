package grupo4.backend.models;
import jakarta.persistence.Entity;
@Entity 
public class MedicoClinicoGeral extends ProfissionalSaude {
    protected MedicoClinicoGeral() {
    }

    public MedicoClinicoGeral(
            String nome,
            String registroProfissional,
            String telefone,
            String email) {
                super(nome, registroProfissional, telefone, email);
            }
    
}
