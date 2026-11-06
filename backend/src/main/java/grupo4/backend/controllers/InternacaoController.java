
package grupo4.backend.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import grupo4.backend.dto.InternacaoRequest;
import grupo4.backend.models.Internacao;
import grupo4.backend.services.InternacaoService;

@RestController
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    public InternacaoController(InternacaoService internacaoService) {
        this.internacaoService = internacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Internacao>> listarTodos() {
        return ResponseEntity.ok(internacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internacao> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(internacaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Internacao> cadastrar(
            @RequestBody InternacaoRequest dados) {

        Internacao internacao = internacaoService.cadastrar(dados);

        return ResponseEntity.status(HttpStatus.CREATED).body(internacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Internacao> atualizar(
            @PathVariable int id,
            @RequestBody InternacaoRequest dados) {

        Internacao internacao = internacaoService.atualizar(id, dados);

        return ResponseEntity.ok(internacao);
    }

    @PatchMapping("/{id}/alta")
    public ResponseEntity<Void> registrarAlta(
            @PathVariable int id,
            @RequestParam LocalDate data) {

        internacaoService.registrarAlta(id, data);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {

        internacaoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
