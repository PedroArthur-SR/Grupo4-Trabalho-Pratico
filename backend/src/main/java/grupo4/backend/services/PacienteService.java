
package grupo4.backend.services;

import grupo4.backend.models.HistoricoMedico;
import grupo4.backend.models.Paciente;
import grupo4.backend.repositories.PacienteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public Paciente cadastrar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente atualizar(Integer id, Paciente dadosAtualizados) {
        Paciente paciente = buscarPorId(id);

        paciente.setNome(dadosAtualizados.getNome());
        paciente.setTelefone(dadosAtualizados.getTelefone());
        paciente.setEmail(dadosAtualizados.getEmail());
        paciente.setCpf(dadosAtualizados.getCpf());
        paciente.setDataNascimento(dadosAtualizados.getDataNascimento());
        paciente.setEndereco(dadosAtualizados.getEndereco());

        return pacienteRepository.save(paciente);
    }

    public void excluir(Integer id) {
        Paciente paciente = buscarPorId(id);

        HistoricoMedico historico = paciente.getHistorico();

        if (historico != null) {
            historico.setPaciente(null);
        }

        pacienteRepository.delete(paciente);
    }
}
