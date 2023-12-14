package books;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import datalake.cloud.storage.GoogleBucket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BookNames {
    private Map<Integer, String> data;

    public BookNames(GoogleBucket bucket) {
        load(bucket);
    }

    public void load(GoogleBucket bucket) {
        Blob blob = bucket.getBlob("books.json");
        if (blob == null) {
            data = new HashMap<>();
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                data = objectMapper.readValue(
                    new InputStreamReader(Channels.newInputStream(blob.reader())),
                    new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Loaded books.json:" + data.toString());
        }
    }

    public void save(GoogleBucket bucket){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String content = objectMapper.writeValueAsString(data);
            bucket.createBlob("books.json", content);
            System.out.println("Written books.json:" + data.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void add(String bookName) {
        throw new UnsupportedOperationException();

        // old
        int bookIndex = 0;
        if (!bookNames.isEmpty()) {
            bookIndex = Collections.max(bookNames.keySet()) + 1;
        }
        bookNames.put(bookIndex, bookName);
        bookNames.save(bookNames);
        return bookIndex;
    }

    public String get(int index) {
        return data.get(index);
    }
}
