import java.util.List;

public class Book {
    private String name;
    private List<String> words;

    public Book(String name, List<String> words) {
        this.name = name;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public List<String> getWords() {
        return words;
    }
}
