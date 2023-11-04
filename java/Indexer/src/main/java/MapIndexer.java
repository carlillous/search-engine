import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MapIndexer {

    private Map<String, HashSet<Integer>> wordIndexes;
    private DataMart dataMart;

    public MapIndexer() {
        wordIndexes = new HashMap<>();
        dataMart = new FileDataMart();
    }

    public void indexOne(String path) {
        Reader reader = new Reader();
        Book book = reader.readBook(path);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        System.out.println("[INDEXER]: Indexing book: \"" + bookName + "\"");
        for (String word : words) {
            if (!wordIndexes.containsKey(word)) {
                wordIndexes.put(word, new HashSet<>());
            }
            wordIndexes.get(word).add(index);
            dataMart.addBookIndexToWord(word,index);
        }
    }

    public void indexAll(String directory) {
        System.out.println("[INDEXER]: ------------------ Indexing starting -------------------");
        File[] files = new File(directory).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    indexOne(files[i].getAbsolutePath());
                }
            }
        }
        System.out.println("[INDEXER]: -------------------- Indexing ended --------------------");
    }

}

