import java.util.List;

public class QueryMethods {

    private DataMart dataMart = new FileDataMart();
    private DataIndexHandler dataLake = new DataIndexHandler();

    public void queryWord(String word){
        System.out.println("The word" + word + "appears in:");
        List<Integer> invertedIndexOfWord = dataMart.getInvertedIndexOfWord(word);
        for(int i : invertedIndexOfWord){
            System.out.println("- "+dataLake.getTitle(i)+"\n");
        }

    }
}
