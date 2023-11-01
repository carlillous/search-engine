import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Reader implements BookReader {
    private Set<String> stopwordsEng = new HashSet<>();

    public Reader() {
        loadStopwords();
    }

    private void loadStopwords() {
        try (FileInputStream stopwordStream = new FileInputStream("files/en-stopwords.txt");
             InputStreamReader stopwordStreamReader = new InputStreamReader(stopwordStream, StandardCharsets.UTF_8);
             BufferedReader stopwordBufferedReader = new BufferedReader(stopwordStreamReader)) {

            String line;
            while ((line = stopwordBufferedReader.readLine()) != null) {
                stopwordsEng.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public List<Object> readBook(String path) {
        List<Object> result = new ArrayList<>();
        List<String> words = new ArrayList<>();

        String[] pathParts = path.split(Pattern.quote(getPathSeparator()));
        String bookName = pathParts[pathParts.length - 1].split("\\.")[0];
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

        result.add(bookName);
        result.add(words);

        return result;
    }
}
