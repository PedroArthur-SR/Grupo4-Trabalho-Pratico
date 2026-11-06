
package grupo4.backend.services;

import grupo4.backend.dto.ConsultaRequest;

import grupo4.backend.models.Consulta;
import grupo4.backend.models.ConsultaRotina;
import grupo4.backend.models.ConsultaEspecializada;
import grupo4.backend.models.Paciente;
import grupo4.backend.models.ProfissionalSaude;
import grupo4.backend.models.MedicoClinicoGeral;
import grupo4.backend.models.MedicoEspecialista;

import grupo4.backend.repositories.ConsultaRepository;
import grupo4.backend.repositories.PacienteRepository;
import grupo4.backend.repositories.ProfissionalSaudeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalSaudeRepository profissionalRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            ProfissionalSaudeRepository profissionalRepository) {

        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<Consulta> listarTodos() {
        return consultaRepository.findAll();
    }

    public Consulta buscarPorId(Integer id) {
        return consultaRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Consulta não encontrada"));
    }

    public Consulta cadastrar(ConsultaRequest dados) {

        if (dados.tipo() == null || dados.tipo().isBlank()) {
            throw new IllegalArgumentException(
                "Informe o tipo da consulta."
            );
        }

        Paciente paciente = pacienteRepository.findById(
            dados.pacienteId()
        ).orElseThrow(() ->
            new RuntimeException("Paciente não encontrado")
        );

        ProfissionalSaude profissional = profissionalRepository.findById(
            dados.profissionalId()
        ).orElseThrow(() ->
            new RuntimeException("Profissional não encontrado")
        );

        Consulta consulta;

        switch (dados.tipo().toUpperCase()) {

            case "ROTINA" -> {

                if (!(profissional instanceof MedicoClinicoGeral)) {
                    throw new IllegalArgumentException(
                        "Consulta de rotina exige um médico clínico-geral."
                    );
                }

                consulta = new ConsultaRotina(
                    dados.data(),
                    dados.horario(),
                    dados.motivo(),
                    paciente,
                    (MedicoClinicoGeral) profissional
                );
            }

            case "ESPECIALIZADA" -> {

                if (!(profissional instanceof MedicoEspecialista)) {
                    throw new IllegalArgumentException(
                        "Consulta especializada exige um médico especialista."
                    );
                }

                if (dados.especialidade() == null) {
                    throw new IllegalArgumentException(
                        "Informe a especialidade da consulta."
                    );
                }

                consulta = new ConsultaEspecializada(
                    dados.data(),
                    dados.horario(),
                    dados.motivo(),
                    paciente,
                    (MedicoEspecialista) profissional,
                    dados.especialidade()
                );
            }

            default -> throw new IllegalArgumentException(
                "Tipo de consulta inválido."
            );
        }

        return consultaRepository.save(consulta);
    }

    public Consulta atualizar(Integer id, ConsultaRequest dados) {

        Consulta consulta = buscarPorId(id);

        if (consulta.getStatusConsulta() !=
                grupo4.backend.models.enums.StatusConsulta.AGENDADA) {
            throw new IllegalStateException(
                "Somente consultas agendadas podem ser atualizadas."
            );
        }

        if (dados.data() == null ||
            dados.horario() == null ||
            dados.motivo() == null ||
            dados.motivo().isBlank()) {

            throw new IllegalArgumentException(
                "Informe data, horário e motivo da consulta."
            );
        }

        consulta.setData(dados.data());
        consulta.setHorario(dados.horario());
        consulta.setMotivo(dados.motivo());

        return consultaRepository.save(consulta);
    }

    public void excluir(Integer id) {

        Consulta consulta = buscarPorId(id);

        if (consulta.getStatusConsulta() !=
                grupo4.backend.models.enums.StatusConsulta.AGENDADA) {
            throw new IllegalStateException(
                "Somente consultas agendadas podem ser excluídas."
            );
        }

        consultaRepository.delete(consulta);
    }
}
