package api.controllers.docs;

import api.dto.request.OrigemRequestDTO;
import api.dto.response.OrigemResponseDTO;
import api.dto.response.SetorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrigemControllerDocs {

    @Operation(summary = "Listar todos os setores",
            description = "Retorna todos os setores de forma paginada",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SetorResponseDTO.class)))),
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<PagedModel<EntityModel<OrigemResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Buscar setor por nome",
            description = "Retorna setores com nome semelhante (filtro paginado)",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SetorResponseDTO.class)))),
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<PagedModel<EntityModel<OrigemResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Buscar setor por ID",
            description = "Retorna os dados de um setor específico",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SetorResponseDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    OrigemResponseDTO buscarPorId(@PathVariable("id") Long id);

    @Operation(summary = "Cadastrar novo setor",
            description = "Cria um novo setor",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = SetorResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    OrigemResponseDTO cadastrar(OrigemRequestDTO requestDTO);

    @Operation(summary = "Atualizar setor",
            description = "Atualiza os dados de um setor existente",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SetorResponseDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    OrigemResponseDTO atualizar(@PathVariable("id") Long id, OrigemRequestDTO requestDTO);

    @Operation(summary = "Excluir setor",
            description = "Remove um setor pelo ID",
            tags = {"Origens"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<?> excluir(@PathVariable("id") Long id);
}
