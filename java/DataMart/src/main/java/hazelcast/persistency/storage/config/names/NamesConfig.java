package hazelcast.persistency.storage.config.names;

import hazelcast.persistency.storage.config.GoogleBucketConfig;

public class NamesConfig implements GoogleBucketConfig {
    public String projectId = "search-engine-404311";
    public String bucketId = "ulpgc-big-data-search-engine-hazelcast-names";

    @Override
    public String projectId() {
        return projectId;
    }

    @Override
    public String bucketId() {
        return bucketId;
    }
}
