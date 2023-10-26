import java.util.List;

public class DataMartUsage {
    public static void main(String[] args) {
        DataMart dataMart = new FileDataMart();

        String word = "hello";
        List<Integer> invertedIndexOfWord = dataMart.getInvertedIndexOfWord(word);

        System.out.println(invertedIndexOfWord);
    }
}
