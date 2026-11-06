
package grupo4.backend.services;

import grupo4.backend.dto.ProfissionalRequest;
import grupo4.backend.models.ProfissionalSaude;
import grupo4.backend.models.MedicoClinicoGeral;
import grupo4.backend.models.MedicoEspecialista;
import grupo4.backend.models.Cirurgiao;
import grupo4.backend.repositories.ProfissionalSaudeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalRepository;

    public ProfissionalSaudeService(
            ProfissionalSaudeRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public List<ProfissionalSaude> listarTodos() {
        return profissionalRepository.findAll();
    }

    public ProfissionalSaude buscarPorId(Integer id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Profissional não encontrado"));
    }

    public ProfissionalSaude cadastrar(ProfissionalRequest dados) {

        if (dados.tipo() == null || dados.tipo().isBlank()) {
            throw new IllegalArgumentException(
                "Informe o tipo do profissional."
            );
        }

        ProfissionalSaude profissional;

        switch (dados.tipo().toUpperCase()) {

            case "CLINICO_GERAL" ->
                profissional = new MedicoClinicoGeral(
                    dados.nome(),
                    dados.registroProfissional(),
                    dados.telefone(),
                    dados.email()
                );

            case "ESPECIALISTA" -> {
                if (dados.especialidade() == null) {
                    throw new IllegalArgumentException(
                        "Informe a especialidade."
                    );
                }

                profissional = new MedicoEspecialista(
                    dados.nome(),
                    dados.registroProfissional(),
                    dados.telefone(),
                    dados.email(),
                    dados.especialidade()
                );
            }

            case "CIRURGIAO" -> {
                if (dados.areaAtuacaoCirurgica() == null ||
                    dados.areaAtuacaoCirurgica().isBlank()) {
                    throw new IllegalArgumentException(
                        "Informe a área de atuação cirúrgica."
                    );
                }

                profissional = new Cirurgiao(
                    dados.nome(),
                    dados.registroProfissional(),
                    dados.telefone(),
                    dados.email(),
                    dados.areaAtuacaoCirurgica()
                );
            }

            default -> throw new IllegalArgumentException(
                "Tipo de profissional inválido."
            );
        }

        return profissionalRepository.save(profissional);
    }

    public ProfissionalSaude atualizar(
            Integer id, ProfissionalRequest dados) {

        ProfissionalSaude profissional = buscarPorId(id);

        if (dados.tipo() == null || dados.tipo().isBlank()) {
            throw new IllegalArgumentException(
                "Informe o tipo do profissional."
            );
        }

        String tipo = dados.tipo().toUpperCase();

        if (profissional instanceof MedicoClinicoGeral
                && !tipo.equals("CLINICO_GERAL")) {
            throw new IllegalArgumentException(
                "Não é possível alterar a categoria do profissional."
            );
        }

        if (profissional instanceof MedicoEspecialista
                && !tipo.equals("ESPECIALISTA")) {
            throw new IllegalArgumentException(
                "Não é possível alterar a categoria do profissional."
            );
        }

        if (profissional instanceof Cirurgiao
                && !tipo.equals("CIRURGIAO")) {
            throw new IllegalArgumentException(
                "Não é possível alterar a categoria do profissional."
            );
        }

        profissional.setNome(dados.nome());
        profissional.setRegistroProfissional(
            dados.registroProfissional()
        );
        profissional.setTelefone(dados.telefone());
        profissional.setEmail(dados.email());

        if (profissional instanceof MedicoEspecialista especialista) {

            if (dados.especialidade() == null) {
                throw new IllegalArgumentException(
                    "Informe a especialidade."
                );
            }

            especialista.setEspecialidade(dados.especialidade());
        }

        if (profissional instanceof Cirurgiao cirurgiao) {

            if (dados.areaAtuacaoCirurgica() == null ||
                dados.areaAtuacaoCirurgica().isBlank()) {
                throw new IllegalArgumentException(
                    "Informe a área de atuação cirúrgica."
                );
            }

            cirurgiao.setAreaAtuacao(
                dados.areaAtuacaoCirurgica()
            );
        }

        return profissionalRepository.save(profissional);
    }

    public void excluir(Integer id) {
        ProfissionalSaude profissional = buscarPorId(id);
        profissionalRepository.delete(profissional);
    }
}
