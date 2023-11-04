import java.util.Map;

public class DataIndexHandler {

    private Map<Integer, String> bookNames;
    private BookPersistance persistance = new BookPersistance();

    public DataIndexHandler() {
        bookNames = persistance.load();
    }

    public void addBook(int bookIndex, String bookTitle) {
        bookNames.put(bookIndex, bookTitle);
        persistance.save(bookNames);
    }

    public String getTitle(int bookIndex) {
        return bookNames.get(bookIndex);
    }



}
