package api.file.importer.impl;

import api.dto.request.MaterialRequestDTO;
import api.file.importer.contract.MaterialImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements MaterialImporter {

    @Override
    public List<MaterialRequestDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToMateriaisDTOs(records);
    }

    private List<MaterialRequestDTO> parseRecordsToMateriaisDTOs(Iterable<CSVRecord> records) {
        List<MaterialRequestDTO> materiais = new ArrayList<>();

        for (CSVRecord record : records) {
            MaterialRequestDTO materialRequestDTO = new MaterialRequestDTO();
            materialRequestDTO.setNome(record.get("nome"));

            materiais.add(materialRequestDTO);
        }
        return materiais;
    }
}
