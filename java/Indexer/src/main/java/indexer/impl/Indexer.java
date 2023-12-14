package indexer.impl;

import hazelcast.DataMart;
import datalake.cloud.DataLake;

import indexer.parsing.BookParser;
import indexer.splitting.BookSplitter;
import indexer.splitting.SplitBook;
import indexer.utils.BookIdParser;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer {
    private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

    private final DataLake dataLake;
    private final DataMart dataMart;

    public Indexer(DataLake dataLake, DataMart dataMart) {
        this.dataLake = dataLake;
        this.dataMart = dataMart;
    }

    public void index(String fileName) {
        String data = dataLake.get(fileName);
        SplitBook split = BookSplitter.split(data);
        List<String> words = BookParser.parse(split.content);
        int id = BookIdParser.parse(fileName);
        logger.info("Indexing: " + fileName + "...");
        dataMart.addWords(id, split.name, words);
        logger.info("Done!");
    }
}

