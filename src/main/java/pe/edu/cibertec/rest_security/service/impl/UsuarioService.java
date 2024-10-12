package pe.edu.cibertec.rest_security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.rest_security.model.Usuario;
import pe.edu.cibertec.rest_security.repository.UsuarioRepository;
import pe.edu.cibertec.rest_security.service.IUsuarioService;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario obtenerUsuarioXNombre(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

}
