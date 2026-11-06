
package grupo4.backend.controllers;

import grupo4.backend.models.Paciente;
import grupo4.backend.services.PacienteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<Paciente> cadastrar(
            @RequestBody Paciente paciente) {

        Paciente novoPaciente = pacienteService.cadastrar(paciente);

        return ResponseEntity.status(201).body(novoPaciente);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(
            @PathVariable Integer id,
            @RequestBody Paciente paciente) {

        return ResponseEntity.ok(
            pacienteService.atualizar(id, paciente)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        pacienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
