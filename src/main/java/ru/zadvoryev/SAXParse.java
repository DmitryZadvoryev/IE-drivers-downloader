package ru.zadvoryev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXParse extends DefaultHandler {

    private static Logger log = LoggerFactory.getLogger(SAXParse.class);

    private List<String> results = new ArrayList();

    private String element = null;

    @Override
    public void startDocument() throws SAXException {
        log.info("Parser Start....");
    }

    @Override
    public void endDocument() throws SAXException {
        log.info("Parser End....");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        element = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (element.equals("Key")) {
            results.add(new String(ch, start, length));
        }
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

}
