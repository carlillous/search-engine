package crawler.impl;

import crawler.messages.MessageSender;
import datalake.cloud.CloudDataLake;
import java.util.Optional;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Downloader {
    private final CloudDataLake dataLake;
    private static final Logger logger = LoggerFactory.getLogger(Downloader.class);

    public Downloader(CloudDataLake dataLake){
        this.dataLake = dataLake;
    }

    public void run() {
        int id = getRandomBookIndex();
        String fileName = id + ".txt";
        try {
            String bookUrl = "https://www.gutenberg.org/cache/epub/" + id + "/pg" + fileName;
            if (dataLake.contains(fileName)) {
                logger.error("Book already downloaded: " + fileName);
            } else {
                Optional<String> optData = download(bookUrl);
                if (optData.isPresent()) {
                    String data = optData.get();
                    save(fileName, data);
                }
            }
        } catch (Exception e) {
            logger.error("Exception: " + e);
        }
    }

    private void save(String name, String data) {
        logger.info("Saving data: " + name + "...");
        dataLake.save(name, data);
        logger.info("Done!");

        logger.info("Sending message: " + name + "...");
        MessageSender.sendMessage(name);
        logger.info("Done!");
    }

    private int getRandomBookIndex() {
        Random random = new Random();
        return random.nextInt(70000) + 1;
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