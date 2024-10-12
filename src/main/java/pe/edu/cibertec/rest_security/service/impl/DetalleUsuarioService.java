package pe.edu.cibertec.rest_security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.rest_security.model.Rol;
import pe.edu.cibertec.rest_security.model.Usuario;
import pe.edu.cibertec.rest_security.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {

    private final IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioXNombre(username);

        return crearUserDatail(usuario, obtenerRolesUsuario(usuario.getRoles()));
    }

    public List<GrantedAuthority> obtenerRolesUsuario(Set<Rol> rolesList) {
        List<GrantedAuthority> rolesAuth = new ArrayList<>();

        for (Rol rol : rolesList) {
            rolesAuth.add(new SimpleGrantedAuthority("ROLE_" +
                    rol.getNombre()));
        }

        return rolesAuth;
    }

    private UserDetails crearUserDatail(
            Usuario usuario,
            List<GrantedAuthority> authorityList
    ) {
        return new User(usuario.getUsuario(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                authorityList);
    }

}
