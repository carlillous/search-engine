import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Descargar cada minuto y antes del nombre del libro que se descargue y ordene por fecha.
public class GutenbergScraper {

    public GutenbergScraper(){
        try {
            String url = "https://www.gutenberg.org/ebooks/search/?sort_order=downloads";
            Document doc = Jsoup.connect(url).userAgent("Chrome/5.0").get();
            Elements links = doc.select("li.booklink h3 a");
            for (Element link : links) {
                String bookUrl = "https://www.gutenberg.org" + link.attr("href");
                downloadBook(bookUrl);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadBook(String bookUrl) {
        try {
            Document doc = Jsoup.connect(bookUrl).get();

            Element downloadLink = doc.selectFirst("a[href^=\"/ebooks/\"]");

            if (downloadLink != null) {
                String downloadUrl = "https://www.gutenberg.org" + downloadLink.attr("href");
                String bookTitle = doc.title();
                String fileName = bookTitle + ".txt";
                if (!fileExists(fileName)) {
                    String bookContent = Jsoup.connect(downloadUrl).ignoreContentType(true).execute().body();
                    saveToFile(fileName, bookContent);
                } else {
                    System.out.println("El libro ya existe: " + fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    private void saveToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Libro guardado: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




