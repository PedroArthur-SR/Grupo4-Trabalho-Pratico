
package grupo4.backend.services;

import grupo4.backend.dto.QuartoRequest;
import grupo4.backend.models.Quarto;
import grupo4.backend.models.Internacao;
import grupo4.backend.repositories.QuartoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    @Transactional(readOnly = true)
    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Quarto buscarPorId(Integer id) {
        return quartoRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Quarto não encontrado"));
    }

    public Quarto cadastrar(QuartoRequest dados) {

        validarDados(dados);

        Quarto quarto = new Quarto(
            dados.numero(),
            dados.andar(),
            dados.capacidadeMaxima(),
            dados.tipo()
        );

        return quartoRepository.save(quarto);
    }

    public Quarto atualizar(Integer id, QuartoRequest dados) {

        validarDados(dados);

        Quarto quarto = buscarPorId(id);

        long ocupacaoAtual = quarto.getInternacoes().stream()
                .filter(Internacao::estaAtiva)
                .count();

        if (dados.capacidadeMaxima() < ocupacaoAtual) {
            throw new IllegalArgumentException(
                "A capacidade não pode ser menor que a ocupação atual."
            );
        }

        quarto.setNumero(dados.numero());
        quarto.setAndar(dados.andar());
        quarto.setCapacidadeMaxima(dados.capacidadeMaxima());
        quarto.setTipo(dados.tipo());

        return quartoRepository.save(quarto);
    }

    public void excluir(Integer id) {

        Quarto quarto = buscarPorId(id);

        if (!quarto.getInternacoes().isEmpty()) {
            throw new IllegalStateException(
                "Não é possível excluir um quarto que possui internações associadas."
            );
        }

        quartoRepository.delete(quarto);
    }

    private void validarDados(QuartoRequest dados) {

        if (dados.numero() <= 0) {
            throw new IllegalArgumentException(
                "O número do quarto deve ser positivo."
            );
        }

        if (dados.andar() < 0) {
            throw new IllegalArgumentException(
                "O andar não pode ser negativo."
            );
        }

        if (dados.capacidadeMaxima() <= 0) {
            throw new IllegalArgumentException(
                "A capacidade máxima deve ser positiva."
            );
        }

        if (dados.tipo() == null) {
            throw new IllegalArgumentException(
                "Informe o tipo do quarto."
            );
        }
    }
}
