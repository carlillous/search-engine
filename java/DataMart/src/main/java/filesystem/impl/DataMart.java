package impl;

import java.util.List;

public interface DataMart {
    /**
     * Get the inverted list of book indexes for a given word.
     * @param word The word to get the inverted indexes for.
     * @return The inverted list of book indexes for the given word.
     */
    List<Integer> getInvertedIndexOf(String word);

    /**
     * Add an index of a book to a word. Inserting non-unique book index does not insert.
     * @param word The word to add the book index to.
     * @param bookIndex The index of the book to add to the word.
     */
    void addBookIndexToWord(String word, int bookIndex);
}
