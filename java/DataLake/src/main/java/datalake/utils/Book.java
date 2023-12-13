package datalake.utils;

import java.util.List;

public class Book {

    private int index;
    private String name;
    private List<String> words;


    public Book(String name,int indx, List<String> words) {
        this.name = name;
        this.words = words;
        this.index = indx;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex(){
        return this.index;
    }

    public List<String> getWords() {
        return this.words;
    }

}
