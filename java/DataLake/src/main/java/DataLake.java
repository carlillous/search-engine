import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DataLake {
    private String path;
    private Map<Integer, String> bookNames;
    private BookPersistance persistance = new BookPersistance();

    public DataLake() {
        this.path = "datalake/files";
        bookNames = persistance.load();
    }


    public DataLake(String directory) {
        this.path = directory;
        bookNames = persistance.load();
    }

    public void addBook(int bookIndex, String bookTitle) {
        bookNames.put(bookIndex, bookTitle);
        persistance.save(bookNames);
    }

    public boolean isBookInDataLake(String i) {
        File dir = new File("datalake/files");
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.contains("(" + i + ")")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(int bookIndex) {
        return bookNames.get(bookIndex);
    }

    public String getDataLakePath(){
        return this.path;
    }

}
