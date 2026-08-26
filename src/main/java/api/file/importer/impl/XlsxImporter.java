package api.file.importer.impl;

import api.dto.request.MaterialRequestDTO;
import api.file.importer.contract.MaterialImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements MaterialImporter {

    @Override
    public List<MaterialRequestDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next();

            return parseRowsToMaterialDtoList(rowIterator);

        }
    }

    private List<MaterialRequestDTO> parseRowsToMaterialDtoList(Iterator<Row> rowIterator) {
        List<MaterialRequestDTO> materiais = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                materiais.add(parseRowToMaterialDto(row));
            }
        }
        return materiais;
    }



    // Determinar a coluna, por exemplo Coluna 0 = ID, Coluna 1 = Nome
    private MaterialRequestDTO parseRowToMaterialDto(Row row) {
        MaterialRequestDTO materialRequestDTO = new MaterialRequestDTO();
        materialRequestDTO.setNome(row.getCell(1).getStringCellValue());
        return materialRequestDTO;
    }



    private static boolean isRowValid(Row row) {
        return row.getCell(1) != null && row.getCell(1).getCellType() != CellType.BLANK;
    }
}
