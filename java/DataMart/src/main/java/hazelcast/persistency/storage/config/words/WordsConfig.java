package hazelcast.persistency.storage.config.words;

import hazelcast.persistency.storage.config.GoogleBucketConfig;

public class WordsConfig implements GoogleBucketConfig {
    public static String projectId = "search-engine-404311";
    public static String bucketId = "ulpgc-big-data-search-engine-hazelcast-words";

    @Override
    public String projectId() {
        return projectId;
    }

    @Override
    public String bucketId() {
        return bucketId;
    }
}
