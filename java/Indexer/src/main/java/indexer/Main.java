package indexer;

import datalake.filesystem.DataLake;

public class Main {
    public static void main(String[] args) {
        Indexer indexer = new Indexer(new DataLake());
        indexer.indexQueue();
    }
}
