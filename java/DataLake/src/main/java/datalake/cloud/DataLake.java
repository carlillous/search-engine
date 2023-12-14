package datalake.cloud;

import com.google.cloud.storage.Blob;
import datalake.cloud.storage.GoogleBucket;

public class DataLake {
    private final GoogleBucket bucket;

    public DataLake() {
        bucket = new GoogleBucket();
    }

    public boolean contains(String name) {
        return bucket.contains(name);
    }

    public void save(String name, String content) {
        bucket.createBlob(name, content);
    }

    public String get(String name) {
        Blob blob = bucket.getBlob(name);
        return new String(blob.getContent());
    }
}


