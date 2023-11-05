import impl.DataMart;
import impl.file.FileDataMart;

public class DataMartUsage {
    public static void main(String[] args) {
        DataMart dataMart = new FileDataMart();

        String word = "hello";
        int bookIndex = 0;

        dataMart.addBookIndexToWord(word, bookIndex);
    }
}
