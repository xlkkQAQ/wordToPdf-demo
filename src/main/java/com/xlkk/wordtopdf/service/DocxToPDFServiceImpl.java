package com.xlkk.wordtopdf.service;

import com.xlkk.wordtopdf.utils.AsposeUtil;
import com.xlkk.wordtopdf.utils.FileUtil;
import com.xlkk.wordtopdf.utils.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xlkk
 * @date 2022/9/2 0002 14:13
 * @Description:
 */
@Service
public class DocxToPDFServiceImpl {
    public void docxToPdf(HttpServletResponse res){
//        getResourceAsStream()
//        String fileName = "1.docx";
//        String path = "E://desktop";
//        WordUtils.exportByLocalPath(res,fileName,path,null);
        File file = new File("src/main/resources/static/1.docx");
        byte[] b = FileUtil.fileToByte(file);
        try {
            WordUtils.convertDocxToPdf(b,res);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("-------------失败了");
        }
    }


    public void changeByUpload(MultipartFile file, HttpServletResponse res) {
        try {
            byte[] bytes = file.getBytes();
            WordUtils.convertDocxToPdf(bytes,res);
        } catch (Exception e) {
            System.out.println("-------失败了");
        }
    }
    public void changeByAspose(String wordPath, String pdfPath){
        AsposeUtil.wordToPdf(wordPath, pdfPath);
    }
    public void changeByAspose(MultipartFile file,HttpServletResponse res){
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            System.out.println("获取失败");
        }
        AsposeUtil.wordToPdf(inputStream,res);
    }
}
