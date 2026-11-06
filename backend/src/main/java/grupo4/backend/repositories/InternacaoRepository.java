
package grupo4.backend.repositories;

import grupo4.backend.models.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternacaoRepository
        extends JpaRepository<Internacao, Integer> {
}
