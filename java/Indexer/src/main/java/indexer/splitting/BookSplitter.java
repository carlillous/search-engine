package indexer.splitting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookSplitter {
    public static SplitBook split(String data) {
        String content = getBookContent(data);
        String name = getBookTitle(content);
        return new SplitBook(name, content);
    }

    private static String getBookTitle(String bookContent){
        String[] lines = bookContent.split("\n");
        String title = null;
        for (String line : lines) {
            if (line.startsWith("Title:")) {
                title = line.replace("Title:", "").trim();
                break;
            }
        }

        return cleanFilename(title);
    }

    private static String cleanFilename(String title) {

        String[][] replacements = {
                {":", " "}, {" ", "_"}, {",", ""}, {".", ""},
                {"(", " "}, {")", ""}, {"-", ""}, {"/", ""},
                {";", ""}, {"\"", ""}, {"—", ""}, {"?", ""}
        };

        for (String[] replacement : replacements) {
            title = title.replace(replacement[0], replacement[1]);
        }

        return title;
    }

    private static String getBookContent(String content){

        Pattern regex1 = Pattern.compile("\\*\\*\\* START OF THE PROJECT GUTENBERG EBOOK(.*)", Pattern.DOTALL);
        Pattern regex2 = Pattern.compile("\\*\\*\\* START OF THE PROJECT GUTENBERG EBOOK(.*)", Pattern.DOTALL);

        String bookContent = "";

        Matcher matcher1 = regex1.matcher(content);
        Matcher matcher2 = regex2.matcher(content);

        if (matcher1.find()) {
            bookContent = matcher1.group(1).trim();
        } else if (matcher2.find()) {
            bookContent = matcher2.group(1).trim();
        }

        return bookContent.split("\\*\\*\\*")[1].trim();
    }
}
