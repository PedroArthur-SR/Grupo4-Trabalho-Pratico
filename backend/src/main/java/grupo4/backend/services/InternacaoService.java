
package grupo4.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupo4.backend.dto.InternacaoRequest;
import grupo4.backend.models.Internacao;
import grupo4.backend.models.Paciente;
import grupo4.backend.models.ProfissionalSaude;
import grupo4.backend.models.Quarto;
import grupo4.backend.repositories.InternacaoRepository;
import grupo4.backend.repositories.PacienteRepository;
import grupo4.backend.repositories.ProfissionalSaudeRepository;
import grupo4.backend.repositories.QuartoRepository;

@Service
public class InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalSaudeRepository profissionalRepository;
    private final QuartoRepository quartoRepository;

    public InternacaoService(
            InternacaoRepository internacaoRepository,
            PacienteRepository pacienteRepository,
            ProfissionalSaudeRepository profissionalRepository,
            QuartoRepository quartoRepository) {

        this.internacaoRepository = internacaoRepository;
        this.pacienteRepository = pacienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.quartoRepository = quartoRepository;
    }

    @Transactional(readOnly = true)
    public List<Internacao> listarTodos() {
        return internacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Internacao buscarPorId(int id) {
        return internacaoRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Internação não encontrada."));
    }

    @Transactional
    public Internacao cadastrar(InternacaoRequest dados) {

        if (dados.dataEntrada() == null
                || dados.dataPrevistaAlta() == null
                || dados.pacienteId() == null
                || dados.profissionalId() == null
                || dados.quartoId() == null) {

            throw new IllegalArgumentException(
                "Preencha todos os dados obrigatórios da internação."
            );
        }

        Paciente paciente = pacienteRepository.findById(dados.pacienteId())
                .orElseThrow(() ->
                    new RuntimeException("Paciente não encontrado."));

        ProfissionalSaude profissional =
                profissionalRepository.findById(dados.profissionalId())
                .orElseThrow(() ->
                    new RuntimeException("Profissional não encontrado."));

        Quarto quarto = quartoRepository.findById(dados.quartoId())
                .orElseThrow(() ->
                    new RuntimeException("Quarto não encontrado."));

        if (!quarto.possuiVaga()) {
            throw new IllegalStateException(
                "O quarto está sem vagas disponíveis."
            );
        }

        Internacao internacao = new Internacao(
            dados.dataEntrada(),
            dados.dataPrevistaAlta(),
            dados.observacoes(),
            paciente,
            profissional,
            quarto
        );

        return internacaoRepository.save(internacao);
    }

    @Transactional
    public Internacao atualizar(int id, InternacaoRequest dados) {

        Internacao internacao = buscarPorId(id);

        if (!internacao.estaAtiva()) {
            throw new IllegalStateException(
                "Não é possível alterar uma internação encerrada."
            );
        }

        if (dados.dataEntrada() == null
                || dados.dataPrevistaAlta() == null) {

            throw new IllegalArgumentException(
                "As datas de entrada e de alta prevista são obrigatórias."
            );
        }

        if (dados.dataPrevistaAlta().isBefore(dados.dataEntrada())) {
            throw new IllegalArgumentException(
                "A data prevista de alta não pode ser anterior à entrada."
            );
        }

        internacao.setDataEntrada(dados.dataEntrada());
        internacao.setDataPrevistaAlta(dados.dataPrevistaAlta());
        internacao.setObservacoes(dados.observacoes());

        return internacaoRepository.save(internacao);
    }

    @Transactional
    public void registrarAlta(int id, LocalDate data) {

        Internacao internacao = buscarPorId(id);

        internacao.registrarAlta(data);

        internacaoRepository.save(internacao);
    }

    @Transactional
    public void excluir(int id) {

        Internacao internacao = buscarPorId(id);

        if (internacao.estaAtiva()) {
            throw new IllegalStateException(
                "Não é possível excluir uma internação ativa. Registre a alta primeiro."
            );
        }

        internacaoRepository.delete(internacao);
    }
}
