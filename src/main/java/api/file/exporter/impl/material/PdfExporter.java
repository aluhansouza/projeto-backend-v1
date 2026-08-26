package api.file.exporter.impl.material;

import api.dto.response.MaterialResponseDTO;
import api.file.exporter.contract.MaterialExporter;
import api.services.utils.QRCodeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements MaterialExporter {

    @Autowired
    private QRCodeService service;

    @Override
    public Resource exportMateriais(List<MaterialResponseDTO> equipamentos) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/materiais.jrxml");
        if (inputStream == null) {
            throw new RuntimeException("Template file not found: /templates/materiais.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(equipamentos);
        Map<String, Object> parameters = new HashMap<>();
        // parameters.put("title", "Equipamento Report");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }


    @Override
    public Resource exportMaterial(MaterialResponseDTO materialResponseDTO) throws Exception {

        InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/materiais.jrxml");
        if (mainTemplateStream == null) {
            throw new RuntimeException("Template file not found: /templates/materiais.jrxml");
        }

        /*InputStream subReportStream = getClass().getResourceAsStream("/templates/books.jrxml");
        if (subReportStream == null) {
            throw new RuntimeException("Template file not found: /templates/books.jrxml");
        }*/

        JasperReport mainReport = JasperCompileManager.compileReport(mainTemplateStream);
        //JasperReport subReport = JasperCompileManager.compileReport(subReportStream);

        InputStream qrCodeStream = service.generateQRCode(materialResponseDTO.getQrValor(), 200, 200);

        //JRBeanCollectionDataSource subReportDataSource = new JRBeanCollectionDataSource(person.getBooks());

        //String path = getClass().getResource("/templates/books.jasper").getPath();

        Map<String, Object> parameters = new HashMap<>();
        //parameters.put("SUB_REPORT_DATA_SOURCE", subReportDataSource);
        //parameters.put("BOOK_SUB_REPORT", subReport);
        //parameters.put("SUB_REPORT_DIR", path);
        parameters.put("QRCODE_IMAGEM", qrCodeStream);

        JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singletonList(materialResponseDTO));

        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
