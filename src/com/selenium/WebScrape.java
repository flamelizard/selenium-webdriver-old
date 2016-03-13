package com.selenium;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

/**
 * Created by Tom on 3/12/2016.
 */

/*
Jsoup - answer to python's Beautiful soup

Powerful Open Source HTML parser, Java hasn't got any HTML parser part of
Java API. This one is best.

Using XML parser is bad idea since HTML has to be well-formed e.g closed tags,
otherwise parsing terminates with exception.
 */
public class WebScrape {
    private String URL;

    public WebScrape(String URL) {
        this.URL = URL;
    }

    public static void main(String[] args) throws Exception {
        WebScrape parser = new WebScrape("http://root.cz");
        parser.printElementLineage();
    }

    public void printElementLineage() throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect(URL).get();
        Elements nodes = doc.getElementsByClass("impressum__author");

        for (Element node : nodes) {
            System.out.println("[elem] " + node.toString());
            System.out.println(node.cssSelector());
        }

//        alternative approach for single node
        Elements parents = nodes.last().parents();
        for (Element par : parents) {
            System.out.println(String.format("[parent] %s %s", par.tagName(),
                    par.className()));
        }
    }

    public void parseHTMLWithXMLParser() throws
            IOException, ParserConfigurationException, SAXException {
        URL url = new URL(URL);

//        Download web page through Java API URL class
        BufferedReader bin = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String html = "";
        String line = "";
        while (line != null) {
            line = bin.readLine();
            html += line;
        }
        bin.close();

        /*
        DOM parser accepts only file stream or InputSource, not a string

        InputSource is SAX parser wrapper that will intelligently determine
        how to read input based on its type
         */
        InputSource htmlInput = new InputSource(new StringReader(html));

        DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = parser.parse(htmlInput);

//        never reach here as parser will fail not malformed HTML input
        System.out.println(doc.getDocumentURI());
    }


}
