package com.xlkk.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.springframework.boot.info.OsInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class WordUtils {
    public static <T> void exportByLocalPath(HttpServletResponse response, String fileName, String path, Map<String,String> params){
        try  {
            File file = new File("src/main/resources/static/1.docx");
            String p = file.getCanonicalPath();
            InputStream in = new FileInputStream(p);
            convertDocxToPdf(in, response,fileName,params);
        } catch (Exception e) {
            log.error("docx文档转换为PDF失败", e.getMessage());
        }
    }
    /**
     * docx文档转换为PDF
     * @param in
     * @param response
     * @return
     */
    public static void convertDocxToPdf(InputStream in, HttpServletResponse response, String fileName, Map<String,String> params) throws Exception {
        response.setContentType("application/pdf");
        String fullFileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-disposition", "attachment;filename=" + fullFileName + ".pdf");
        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.load(in);
//        if (params!=null&&!params.isEmpty()) {
//            MainDocumentPart documentPart = wmlPackage.getMainDocumentPart();
////            cleanDocumentPart(documentPart);
//            documentPart.variableReplace(params);
//        }
        setFontMapper(wmlPackage);
        Docx4J.toPDF(wmlPackage,response.getOutputStream());
    }

    /**
     * 单纯的转pdf，不需要属性填充
     * @param in
     * @param response
     * @param fileName
     * @throws Exception
     */
    public static void convertDocxToPdf(InputStream in, HttpServletResponse response, String fileName) throws Exception {
        response.setContentType("application/pdf");
        String fullFileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-disposition", "attachment;filename=" + fullFileName + ".pdf");
        WordprocessingMLPackage wmlPackage = WordprocessingMLPackage.load(in);
        setFontMapper(wmlPackage);
        Docx4J.toPDF(wmlPackage,response.getOutputStream());
    }


    private static void setFontMapper(WordprocessingMLPackage mlPackage) throws Exception {
        Mapper fontMapper = new IdentityPlusMapper();
        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
        fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
        fontMapper.put("MSYH", PhysicalFonts.get("Microsoft Yahei"));
        fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
        fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
        fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
        fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
        fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
        fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
        fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
        fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
        fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
        fontMapper.put("CALIBRI", PhysicalFonts.get("CALIBRI"));
        mlPackage.setFontMapper(fontMapper);
    }

    public static void main(String[] args) throws Exception {
        WordUtils.convertDocxToPdf("src/main/resources/static/1.docx","src/main/resources/static/1.pdf");
    }

    /**
     * docx文档转换为PDF
     * @param body 文档
     * @param response 响应给前端
     * @return pdf 输出流
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPdf(byte[] body , HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        File docxFile = FileUtil.byteToFile(body, UUID.randomUUID().toString() + ".docx");
        try {
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(docxFile);
            setFontMapper(mlPackage);
            Docx4J.toPDF(mlPackage, response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
            log.error("docx文档转换为PDF失败");
        }
        FileUtil.deleteTempFile(docxFile);
    }

    /**
     * docx文档转换为PDF
     *
     * @param pdfPath PDF文档存储路径
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPdf(String docxPath, String pdfPath) throws Exception {

        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(docxPath);
            fileOutputStream = new FileOutputStream(new File(pdfPath));
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(file);
            setFontMapper(mlPackage);
            Docx4J.toPDF(mlPackage, new FileOutputStream(new File(pdfPath)));
        }catch (Exception e){
            e.printStackTrace();
            log.error("docx文档转换为PDF失败");
        }finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

}

