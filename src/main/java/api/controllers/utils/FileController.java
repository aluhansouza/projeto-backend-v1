package api.controllers.utils;

import api.controllers.docs.FileControllerDocs;
import api.dto.response.UploadFileResponseDTO;
import api.services.utils.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Endpoint para fazer upload de um único arquivo
     */
    @PostMapping("/uploadFile")
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file,
                                            @RequestParam(value = "subDirectory", defaultValue = "default") String subDirectory) {

        // Armazenar o arquivo na subpasta especificada
        String fileName = fileStorageService.storeFile(file, subDirectory);

        // Gerar a URL de download do arquivo
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/file/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    /**
     * Endpoint para fazer upload de múltiplos arquivos
     */
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                           @RequestParam(value = "subDirectory", defaultValue = "default") String subDirectory) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file, subDirectory))
                .collect(Collectors.toList());
    }

    /**
     * Endpoint para fazer download de um arquivo específico
     */
    @GetMapping("/downloadFile/{subDirectory}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String subDirectory, @PathVariable String fileName, HttpServletRequest request) {
        // Carregar o arquivo como recurso, passando o subdiretório
        Resource resource = fileStorageService.loadFileAsResource(fileName, subDirectory);

        // Determinar o tipo MIME do arquivo
        String contentType = getContentType(resource, request);

        // Preparar e retornar a resposta com o arquivo para download
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Endpoint para obter o caminho de upload configurado
     */
    @GetMapping("/upload-path")
    public String getUploadPath() {
        return fileStorageService.getUploadDirectoryPath();
    }

    /**
     * Método auxiliar para determinar o tipo MIME do arquivo
     */
    private String getContentType(Resource resource, HttpServletRequest request) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.error("Could not determine file type for file: " + resource.getFilename());
        }

        return contentType != null ? contentType : "application/octet-stream";
    }
}