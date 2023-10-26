public class DataMartUsage {
    public static void main(String[] args) {
        FileDataMart dataMart = new FileDataMart();

        String word = "hello";
        int bookIndex = 0;

        dataMart.addBookIndexToWord(word, bookIndex);
    }
}
