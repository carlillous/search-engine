import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ReaderTest {
    @Test
    public void testReadBook() {
        Reader reader = new Reader();
        String filePath = "files/BOOK";
        Book bookData = reader.readBook(filePath);

        assertNotNull("Book data should not be null.", bookData);

        String bookName = bookData.getName();
        List<String> words = bookData.getWords();

        assertNotNull("Book name should not be null.", bookName);
        assertNotNull("Words in the book should not be null.", words);
        assertTrue("The book should contain words.", words.size() > 0);

        System.out.println("Book Name: " + bookName);
        System.out.println("Words in the book: " + words);
    }
}
