package indexer.utils;

import java.util.regex.Pattern;

public class BookIdParser {
    public static int parse(String fileName) {
        if (fileName.contains(".")) {
            return Integer.parseInt(fileName.split(Pattern.quote("."))[0]);
        } else {
            throw new IllegalArgumentException("File " + fileName + " does not contain \".\"!");
        }
    }
}
