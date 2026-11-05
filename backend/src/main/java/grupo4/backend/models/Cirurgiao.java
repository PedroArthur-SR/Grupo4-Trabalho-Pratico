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

    public String getAreaAtuacao() {
        return areaAtuacaoCirurgica;
    }

    public void setAreaAtuacao(String areaAtuacaoCirurgica) {
        this.areaAtuacaoCirurgica = areaAtuacaoCirurgica;
    }
    
}
