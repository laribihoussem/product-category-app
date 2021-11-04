package com.example.demo.service;


import com.example.demo.model.Produits;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExportProductService {
    public static ByteArrayInputStream productsPDFReport(List<Produits> products){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document,out);
            document.open();

            //add text to pdf file
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER,14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Products List", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);

            //make colimn titles

            Stream.of("id", "Title", "quantity", "category id", "creation date", "update date").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for(Produits prod: products) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(prod.getId())));
                idCell.setPaddingLeft(1);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idCell);

                PdfPCell titleCell = new PdfPCell(new Phrase(prod.getNom()));
                titleCell.setPaddingLeft(1);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(titleCell);

                PdfPCell qtCell = new PdfPCell(new Phrase(String.valueOf(prod.getQt())));
                qtCell.setPaddingLeft(1);
                qtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                qtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(qtCell);

                PdfPCell descCell = new PdfPCell(new Phrase(String.valueOf(prod.getCategories().getId())));
                descCell.setPaddingLeft(1);
                descCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descCell);

                PdfPCell creationCell = new PdfPCell(new Phrase(String.valueOf(prod.getDate_creation())));
                creationCell.setPaddingLeft(1);
                creationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                creationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(creationCell);

                PdfPCell updatecell = new PdfPCell(new Phrase(String.valueOf(prod.getDate_modif())));
                updatecell.setPaddingLeft(1);
                updatecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                updatecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(updatecell);
            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public static ByteArrayInputStream productsExcelReport(List<Produits> products) throws IOException {
        String[] columns = {"id","nom", "quantity", "disponibility", "creation date", "updating date", "category id"};
            try(Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
                CreationHelper creationHelper = workbook.getCreationHelper();

                Sheet sheet = workbook.createSheet("Products");
                sheet.autoSizeColumn(columns.length);
                org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setColor(IndexedColors.BLUE.getIndex());

                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFont(headerFont);

                // Row for header
                Row headerRow = sheet.createRow(0);

                //Header
                for (int col=0;col <columns.length; col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(columns[col]);
                    cell.setCellStyle(cellStyle);
                }

                CellStyle cellStyle1 = workbook.createCellStyle();
                cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

                int rowIndex = 1;
                for (Produits product: products) {
                    Row row = sheet.createRow(rowIndex++);

                    row.createCell(0).setCellValue(String.valueOf(product.getId()));
                    row.createCell(1).setCellValue(product.getNom());
                    row.createCell(2).setCellValue(String.valueOf(product.getQt()));
                    row.createCell(3).setCellValue(String.valueOf(product.isDisponible()));
                    row.createCell(4).setCellValue(String.valueOf(product.getDate_creation()));
                    row.createCell(5).setCellValue(String.valueOf(product.getDate_modif()));
                    row.createCell(6).setCellValue(String.valueOf(product.getCategories().getId()));

                }
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
    }
}
