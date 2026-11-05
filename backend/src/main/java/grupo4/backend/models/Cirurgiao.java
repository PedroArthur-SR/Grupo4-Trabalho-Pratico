package grupo4.backend.models;

import jakarta.persistence.Entity;
@Entity 

public class Cirurgiao extends ProfissionalSaude {
    private String areaAtuacaoCirurgica;

    protected Cirurgiao(){};

    public Cirurgiao(String nome, String registroProfissional, String telefone, String email, String areaAtuacaoCirurgica) {
        super(nome, registroProfissional, telefone, email);
        this.areaAtuacaoCirurgica = areaAtuacaoCirurgica;
    }

    public void registrarProcedimento(ProcedimentoCirurgico procedimento) {
        if (procedimento == null) {
            throw new IllegalArgumentException("O procedimento não pode ser nulo.");
        }

        procedimento.setCirurgiaoResponsavel(this);

        if (!procedimento.validarCondicoes()) {
            procedimento.setCirurgiaoResponsavel(null);
            throw new IllegalArgumentException("Procedimento inválido.");
        }
    }

    public String getAreaAtuacao() {
        return areaAtuacaoCirurgica;
    }

    public void setAreaAtuacao(String areaAtuacaoCirurgica) {
        this.areaAtuacaoCirurgica = areaAtuacaoCirurgica;
    }
    
}
