package api;

import java.util.List;

public class SearchRequest {
    public String word;
    public List<String> books;

    public SearchRequest(String word, List<String> books) {
        this.word = word;
        this.books = books;
    }
}
