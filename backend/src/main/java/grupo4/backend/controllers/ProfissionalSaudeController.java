
package grupo4.backend.controllers;

import grupo4.backend.dto.ProfissionalRequest;
import grupo4.backend.models.ProfissionalSaude;
import grupo4.backend.services.ProfissionalSaudeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;

    public ProfissionalSaudeController(
            ProfissionalSaudeService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listarTodos() {
        return ResponseEntity.ok(
            profissionalService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
            profissionalService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaude> cadastrar(
            @RequestBody ProfissionalRequest dados) {

        ProfissionalSaude novoProfissional =
                profissionalService.cadastrar(dados);

        return ResponseEntity.status(201).body(novoProfissional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> atualizar(
            @PathVariable Integer id,
            @RequestBody ProfissionalRequest dados) {

        ProfissionalSaude profissionalAtualizado =
                profissionalService.atualizar(id, dados);

        return ResponseEntity.ok(profissionalAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {

        profissionalService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
