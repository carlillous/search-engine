package cli;

import impl.DataMart;
import impl.file.FileDataMart;

import datalake.DataLake;

import java.util.List;

public class QueryMethods {

    private DataMart dataMart = new FileDataMart();
    private DataLake dataLake = new DataLake();

    public void queryWord(String word){
        System.out.println("The word " + word + " appears in:");
        List<Integer> invertedIndexOfWord = dataMart.getInvertedIndexOf(word);
        for(int i : invertedIndexOfWord){
            System.out.println("- "+dataLake.getTitle(i)+"\n");
        }

    }
}
