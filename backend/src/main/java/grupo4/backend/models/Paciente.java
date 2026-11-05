package grupo4.backend.models;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity 
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    @OneToOne 
    @JoinColumn (name = "endereco_id")
    private Endereco endereco;
    @OneToOne
    @JoinColumn(name = "historico_id")
    private HistoricoMedico historico;
    protected Paciente(){

    }
    
    public Paciente(String nome, String telefone, String email, String cpf, LocalDate dataNascimento, Endereco endereco, HistoricoMedico historico) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;    
        this.endereco = endereco;
    }
    public int calcularIdade() {
        if (dataNascimento == null) {
            throw new IllegalArgumentException(
                "A data de nascimento não pode ser nula."
            );
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                "A data de nascimento não pode ser futura."
            );
        }

        LocalDate hoje = LocalDate.now();

        int idade = hoje.getYear() - dataNascimento.getYear();

        boolean aindaNaoFezAniversario =
            hoje.getMonthValue() < dataNascimento.getMonthValue() ||
            (hoje.getMonthValue() == dataNascimento.getMonthValue()
            && hoje.getDayOfMonth() < dataNascimento.getDayOfMonth());

        if (aindaNaoFezAniversario) {
            idade--;
        }

        return idade;
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
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public HistoricoMedico getHistorico() {
        return historico;
    }

    public void setHistorico(HistoricoMedico historico) {
        this.historico = historico;
    }
    
    
}

