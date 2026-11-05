package grupo4.backend.models;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProfissionalSaude {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String registroProfissional;
    private String telefone;
    private String email;

    @OneToMany (mappedBy = "profissional")
    private List<Disponibilidade> disponibilidades = new ArrayList<>();
    protected ProfissionalSaude() {
    }

    public ProfissionalSaude(
            String nome,
            String registroProfissional,
            String telefone,
            String email) {
        this.nome = nome;
        this.registroProfissional = registroProfissional;
        this.telefone = telefone;
        this.email = email;
    }
    public boolean estaDisponivel(LocalDate data, LocalTime horario) {
        if (data == null || horario == null) {
            return false;
        }

        return disponibilidades.stream().anyMatch(disponibilidade ->
            disponibilidade.getDiaSemana() == data.getDayOfWeek()
            && disponibilidade.contem(horario)
        );
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
