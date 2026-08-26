package api.services.utils;

import api.config.FileStorageConfig;
import api.exceptions.FileNotFoundException;
import api.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDirMateriais()).toAbsolutePath().normalize();
        this.fileStorageLocation = path;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could not create the directory where files will be stored!");
            throw new FileStorageException("Could not create the directory where files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file, String subDirectory) {
        // Verificar se o subDirectory é válido
        if (subDirectory == null || subDirectory.isEmpty() || subDirectory.contains("..")) {
            subDirectory = "default"; // Define "default" como subdiretório padrão
        }

        // Normaliza o subdiretório e garante que ele seja válido
        Path subDirectoryPath = this.fileStorageLocation.resolve(subDirectory).normalize();

        // Garante que o subdiretório exista
        try {
            Files.createDirectories(subDirectoryPath);
        } catch (Exception e) {
            logger.error("Could not create the subdirectory: " + subDirectory);
            throw new FileStorageException("Could not create the subdirectory: " + subDirectory, e);
        }

        // Gera um nome único para o arquivo
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
            if (uniqueFileName.contains("..")) {
                logger.error("Invalid file path: " + uniqueFileName);
                throw new FileStorageException("Invalid file path: " + uniqueFileName);
            }

            logger.info("Saving file in Disk");
            Path targetLocation = subDirectoryPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (Exception e) {
            logger.error("Could not store file " + uniqueFileName + ". Please try Again!");
            throw new FileStorageException("Could not store file " + uniqueFileName + ". Please try Again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName, String subDirectory) {
        // Validação do subdiretório
        if (subDirectory == null || subDirectory.isEmpty() || subDirectory.contains("..")) {
            subDirectory = "default";  // Definir um valor padrão, caso o subdiretório seja inválido
        }

        try {
            // Resolver o caminho do arquivo incluindo o subdiretório
            Path filePath = this.fileStorageLocation.resolve(subDirectory).resolve(fileName).normalize();

            // Criar o recurso do arquivo
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;  // Retorna o recurso se o arquivo existir
            } else {
                logger.error("File not found " + fileName);
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e) {
            logger.error("File not found " + fileName);
            throw new FileNotFoundException("File not found " + fileName, e);
        }
    }

    public void deleteFile(String fileName, String subDirectory) {
        try {
            Path filePath = this.fileStorageLocation.resolve(subDirectory).resolve(fileName).normalize();
            Files.deleteIfExists(filePath);  // Deleta o arquivo, se ele existir
        } catch (IOException e) {
            logger.error("Erro ao excluir o arquivo: " + fileName);
            throw new FileStorageException("Erro ao excluir o arquivo " + fileName, e);
        }
    }

    public String getUploadDirectoryPath() {
        return this.fileStorageLocation.toString();
    }
}