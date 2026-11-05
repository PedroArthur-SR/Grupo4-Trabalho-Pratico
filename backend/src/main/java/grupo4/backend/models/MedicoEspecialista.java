
package grupo4.backend.models;

import grupo4.backend.models.enums.Especialidade;
import jakarta.persistence.Entity;

@Entity
public class MedicoEspecialista extends ProfissionalSaude {

    private Especialidade especialidade;

    protected MedicoEspecialista() {
    }

    public MedicoEspecialista(
            String nome,
            String registroProfissional,
            String telefone,
            String email,
            Especialidade especialidade) {

        super(nome, registroProfissional, telefone, email);
        this.especialidade = especialidade;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
