package hazelcast.persistency.storage;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import hazelcast.persistency.storage.config.GoogleBucketConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GoogleBucket {
    private final Bucket bucket;

    public GoogleBucket(GoogleBucketConfig config) {
        try {
            Credentials credentials = GoogleCredentials.fromStream(
                Objects.requireNonNull(getClass().getResourceAsStream("/credentials.json")));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(config.projectId()).build().getService();
            bucket = storage.get(config.bucketId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Blob getBlob(String blob) {
        return bucket.get(blob);
    }

    public void createBlob(String blob, String content) {
        bucket.create(blob, content.getBytes(StandardCharsets.UTF_8));
    }

    public Page<Blob> list() {
        return bucket.list();
    }
}
