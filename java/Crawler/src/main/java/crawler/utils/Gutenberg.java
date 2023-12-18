package crawler.utils;

import java.util.Random;

public class Gutenberg {
    public static int getRandomBookId() {
        Random random = new Random();
        return random.nextInt(70000) + 1;
    }

    public static String getFileName(int id) {
        return id + ".txt";
    }

    public static String getUrl(int id) {
        return "https://www.gutenberg.org/cache/epub/" + id + "/pg" + getFileName(id);
    }
}
