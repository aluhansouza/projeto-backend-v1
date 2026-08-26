package api.controllers.docs;

import api.dto.request.auth.LoginRequestDTO;
import api.dto.response.auth.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface AuthControllerDocs {

    @Operation(summary = "Autentica o usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário ou senha inválidos",
                    content = @Content
            )
    })
    ResponseEntity<LoginResponseDTO> login(
            @RequestBody(
                    description = "Credenciais do usuário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDTO.class)
                    )
            )
            LoginRequestDTO loginRequest
    );
}