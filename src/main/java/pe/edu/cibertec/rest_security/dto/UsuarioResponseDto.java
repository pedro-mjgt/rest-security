package pe.edu.cibertec.rest_security.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioResponseDto {

    private Integer id;
    private String usuario;
    private String token;

}
