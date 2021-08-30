package com.ly.rep;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.ly.rep.escap.CustomizedXMLEscapingWriterFactory;
import org.codehaus.stax2.XMLOutputFactory2;
import org.junit.Test;

import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ly
 * @create 2021/3/21 - 15:03
 */
public class TestXmlToJson {

    @Test
    public void test01() throws IOException {
        XmlMapper m = new XmlMapper();
        String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<!-- Edited with XML Spy v2007 (http://www.altova.com) -->\n" +
                "<note>\n" +
                "\t<to>George</to>\n" +
                "\t<from>John</from>\n" +
                "\t<heading>Reminder</heading>\n" +
                "\t<body>Don't forget the meeting!</body>\n" +
                "</note>\n";

            long start = System.currentTimeMillis();

            JsonNode jsonNode = m.readTree(str);

        String s = jsonNode.toString();

        System.out.println("s = " + s);

        String john = s.replaceAll("John", "& < > \r\n 123\t123");

        System.out.println("rep = " + john);

        long end = System.currentTimeMillis();

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);

        JsonNode tree = objectMapper.readTree(john);

        String s2 = tree.toString();


        System.out.println("s2 = " + s2);

        ObjectWriter root = m.writer().withRootName("root");

        String s1 = root.writeValueAsString(tree);

        System.out.println("s1 = " + s1);

    }

    @Test
    public void test02() throws IOException {
        XmlMapper m = new XmlMapper();
        String str ="\t<from>John</from>\n";


        long start = System.currentTimeMillis();

        JsonNode jsonNode = m.readTree(str);

        String s = jsonNode.toString();

        System.out.println("s = " + s);

        String john = s.replaceAll("John", "& < > \r\n 123\t123");

        System.out.println("rep = " + john);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);

        JsonNode tree = objectMapper.readTree(john);

        //JacksonUtils.jsonLeaf(tree);

        XMLOutputFactory xmlOutputFactory = m.getFactory().getXMLOutputFactory();
        xmlOutputFactory.setProperty(XMLOutputFactory2.P_ATTR_VALUE_ESCAPER,new CustomizedXMLEscapingWriterFactory());
        xmlOutputFactory.setProperty(XMLOutputFactory2.P_TEXT_ESCAPER, new CustomizedXMLEscapingWriterFactory());

        String root = m.writer().withRootName("root").writeValueAsString(tree);

        System.out.println("root = " + root);

        String value = m.writeValueAsString(tree);

        System.out.println("value = " + value);


    }

}
