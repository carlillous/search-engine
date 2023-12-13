package indexer;

import datalake.filesystem.DataLake;

public class Main {
    public static void main(String[] args) {
        DataLake dataLake = new DataLake();
        Indexer indexer = new Indexer(dataLake);
        indexer.indexQueue();
    }
}
