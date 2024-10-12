package pe.edu.cibertec.rest_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/apis")
public class ApisController {

    @PreAuthorize("hasRole('Gestor')")
    @PostMapping("")
    public ResponseEntity<String> crearGestor() {
        return new ResponseEntity<>("API Autorizada a usuarios con rol de Gestor",
                HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('Coordinador')")
    public ResponseEntity<String> listarCoordinadores() {
        return new ResponseEntity<>("API Autorizada a usuarios con rol de Coordinador",
                HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('Gestor') and hasRole('Coordinador')")
    public ResponseEntity<String> actualizarGestorCoordinador() {
        return new ResponseEntity<>("API Autorizada a usuarios con rol de Gestor y Coordinador",
                HttpStatus.OK);
    }

}
