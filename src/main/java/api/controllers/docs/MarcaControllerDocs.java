package api.controllers.docs;

import api.dto.request.MarcaRequestDTO;
import api.dto.response.MarcaResponseDTO;
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

public interface MarcaControllerDocs {

    @Operation(summary = "Listar todos as marcas",
            description = "Retorna todas as marcas de forma paginada",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarcaResponseDTO.class)))),
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<PagedModel<EntityModel<MarcaResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Buscar marca por nome",
            description = "Retorna marcas com nome semelhante (filtro paginado)",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarcaResponseDTO.class)))),
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<PagedModel<EntityModel<MarcaResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Buscar marca por ID",
            description = "Retorna os dados de um marca específico",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = MarcaResponseDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    MarcaResponseDTO buscarPorId(@PathVariable("id") Long id);

    @Operation(summary = "Cadastrar novo marca",
            description = "Cria um novo marca",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = MarcaResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    MarcaResponseDTO cadastrar(MarcaRequestDTO requestDTO);

    @Operation(summary = "Atualizar marca",
            description = "Atualiza os dados de um marca existente",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = MarcaResponseDTO.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    MarcaResponseDTO atualizar(@PathVariable("id") Long id, MarcaRequestDTO requestDTO);

    @Operation(summary = "Excluir marca",
            description = "Remove um marca pelo ID",
            tags = {"Marcas"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    ResponseEntity<?> excluir(@PathVariable("id") Long id);
}
