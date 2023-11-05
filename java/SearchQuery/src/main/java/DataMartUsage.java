import impl.DataMart;
import impl.file.FileDataMart;

import java.util.List;

public class DataMartUsage {
    public static void main(String[] args) {
        DataMart dataMart = new FileDataMart();

        String word = "hello";
        List<Integer> invertedIndexOfWord = dataMart.getInvertedIndexOf(word);

        System.out.println(invertedIndexOfWord);
    }
}
