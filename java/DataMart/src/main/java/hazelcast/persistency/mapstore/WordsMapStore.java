package hazelcast.persistency.mapstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.hazelcast.map.MapStore;
import hazelcast.persistency.storage.GoogleBucket;
import hazelcast.persistency.storage.config.words.WordsConfig;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordsMapStore {
    private final GoogleBucket bucket = new GoogleBucket(new WordsConfig());
    private static final String FILENAME = "map.json";

    public void store(Map<String, List<Integer>> map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            bucket.createBlob(FILENAME, mapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Integer>> load() {
        Blob blob = bucket.getBlob(FILENAME);
        if (blob == null)
            return new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(blob.getContent(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}