package com.xlkk.wordtopdf.controller;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.xlkk.pdftoword.utils.PDFHelper3;
import com.xlkk.wordtopdf.service.DocxToPDFServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xlkk
 * @date 2022/8/31 0031 9:22
 * @Description:
 */
@RestController
@Slf4j
public class TurnController {
    @Autowired
    DocxToPDFServiceImpl docxToPDFService;
    @PostMapping("/turn")
    public String turn(){
        IConverter converter = LocalConverter.builder().build();
        boolean execute = converter.convert(new File("E://desktop//1.docx"))
                .as(DocumentType.DOCX)
                .to(new File("E://desktop//1.pdf"))
                .as(DocumentType.PDF).execute();
        converter.shutDown();
        if(execute){
            return "success";
        }else {
            return "failure";
        }
    }
    @GetMapping("/change")
    public void docxToPdf(HttpServletResponse res){
        docxToPDFService.docxToPdf(res);
    }

    @PostMapping("/changeByUpload")
    public void docxToPdfByUpload(@RequestParam("file") MultipartFile file, HttpServletResponse res){
        docxToPDFService.changeByUpload(file,res);
    }

    @PostMapping("/changeByAspose")
    public void docxToPdfByAspose(@RequestParam("file") MultipartFile file, HttpServletResponse res){
//        docxToPDFService.changeByAspose(file,res);
        try {
            PDFHelper3.doc2pdf(file.getInputStream(),res);
        } catch (IOException e) {
            System.out.println("失败");
        }
    }



}
