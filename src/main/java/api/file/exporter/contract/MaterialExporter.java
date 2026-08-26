package api.file.exporter.contract;

import api.dto.response.MaterialResponseDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface MaterialExporter {

    Resource exportMateriais(List<MaterialResponseDTO> materiais) throws Exception;

    Resource exportMaterial(MaterialResponseDTO material) throws Exception;
}