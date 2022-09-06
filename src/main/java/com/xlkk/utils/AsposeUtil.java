package com.xlkk.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AsposeUtil {
    public static void main(String[] args) {
        wordToPdf("E:\\desktop\\龙康-4.docx","E:\\desktop\\heihei.pdf");
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
}
