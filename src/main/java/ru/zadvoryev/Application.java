package ru.zadvoryev;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

import static ru.zadvoryev.Helper.createDownloadLinks;

public class Application {

    public static void main(String[] args) {

        Downloader downloader = new Downloader();
        String link = Property.get("site");
        String rootDownloadPath = Property.get("root.download.path");
        List<String> downloadLinks = createDownloadLinks(link, parse(link));
        downloader.downloadAndSave(rootDownloadPath, downloadLinks);
    }


    private static List<String> parse(String address) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        List<String> results = null;
        try {
            parser = factory.newSAXParser();
            SAXParse saxp = new SAXParse();
            parser.parse(address, saxp);
            results = saxp.getResults();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
