package indexer;

import hazelcast.DataMart;
import datalake.cloud.DataLake;
import indexer.impl.Indexer;
import indexer.messages.MessageReceiver;

public class Main {
    public static void main(String[] args) {
        DataLake dataLake = new DataLake();
        DataMart dataMart = new DataMart();
        Indexer indexer = new Indexer(dataLake, dataMart);
        MessageReceiver receiver = new MessageReceiver(indexer);
        receiver.start();
    }
}
