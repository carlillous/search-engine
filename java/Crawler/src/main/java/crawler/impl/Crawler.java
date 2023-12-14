package crawler.impl;

import crawler.utils.Gutenberg;
import crawler.messages.MessageSender;
import datalake.cloud.DataLake;
import java.util.Optional;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
    private final DataLake dataLake;
    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

    public Crawler(DataLake dataLake){
        this.dataLake = dataLake;
    }

    public void run() {
        int id = Gutenberg.getRandomBookId();
        String fileName = Gutenberg.getFileName(id);
        logger.info("To download: " + fileName);
        String bookUrl = Gutenberg.getUrl(id);
        if (dataLake.contains(fileName)) {
            logger.error("Book already downloaded: " + fileName);
        } else {
            try {
                Optional<String> optData = download(bookUrl);
                if (optData.isPresent()) {
                    String data = optData.get();
                    save(fileName, data);
                }
            } catch (Exception e) {
                logger.error("Exception: " + e);
            }
        }
        logger.info("Done: " + fileName);
    }

    private void save(String name, String data) {
        logger.info("Saving data: " + name + "...");
        dataLake.save(name, data);
        logger.info("Done!");

        MessageSender.sendMessage(name);
        logger.info("Done!");
    }


    private Optional<String> download(String url) {
        try {
            logger.info("Downloading " + url + "...");
            Connection.Response response = Jsoup.connect(url).timeout(30000).ignoreContentType(true)
                    .execute();
            logger.info("Done!");
            return Optional.of(response.parse().wholeText());
        } catch (IOException e) {
            logger.error("Exception: " + e);
            return Optional.empty();
        }
    }
}