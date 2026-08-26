package api.file.exporter.factory;

import api.exceptions.BadRequestException;
import api.file.exporter.MediaTypes;
import api.file.exporter.contract.MaterialExporter;
import api.file.exporter.impl.material.CsvExporter;
import api.file.exporter.impl.material.PdfExporter;
import api.file.exporter.impl.material.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public MaterialExporter getExporter(String acceptHeader) throws Exception {
        logger.info("getExporter() got Accept header: [{}]", acceptHeader);
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
            return context.getBean(PdfExporter.class);
        } else {
            throw new BadRequestException("Invalid File Format!");
        }
    }

}
