package impl.file;

import impl.DataMart;
import persistence.BookIndexWordPersistence;
import util.inserter.list.UniqueIntegerListInserter;

import java.util.List;

public class FileDataMart implements DataMart {
    private final UniqueIntegerListInserter uniqueListInserter = new UniqueIntegerListInserter();
    private final BookIndexWordPersistence bookIndexWordPersistence;

    public FileDataMart() {
        String path = "InvertedIndexRepository";
        String extension = ".bin";
        bookIndexWordPersistence = new BookIndexWordPersistence(path, extension);
    }

    @Override
    public List<Integer> getInvertedIndexOf(String word) {
        return bookIndexWordPersistence.getBookIndexesOf(word);
    }

    @Override
    public void addBookIndexToWord(String word, int bookIndex) {
        List<Integer> bookIndexes = getInvertedIndexOf(word);
        uniqueListInserter.insert(bookIndexes, bookIndex);
        bookIndexWordPersistence.saveBookIndexesOf(word, bookIndexes);
    }
}
