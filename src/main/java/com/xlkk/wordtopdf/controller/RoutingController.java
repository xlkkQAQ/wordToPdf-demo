package com.xlkk.wordtopdf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xlkk
 * @date 2022/9/5 0005 14:37
 * @Description:
 */
@Controller
public class RoutingController {
    @RequestMapping("/t1")
    public String home1(){
        return "pdfToWord";
    }
    @RequestMapping("/t2")
    public String home2(){
        return "wordToPdf";
    }
}
