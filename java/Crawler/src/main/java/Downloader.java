import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Downloader {
    private DataLake dataLake;

    public Downloader(DataLake dl){
        dataLake = new DataLake(dl.getDataLakePath());
    }

    public void run() {
        try {
                Random random = new Random();
                int randomNumber = random.nextInt(70000) + 1;
                String numStr = String.valueOf(randomNumber);
                String bookUrl = "https://www.gutenberg.org/cache/epub/" + numStr + "/pg" + numStr + ".txt";

                if (dataLake.isBookInDataLake(numStr)) {
                    System.out.println("Book is already downloaded.");
                }else{
                    if (downloadBook(bookUrl, numStr)) {
                        System.out.println("Descarga completa.");
                    } else {
                        System.out.println("No se encontró el libro en el enlace: " + bookUrl);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean downloadBook(String bookUrl, String i) {
        try {
            Connection.Response response = Jsoup.connect(bookUrl).ignoreContentType(true)
                    .execute();
            String book = response.parse().wholeText();

            if (book.length() > 0) {
                String title = ContentManager.getBookTitle(book);
                String finalTitle = ContentManager.cleanFilename(title);

                String fileName = dataLake.getDataLakePath() + "(" + i + ")"+finalTitle+".txt";
                File dataLakeDir = new File(dataLake.getDataLakePath());
                if (!dataLakeDir.exists()) {
                    dataLakeDir.mkdirs();
                }

                String bookContent = ContentManager.getBookContent(book);

                dataLake.saveToFile(fileName, bookContent);
                System.out.println("Libro guardado: " + fileName);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
