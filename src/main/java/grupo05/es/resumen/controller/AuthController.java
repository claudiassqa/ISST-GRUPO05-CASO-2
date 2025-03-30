package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Lector;
import grupo05.es.resumen.model.Visitante;
import grupo05.es.resumen.repository.LectorRepository;
import grupo05.es.resumen.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LectorRepository lectorRepository;
    private final VisitanteRepository visitanteRepository;

    @GetMapping("/lector/{email}")
    public ResponseEntity<Lector> obtenerLectorPorEmail(@PathVariable String email) {
        Optional<Lector> lector = lectorRepository.findByEmailIgnoreCase(email);
        return lector.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/visitante/{email}")
    public ResponseEntity<Visitante> obtenerVisitantePorEmail(@PathVariable String email) {
        Optional<Visitante> visitante = visitanteRepository.findByEmailIgnoreCase(email);
        return visitante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
