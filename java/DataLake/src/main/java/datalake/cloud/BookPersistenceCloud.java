package datalake.cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

public class BookPersistenceCloud {
    private final Bucket bucket;

    public BookPersistenceCloud(Bucket bucket) {
        this.bucket = bucket;
    }

    public Map<Integer, String> load() {
        Map<Integer, String> map = null;
        try {
            Blob blob = bucket.get("books.json");
            if (blob != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(new InputStreamReader(Channels.newInputStream(blob.reader())), new TypeReference<Map<Integer, String>>() {});
            }
            else {
                map = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded books.json:" + map.toString());
        return map;
    }

    public void save(Map<Integer,String> books){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(books);
            byte[] bytes = json.getBytes();
            bucket.create("books.json", bytes);
            System.out.println("Written books.json:" + books.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
