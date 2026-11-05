
package grupo4.backend.models;

import grupo4.backend.models.enums.Especialidade;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class MedicoEspecialista extends ProfissionalSaude {
    @Enumerated(EnumType.STRING)
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
    

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    
}
