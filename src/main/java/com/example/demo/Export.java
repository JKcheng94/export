package com.example.demo;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class Export{
    
    @PostMapping("/export")
    public void exportXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElement("Voucher");
        doc.appendChild(root);
        
        Element id = doc.createElement("Id");
        id.setTextContent("111");
        root.appendChild(id);
        
        Element name = doc.createElement("Name");
        name.setTextContent("222");
        root.appendChild(name);
    
        String fileName = "voucher_";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        fileName = fileName + format.format(new Date());
        Random random = new Random();
        for (int i = 0; i < 3; i++)
            fileName = fileName + random.nextInt(10);
        fileName = fileName + ".xml";
//        File file = new File(fileName);
    
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result1 = new StreamResult(writer);
        TransformerFactory tf1 = TransformerFactory.newInstance();
        Transformer transformer1 = tf1.newTransformer();
        transformer1.transform(domSource, result1);
    
        response.setContentType("application/xml ; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getWriter().write(writer.toString());
    }
}