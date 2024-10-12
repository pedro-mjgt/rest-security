package pe.edu.cibertec.rest_security.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.rest_security.dto.UsuarioResponseDto;
import pe.edu.cibertec.rest_security.model.Usuario;
import pe.edu.cibertec.rest_security.service.IUsuarioService;
import pe.edu.cibertec.rest_security.service.impl.DetalleUsuarioService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final DetalleUsuarioService detalleUsuarioService;
    private final AuthenticationManager authenticationManager;
    private final IUsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> autenticarUsuario(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password
    ) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            usuario, password));

            if (authentication.isAuthenticated()) {
                Usuario objUsuario = usuarioService.obtenerUsuarioXNombre(usuario);
                String token = generarToken(objUsuario);

                return new ResponseEntity<>(
                        UsuarioResponseDto.builder().id(objUsuario.getId())
                                .usuario(objUsuario.getUsuario())
                                .token(token).build(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generarToken(Usuario usuario) {
        String clave = "ClavePrivada";
        List<GrantedAuthority> authorityList = detalleUsuarioService
                .obtenerRolesUsuario(usuario.getRoles());

        return Jwts.builder()
                .setId(usuario.getId().toString())
                .setSubject(usuario.getUsuario())
                .claim("email", usuario.getEmail())
                .claim("authorities",
                        authorityList.stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 180000))
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
    }

}
