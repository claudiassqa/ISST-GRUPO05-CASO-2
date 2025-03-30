package grupo05.es.resumen.repository;

import grupo05.es.resumen.model.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Integer> {
    Optional<Visitante> findByEmailIgnoreCase(String email);
}
