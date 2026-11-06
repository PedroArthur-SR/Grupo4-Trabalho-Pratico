
package grupo4.backend.repositories;

import grupo4.backend.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

}
