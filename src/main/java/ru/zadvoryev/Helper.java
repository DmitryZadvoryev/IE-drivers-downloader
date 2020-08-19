package ru.zadvoryev;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static List<String> createDownloadLinks(String address, List<String> elements) {
        List<String> downloadLinks = new ArrayList<>();
        for (String element : elements) {
            String item = address.concat(element);
            if (item.length() > 55) downloadLinks.add(item);
        }
        return downloadLinks;
    }
}
