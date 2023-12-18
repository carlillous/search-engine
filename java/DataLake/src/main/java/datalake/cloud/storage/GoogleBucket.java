package datalake.cloud.storage;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GoogleBucket {
    private final Bucket bucket;

    public GoogleBucket() {
        try {
            Credentials credentials = GoogleCredentials.fromStream(
                Objects.requireNonNull(getClass().getResourceAsStream("/credentials.json")));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(Config.projectId).build().getService();
            bucket = storage.get(Config.bucketId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(String blob) {
        for (Blob b : bucket.list().getValues()) {
            String bName = b.getName();
            if (Objects.equals(bName, blob)) {
                return true;
            }
        }

        return false;
    }

    public Blob getBlob(String blob) {
        return bucket.get(blob);
    }

    public void createBlob(String blob, String content) {
        bucket.create(blob, content.getBytes(StandardCharsets.UTF_8));
    }

}
