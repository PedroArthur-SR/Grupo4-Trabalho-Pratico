package grupo4.backend.models;

import java.util.ArrayList;
import java.util.List;

import grupo4.backend.models.enums.TipoQuarto;
import grupo4.backend.models.enums.SituacaoQuarto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;

@Entity
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numero;
    private int andar;
    private int capacidadeMaxima;

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipo;

    @Enumerated(EnumType.STRING)
    private SituacaoQuarto situacao;

    @OneToMany(mappedBy = "quarto")
    private List<Internacao> internacoes = new ArrayList<>();

    protected Quarto() {}

    public Quarto(int numero, int andar, int capacidadeMaxima, TipoQuarto tipo) {
        if (numero <= 0 || andar < 0 || capacidadeMaxima <= 0 || tipo == null) {
            throw new IllegalArgumentException("Dados inválidos para cadastrar o quarto.");
        }

        this.numero = numero;
        this.andar = andar;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tipo = tipo;
        this.situacao = SituacaoQuarto.DISPONIVEL;
    }

    public boolean possuiVaga() {
        long ocupacaoAtual = internacoes.stream()
                .filter(Internacao::estaAtiva)
                .count();

        return ocupacaoAtual < capacidadeMaxima;
    }

    public void adicionarInternacao(Internacao internacao) {
        if (internacao == null) {
            throw new IllegalArgumentException("A internação não pode ser nula.");
        }

        if (internacoes.contains(internacao)) {
            throw new IllegalStateException("Esta internação já está associada ao quarto.");
        }

        if (!possuiVaga()) {
            throw new IllegalStateException("O quarto está sem vagas.");
        }

        if (internacao.getQuarto() != null && internacao.getQuarto() != this) {
            throw new IllegalStateException("A internação já está associada a outro quarto.");
        }

        internacao.setQuarto(this);
        internacoes.add(internacao);
        situacao = SituacaoQuarto.OCUPADO;
    }

    public void liberar() {
        long ocupacaoAtual = internacoes.stream()
                .filter(Internacao::estaAtiva)
                .count();

        if (ocupacaoAtual == 0) {
            situacao = SituacaoQuarto.DISPONIVEL;
        } else {
            situacao = SituacaoQuarto.OCUPADO;
        }
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public SituacaoQuarto getSituacao() {
        return situacao;
    }

    public List<Internacao> getInternacoes() {
        return internacoes;
    }
}