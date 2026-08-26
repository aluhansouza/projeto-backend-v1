package api.controllers;

import api.controllers.docs.MaterialControllerDocs;
import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import api.file.exporter.MediaTypes;
import api.services.interfaces.MaterialService;
import api.services.utils.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/materiais")
@Tag(name = "Materiais", description = "Endpoints para Materiais")
public class MaterialController implements MaterialControllerDocs {


    @Autowired
    private MaterialService materialService;

    @Autowired
    private FileStorageService serviceStorage;


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<MaterialResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "200000000") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(materialService.listar(pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(value = "/buscarPorNome/{nome}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<MaterialResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "200000000") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(materialService.buscarPorNome(nome, pageable));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public MaterialResponseDTO buscarPorId(@PathVariable("id") Long id) {
        return materialService.buscarPorId(id);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping(
            consumes =  {MediaType.MULTIPART_FORM_DATA_VALUE}
            ,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public MaterialResponseDTO cadastrar(@RequestPart("material") MaterialRequestDTO materialRequestDTO,
                                         @RequestPart(value = "imagem", required = false) MultipartFile imagem) {


        return materialService.cadastrar(materialRequestDTO, imagem);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public MaterialResponseDTO atualizar(
            @PathVariable Long id,  // O ID será passado como parâmetro de URL
            @RequestPart("material") MaterialRequestDTO dtoRequest,  // O DTO com os dados atualizados
            @RequestPart(value = "imagem", required = false) MultipartFile imagem  // O arquivo de imagem, opcional
    ) {

        return materialService.atualizar(id, dtoRequest, imagem);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        materialService.excluir(id);
        return ResponseEntity.noContent().build();
    }


    // -----------------------------------------------------------------------

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(value = "/exportarPagina", produces = {
            MediaTypes.APPLICATION_XLSX_VALUE,
            MediaTypes.APPLICATION_CSV_VALUE,
            MediaTypes.APPLICATION_PDF_VALUE})
    @Override
    public ResponseEntity<Resource> exportarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = materialService.exportarPagina(pageable, acceptHeader);

      //Obter o timestamp atual e formatar como string (ex.: 20250601_153045)
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = agora.format(formatter);

        Map<String, String> extensionMap = Map.of(
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypes.APPLICATION_CSV_VALUE, ".csv",
                MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
        );

        var fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";

        var filename = "material_exportado_" + timestamp + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping(value = "/massCreation",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<MaterialResponseDTO> massCreation(@RequestParam("file") MultipartFile file) {
        return materialService.massCreation(file);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping(value = "/exportar/{id}",
            produces = {
                    MediaType.APPLICATION_PDF_VALUE}
    )
    @Override
    public ResponseEntity<Resource> exportar(@PathVariable("id") Long id, HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        Resource file = materialService.exportMaterial(id, acceptHeader);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=material.pdf")
                .body(file);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/verificar-conexao")
    public ResponseEntity<String> verificarConexao() {
        return ResponseEntity.ok("Conexão bem-sucedida");
    }






@GetMapping("/imagem/{nomeArquivo:.+}")
public ResponseEntity<Resource> servirImagem(@PathVariable String nomeArquivo, HttpServletRequest request) {
    // Carrega o arquivo da pasta "materiais"
    Resource resource = serviceStorage.loadFileAsResource(nomeArquivo, "materiais");

    // Tenta determinar o tipo do conteúdo
    String contentType = request.getServletContext().getMimeType(resource.getFilename());
    if (contentType == null) {
        contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
}





}
