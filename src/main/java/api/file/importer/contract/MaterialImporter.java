package api.file.importer.contract;

import api.dto.request.MaterialRequestDTO;

import java.io.InputStream;
import java.util.List;

public interface MaterialImporter {

    List<MaterialRequestDTO> importFile(InputStream inputStream) throws Exception;
}
