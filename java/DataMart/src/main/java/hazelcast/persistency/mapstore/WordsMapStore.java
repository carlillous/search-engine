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

public class WordsMapStore implements MapStore<String, List<Integer>> {
    private final GoogleBucket bucket = new GoogleBucket(new WordsConfig());

    @Override
    public void store(String s, List<Integer> integers) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            bucket.createBlob(s, mapper.writeValueAsString(integers));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeAll(Map<String, List<Integer>> map) {
        for (Map.Entry<String, List<Integer>> entry : map.entrySet())
            store(entry.getKey(), entry.getValue());
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void deleteAll(Collection<String> collection) {

    }

    @Override
    public List<Integer> load(String s) {
        Blob blob = bucket.getBlob(s);
        if (blob == null)
            return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(blob.getContent(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, List<Integer>> loadAll(Collection<String> collection) {
        Map<String, List<Integer>> map = new HashMap<>();
        for(String s : collection) {
            List<Integer> value = load(s);
            if (value != null) {
                map.put(s, value);
            }
        }
        return map;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        Page<Blob> blobs = bucket.list();
        return blobs.streamAll()
            .map(BlobInfo::getName)
            .collect(Collectors.toList());

    }
}