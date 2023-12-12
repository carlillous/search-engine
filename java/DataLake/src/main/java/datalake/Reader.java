package datalake;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Reader implements BookReader {
    private final Set<String> stopwordsEng;
    private final DataLake datalake;

    public Reader(DataLake dataLake) {
        this.datalake = dataLake;
        String[] wordsArray = {
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves",
            "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself",
            "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their",
            "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these",
            "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has",
            "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if",
            "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about",
            "against", "between", "into", "through", "during", "before", "after", "above",
            "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under",
            "again", "further", "then", "once", "here", "there", "when", "where", "why", "how",
            "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no",
            "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can",
            "will", "just", "don", "should", "now"
        };

        stopwordsEng = new HashSet<>(new ArrayList<>(Arrays.asList(wordsArray)));
    }

    @Override
    public List<String> preprocessText(String text) {
        List<String> words = new ArrayList<>();

        String[] tokens = text.replace('—', ' ').replace('.', ' ')
                .replace('_', ' ').replace('=', ' ').replace('-', ' ')
                .split("\\s+");

        for (String token : tokens) {
            String word = token.toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (word.matches(".*[a-zA-Z].*") && !stopwordsEng.contains(word) && !word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }

    @Override
    public String getPathSeparator() {
        String currentOs = System.getProperty("os.name").toLowerCase();
        if (currentOs.contains("win")) {
            return "\\";
        } else {
            return "/";
        }
    }

    @Override
    public Book readBook(String path) {
        String fileName,bookName;
        List<String> words = new ArrayList<>();
        File file = new File(path);
        fileName = file.getName();

        try (FileInputStream fileStream = new FileInputStream(path);
             InputStreamReader fileStreamReader = new InputStreamReader(fileStream, StandardCharsets.UTF_8);
             BufferedReader fileBufferedReader = new BufferedReader(fileStreamReader)) {

            StringBuilder bookContent = new StringBuilder();
            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                bookContent.append(line).append('\n');
            }

            words = preprocessText(bookContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookName = this.extractBookName(fileName);
        Book book = new Book(bookName,words);
        datalake.addBook(book.getIndex(), book.getName());

        return book;
    }

    public static String extractBookName(String fileName) {
        String[] parts = fileName.split("\\)");
        String extractedName = parts[1].replace("_", " ");
        extractedName = extractedName.substring(0, extractedName.length() - 4);
        extractedName = extractedName.substring(0, 1).toUpperCase() + extractedName.substring(1);

        return extractedName;
    }

}
