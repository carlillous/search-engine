import impl.DataMart;
import impl.file.FileSystemDataMart;
import java.io.File;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDataMartTest {

    @Test
    void test() {
        DataMart dataMart = new FileSystemDataMart();

        File f1 = new File("InvertedIndexRepository", "hello.dat");
        File f2 = new File("InvertedIndexRepository", "world.dat");
        f1.delete();
        f2.delete();

        String word1 = "hello";
        String word2 = "world";
        List<Integer> indexes1 = new ArrayList<>();
        List<Integer> indexes2 = new ArrayList<>();

        // Empty
        assertEquals(indexes1, dataMart.getInvertedIndexOf(word1));
        assertEquals(indexes2, dataMart.getInvertedIndexOf(word2));

        // Insert to empty
        int i0 = 0;
        indexes1.add(i0);
        dataMart.addBookIndexToWord(word1, i0);
        assertEquals(indexes1, dataMart.getInvertedIndexOf(word1));

        // Multiple inserts to empty
        for (int i = 0; i < 5; i++) {
            indexes2.add(i);
            dataMart.addBookIndexToWord(word2, i);
        }
        assertEquals(indexes2, dataMart.getInvertedIndexOf(word2));

        // Non-unique inserts
        for (int i = 0; i < 5; i++)
            dataMart.addBookIndexToWord(word2, i);
        assertEquals(indexes2, dataMart.getInvertedIndexOf(word2));

        // Another inserts after non-unique
        indexes2.add(5);
        dataMart.addBookIndexToWord(word2, 5);
        assertEquals(indexes2, dataMart.getInvertedIndexOf(word2));
    }
}