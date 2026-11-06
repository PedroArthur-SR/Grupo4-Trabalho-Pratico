
package grupo4.backend.controllers;

import grupo4.backend.dto.QuartoRequest;
import grupo4.backend.models.Quarto;
import grupo4.backend.services.QuartoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping
    public ResponseEntity<List<Quarto>> listarTodos() {
        return ResponseEntity.ok(
            quartoService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
            quartoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Quarto> cadastrar(
            @RequestBody QuartoRequest dados) {

        Quarto novoQuarto = quartoService.cadastrar(dados);

        return ResponseEntity.status(201).body(novoQuarto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(
            @PathVariable Integer id,
            @RequestBody QuartoRequest dados) {

        Quarto quartoAtualizado =
                quartoService.atualizar(id, dados);

        return ResponseEntity.ok(quartoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {

        quartoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
