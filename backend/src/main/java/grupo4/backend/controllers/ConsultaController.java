
package grupo4.backend.controllers;

import grupo4.backend.dto.ConsultaRequest;
import grupo4.backend.models.Consulta;
import grupo4.backend.services.ConsultaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarTodos() {
        return ResponseEntity.ok(
            consultaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
            consultaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @RequestBody ConsultaRequest dados) {

        Consulta novaConsulta = consultaService.cadastrar(dados);

        return ResponseEntity.status(201).body(novaConsulta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(
            @PathVariable Integer id,
            @RequestBody ConsultaRequest dados) {

        Consulta consultaAtualizada =
                consultaService.atualizar(id, dados);

        return ResponseEntity.ok(consultaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {

        consultaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
