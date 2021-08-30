package com.ly.rep;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ly
 * @create 2021/3/17 - 20:13
 */
@RestController
@RequestMapping("/test")
public class TextProcess {
    public static void main(String[] args) {
//C:\Users\le\Desktop
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse("C:\\Users\\le\\Desktop\\note_error.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList note = document.getElementsByTagName("note");
        //5.进行xml信息获取
        for(int i=0;i<note.getLength();i++){
            Element e = (Element)note.item(i);
            System.out.println("姓名："+
                    e.getElementsByTagName("to").item(0).getFirstChild().getNodeValue());
            System.out.println("邮箱："+
                    e.getElementsByTagName("from").item(0).getFirstChild().getNodeValue());
        }

    }

    public static void process(String str){
        System.out.println("origin");
        System.out.println(str);

        //String pattern = "Date\\(D\\/M\\/Y\\)";
        String pattern = "(\\d{2}\\/\\d{2}\\/\\d{4})(Date\\(D\\/M\\/Y\\))";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.find());

        for (int i = 0; i <= m.groupCount(); i++) {
            System.out.println(m.group(i));
        }

        String replace = str.replace(m.group(0), m.group(2) + m.group(1));

        System.out.println(replace);


    }


    @RequestMapping("/testJson")
    public List<JsonNode> testXmlToJson() throws IOException {



        XmlMapper m = new XmlMapper();
        String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!-- Edited with XML Spy v2007 (http://www.altova.com) -->\n" +
                "<note>\n" +
                "\t<to>George</to>\n" +
                "\t<from>John</from>\n" +
                "\t<heading>Reminder</heading>\n" +
                "\t<body>Don't forget the meeting!</body>\n" +
                "</note>\n";

        List<JsonNode> list = new ArrayList<JsonNode>();
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();

            JsonNode jsonNode = m.readTree(str);

            String s = jsonNode.toString();

            System.out.println(s);

            long end = System.currentTimeMillis();

            System.out.println((i+1)+":"+(end-start));

        }

        return list;
    }
}
