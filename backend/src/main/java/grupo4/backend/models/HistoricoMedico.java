
package grupo4.backend.models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class HistoricoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    private List<String> informacoesRelevantes = new ArrayList<>();

    protected HistoricoMedico() {}

    public void adicionarInformacao(String info) {
        if (info == null || info.isBlank()) {
            throw new IllegalArgumentException(
                "A informação não pode ser vazia."
            );
        }

        informacoesRelevantes.add(info);
    }

    public int getId() {
        return id;
    }

    public List<String> getInformacoesRelevantes() {
        return informacoesRelevantes;
    }

    public void setInformacoesRelevantes(
            List<String> informacoesRelevantes) {
        this.informacoesRelevantes = informacoesRelevantes;
    }
}
