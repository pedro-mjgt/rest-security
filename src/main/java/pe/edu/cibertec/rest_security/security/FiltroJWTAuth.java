package pe.edu.cibertec.rest_security.security;

import io.jsonwebtoken.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroJWTAuth extends OncePerRequestFilter {

    private final String CLAVE = "ClavePrivada";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (validarUsoDeToken(request)) {
                Claims claims = validarToken(request);

                if (claims.get("authorities") != null) {
                    crearAuthToken(claims);
                }
                else {
                    SecurityContextHolder.clearContext();
                }
            }
            else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException |
               UnsupportedJwtException |
               MalformedJwtException ex) {
        }
    }

    private void crearAuthToken(Claims claims) {
        List<String> authList = (List<String>) claims.get("authorities");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authList.stream().map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                );

        SecurityContextHolder.getContext().setAuthentication(
                authenticationToken);
    }

    private Claims validarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization")
                .replace("Bearer ", "");

        return Jwts.parser().setSigningKey(CLAVE.getBytes())
                .parseClaimsJws(token).getBody();
    }

    private boolean validarUsoDeToken(HttpServletRequest request) {
        String autorizacion = request.getHeader("Authorization");

        return autorizacion != null && autorizacion.startsWith("Bearer ");
    }

}
