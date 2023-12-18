package hazelcast.persistency.mapstore;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.hazelcast.map.MapStore;
import hazelcast.persistency.storage.GoogleBucket;
import hazelcast.persistency.storage.config.names.NamesConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NamesMapStore implements MapStore<Integer, String> {
    private final GoogleBucket bucket = new GoogleBucket(new NamesConfig());

    @Override
    public void store(Integer integer, String s) {
        bucket.createBlob(String.valueOf(integer), s);
    }

    @Override
    public void storeAll(Map<Integer, String> map) {
        for(Map.Entry<Integer, String> entry : map.entrySet()) {
            store(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void deleteAll(Collection<Integer> collection) {

    }

    @Override
    public String load(Integer integer) {
        Blob blob = bucket.getBlob(String.valueOf(integer));
        if (blob != null) {
            return new String(blob.getContent());
        }
        return null;
    }

    @Override
    public Map<Integer, String> loadAll(Collection<Integer> collection) {
        Map<Integer, String> map = new HashMap<>();
        for(Integer i : collection) {
            String value = load(i);
            if (value != null) {
                map.put(i, value);
            }
        }
        return map;
    }

    @Override
    public Iterable<Integer> loadAllKeys() {
        Page<Blob> blobs = bucket.list();
        return blobs.streamAll()
            .map(b -> Integer.valueOf(b.getName()))
            .collect(Collectors.toList());
    }
}