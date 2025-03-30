package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Lector;
import grupo05.es.resumen.model.Resumen;
import grupo05.es.resumen.model.Visitante;
import grupo05.es.resumen.repository.LectorRepository;
import grupo05.es.resumen.repository.ResumenRepository;
import grupo05.es.resumen.repository.VisitanteRepository;
import grupo05.es.resumen.service.ResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalogo")
@RequiredArgsConstructor
public class ResumenController {

    private final ResumenService resumenService;
    private final ResumenRepository resumenRepository;
    private final LectorRepository lectorRepository;
    private final VisitanteRepository visitanteRepository;

    // Endpoint de login que distingue entre lector y visitante
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<Lector> lector = lectorRepository.findAll().stream()
                .filter(l -> l.getEmail().equalsIgnoreCase(request.getEmail()))
                .findFirst();

        if (lector.isPresent()) {
            return ResponseEntity.ok("lector");
        }

        Optional<Visitante> visitante = visitanteRepository.findAll().stream()
                .filter(v -> v.getEmail().equalsIgnoreCase(request.getEmail()))
                .findFirst();

        if (visitante.isPresent()) {
            return ResponseEntity.ok("visitante");
        }

        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    // Endpoint que devuelve resúmenes según el tipo de usuario
    @GetMapping("/resumenes")
    public List<Resumen> obtenerTodos(@RequestParam(required = false, defaultValue = "visitante") String tipo) {
        if ("lector".equalsIgnoreCase(tipo)) {
            return resumenRepository.findAll(); // Lectores ven todo
        } else {
            return resumenRepository.findByPrimeFalse(); // Visitantes solo los gratuitos
        }
    }

    @GetMapping("/resumenes/{id}")
    public ResponseEntity<Resumen> obtenerPorId(@PathVariable Integer id) {
        return resumenRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/resumen")
    public ResponseEntity<String> crearResumen(@RequestBody Resumen resumen) {
        if (resumen.getAutor() == null || resumen.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body("El resumen debe tener un autor con ID válido.");
        }

        resumenService.guardarResumen(resumen);
        return ResponseEntity.ok("Resumen creado correctamente");
    }
}

