package com.xlkk.pdftoword.utils;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PDFHelper3 {

    public static void main(String[] args) throws IOException {
//        pdf2doc("E:\\desktop\\龙康.pdf");
        doc2pdf("E:\\desktop\\龙康.docx");
    }
    public static void doc2pdf(String pdfPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".pdf";
            FileOutputStream os = new FileOutputStream(wordPath);
            //doc是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PdfXml);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }

    //pdf转doc
    public static void pdf2doc(String pdfPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
            FileOutputStream os = new FileOutputStream(wordPath);
            //doc是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }

    public static void pdf2doc(InputStream in, HttpServletResponse response) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
//            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
//            FileOutputStream os = new FileOutputStream(wordPath);
            String fileName = "xlkk";
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".docx");

            ServletOutputStream os = response.getOutputStream();
            //doc是将要被转化的word文档
            Document doc = new Document(in);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }
    public static void doc2pdf(InputStream in, HttpServletResponse response) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
//            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
//            FileOutputStream os = new FileOutputStream(wordPath);
            String fileName = "xlkk";
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
            ServletOutputStream os = response.getOutputStream();
            //doc是将要被转化的word文档
            Document doc = new Document(in);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.Pdf);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Word 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Word 转 Pdf 失败...");
            e.printStackTrace();
        }
    }

}


