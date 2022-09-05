package com.xlkk.controller;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.xlkk.service.DocxToPDFServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author xlkk
 * @date 2022/8/31 0031 9:22
 * @Description:
 */
@RestController
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
}
