package grupo05.es.resumen.controller;

import grupo05.es.resumen.model.Resumen;
import grupo05.es.resumen.service.ResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@RequiredArgsConstructor
public class ResumenController {

    private final ResumenService resumenService;

    @GetMapping("/resumenes")
    public ResponseEntity<List<Resumen>> getTodosResumenes() {
        List<Resumen> resumenes = resumenService.getAllResumenes();
        return resumenes.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(resumenes);
    }

    @GetMapping("/resumen/{id}")
    public ResponseEntity<Resumen> getResumenPorId(@PathVariable Integer id) {
        return resumenService.getResumenById(id)
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
