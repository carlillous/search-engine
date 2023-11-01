import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ReaderTest {
    @Test
    public void testReadBook() {
        Reader reader = new Reader();
        String filePath = "files/pg72005.txt";
        List<Object> bookData = reader.readBook(filePath);

        assertFalse("Failed to read the book.", bookData.isEmpty());

        String bookName = (String) bookData.get(0);
        List<String> words = (List<String>) bookData.get(1);

        assertNotNull("Book name should not be null.", bookName);
        assertNotNull("Words in the book should not be null.", words);
        assertTrue("The book should contain words.", words.size() > 0);
    }
}
