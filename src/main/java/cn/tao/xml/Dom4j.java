package cn.tao.xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class Dom4j {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Admin\\Desktop\\demo\\src\\main\\recourse\\test.xml");
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        if (document != null) {
            System.out.println(document.getXMLEncoding());
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            List<Attribute> attributes = rootElement.attributes();
            for (Attribute attribute : attributes) {
                System.out.println(attribute.getName());
                System.out.println(attribute.getValue());
            }

            for (Element element : elements) {
                List<Attribute> lsitAttribute =element.attributes();

                for (Attribute attribute : lsitAttribute) {
                    System.err.println(attribute.getName());
                    System.err.println(attribute.getValue());
                }

                List<Element> elementS = element.elements();
                for (Element element1 : elementS) {
                    System.out.println(element1.getName());
                    System.out.println(element1.getStringValue());
                }

            }

        }
    }
}
