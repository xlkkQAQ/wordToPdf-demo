package com.xlkk.service;

import com.xlkk.utils.FileUtil;
import com.xlkk.utils.WordUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

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




}
