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
    public void registrarConsulta(ConsultaRotina consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("A consulta não pode ser nula.");
        }

        consulta.setProfissionalResponsavel(this);

        if (!consulta.validarCondicoes()) {
            consulta.setProfissionalResponsavel(null);
            throw new IllegalArgumentException("Consulta inválida.");
        }
    }
    public void atenderEspecialidade(ConsultaEspecializada consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("A consulta não pode ser nula.");
        }

        consulta.setProfissionalResponsavel(this);

        if (!consulta.validarCondicoes()) {
            consulta.setProfissionalResponsavel(null);
            throw new IllegalArgumentException("Consulta especializada inválida.");
        }
    }
    
}
