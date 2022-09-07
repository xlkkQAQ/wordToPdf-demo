package com.xlkk.wordtopdf.controller;

import com.xlkk.pdftoword.utils.PDFHelper3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xlkk
 * @date 2022/9/7 0007 14:03
 * @Description:
 */
@RestController
@Slf4j
public class PdfToWordController {
    @PostMapping("/pdfToWord")
    public void pdfToWord(@RequestParam("file") MultipartFile file, HttpServletResponse res){
        try {
            InputStream in = file.getInputStream();
            PDFHelper3.pdf2doc(in,res);
            log.info("成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
