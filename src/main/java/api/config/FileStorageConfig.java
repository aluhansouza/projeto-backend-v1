package api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("/projeto/uploads/materiais/")
    private String uploadDirMateriais;

    public FileStorageConfig() {}

    public String getUploadDir() {
        return uploadDirMateriais;
    }

    public String getUploadDirMateriais() {
        return uploadDirMateriais;
    }

    public void setUploadDir(String uploadDirMateriais) {
        this.uploadDirMateriais = uploadDirMateriais;
    }
}
