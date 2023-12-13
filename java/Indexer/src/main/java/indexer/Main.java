package indexer;

import datalake.cloud.CloudDataLake;

public class Main {
    public static void main(String[] args) {
        CloudDataLake dataLake = new CloudDataLake();
        Indexer indexer = new Indexer(dataLake);
        indexer.indexQueue();
    }
}
