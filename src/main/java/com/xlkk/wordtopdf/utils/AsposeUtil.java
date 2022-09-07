package com.xlkk.wordtopdf.utils;

import com.aspose.words.DocSaveOptions;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AsposeUtil {
    public static void main(String[] args) {
        pdfToWord("E:\\desktop\\龙康.pdf","E:\\desktop\\龙康.docx");
    }

    /**
     * 加载license 用于破解 不生成水印
     */
    @SneakyThrows
    private static void getLicense() {
        try (InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("License.xml")) {
            License license = new License();
            license.setLicense(is);
        }
    }

    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath) {
        getLicense();
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
        }catch (Exception e){
            System.out.println("失败了");
        }
    }
    @SneakyThrows
    public static void pdfToWord(String wordPath, String pdfPath) {
        getLicense();
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            DocSaveOptions saveOptions = new DocSaveOptions();
//            saveOptions.setMode(RecognitionMode.Flow);
//            saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.DOCX);
        }catch (Exception e){
            System.out.println("失败了");
        }
    }
    @SneakyThrows
    public static void wordToPdf(InputStream in, HttpServletResponse response) {
        getLicense();
        response.setContentType("application/pdf");
        Document doc = new Document(in);
        String fileName = doc.getOriginalFileName();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        doc.save(response.getOutputStream(), SaveFormat.PDF);

    }
    @SneakyThrows
    public static void pdfToDocx(InputStream in, HttpServletResponse response) {
        getLicense();
        response.setContentType("application/docx");
        Document doc = new Document(in);
        String fileName = doc.getOriginalFileName();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".docx");
        doc.save(response.getOutputStream(), SaveFormat.DOCX);

    }
}
