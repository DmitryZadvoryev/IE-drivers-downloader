package ru.zadvoryev;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Downloader {

    private static Logger log = LoggerFactory.getLogger(Downloader.class);

    private int count = 0;
    private double size = 0;

    public Downloader() {
    }

    public void downloadAndSave(String rootDownloadPath, List<String> downloadLinks) {
        for (int i = 0; i < downloadLinks.size(); i++) {
            String downloadLink = downloadLinks.get(i);
            String key = downloadLink.substring(47).replace("/", "\\");
            Path versionDirectory = Paths.get(rootDownloadPath.concat(getVersion(key)));
            createDirectoryIfNotExist(versionDirectory);
            Path downloadPath = fullDownloadPath(rootDownloadPath, key);
            try {
                log.info("Download STARTED {}", key);
                download(downloadPath, new URI(downloadLink));
                count++;
                size = (double) Files.size(downloadPath) / (1024 * 1024);
                log.info("Download ENDED {}", key);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("DOWNLOAD FINISHED : Downloaded {} files,  {} megabytes", count, size);
    }

    private Path fullDownloadPath(String rootDownloadPath, String key) {
        return Paths.get(rootDownloadPath + key);
    }

    private void createDirectoryIfNotExist(Path versionDirectory) {
        if (!Files.exists(versionDirectory)) {
            try {
                Files.createDirectories(versionDirectory);
                log.info("\n");
                log.info("===================================");
                log.info("Create folder {} ", versionDirectory);
                log.info("Downloaded {} files,  {} megabytes", count, size);
                log.info("===================================\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getVersion(String item) {
        int index = item.indexOf("\\", 2);
        String versionDir = item.substring(1, index);
        return versionDir;
    }

    private void download(Path downloadPath, URI uri) {
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(new HttpGet(uri));
             InputStream inputStream = response.getEntity().getContent()) {
            Files.copy(inputStream, downloadPath);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
