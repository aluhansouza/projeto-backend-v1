package api.file.exporter.impl.material;

import api.dto.response.MaterialResponseDTO;
import api.file.exporter.contract.MaterialExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements MaterialExporter {

    @Override
    public Resource exportMateriais(List<MaterialResponseDTO> materiais) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "Nome")
                .setSkipHeaderRecord(false)
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
            for (MaterialResponseDTO material : materiais) {
                csvPrinter.printRecord(
                        material.getId(),
                        material.getNome()
                );
            }

        }
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public Resource exportMaterial(MaterialResponseDTO equipamento) throws Exception {
        return null;
    }
}
