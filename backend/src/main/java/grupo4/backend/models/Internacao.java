package grupo4.backend.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dataEntrada;
    private LocalDate dataPrevistaAlta;
    private LocalDate dataEfetivaAlta;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissionalResponsavel;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    protected Internacao() {}

    public Internacao(
            LocalDate dataEntrada,
            LocalDate dataPrevistaAlta,
            String observacoes,
            Paciente paciente,
            ProfissionalSaude profissionalResponsavel,
            Quarto quarto) {

        this.dataEntrada = dataEntrada;
        this.dataPrevistaAlta = dataPrevistaAlta;
        this.observacoes = observacoes;
        this.paciente = paciente;
        this.profissionalResponsavel = profissionalResponsavel;
        this.quarto = quarto;

        if (!validarCondicoes()) {
            throw new IllegalArgumentException(
                "Dados inválidos para realizar a internação."
            );
        }

        if (!quarto.possuiVaga()) {
            throw new IllegalStateException("O quarto está sem vagas.");
        }

        quarto.adicionarInternacao(this);
        quarto.ocupar();
    }

    public void registrarAlta(LocalDate data) {
        if (!estaAtiva()) {
            throw new IllegalStateException(
                "Esta internação já foi encerrada."
            );
        }

        if (data == null || data.isBefore(dataEntrada)) {
            throw new IllegalArgumentException(
                "Data de alta inválida."
            );
        }

        this.dataEfetivaAlta = data;
        quarto.liberar();
    }

    public boolean estaAtiva() {
        return dataEfetivaAlta == null;
    }

    public boolean validarCondicoes() {
        return dataEntrada != null
                && dataPrevistaAlta != null
                && !dataPrevistaAlta.isBefore(dataEntrada)
                && paciente != null
                && profissionalResponsavel != null
                && quarto != null;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataPrevistaAlta() {
        return dataPrevistaAlta;
    }

    public void setDataPrevistaAlta(LocalDate dataPrevistaAlta) {
        this.dataPrevistaAlta = dataPrevistaAlta;
    }

    public LocalDate getDataEfetivaAlta() {
        return dataEfetivaAlta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ProfissionalSaude getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public void setProfissionalResponsavel(
            ProfissionalSaude profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
}