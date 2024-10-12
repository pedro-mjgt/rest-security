package pe.edu.cibertec.rest_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.rest_security.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
