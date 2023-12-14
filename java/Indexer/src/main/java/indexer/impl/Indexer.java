package indexer;

import datalake.cloud.CloudDataLake;
import datalake.cloud.service.CloudReader;
import datalake.utils.*;
import impl.DataMart;
import impl.file.FileSystemDataMart;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer {
    private final DataMart dataMart;
    private final CloudDataLake dataLake;
    private final Reader reader;

    private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

    public Indexer(CloudDataLake dataLake) {
        dataMart = new FileSystemDataMart();
        this.dataLake = dataLake;
        reader = new CloudReader(dataLake);
    }

    public void index(String fileName) {
        Book book = reader.read(fileName);
        int index = book.getIndex();
        String bookName = book.getName();
        List<String> words = book.getWords();
        logger.info("Indexing: \"" + bookName + "\"");
        for (String word : words) {
            dataMart.addBookIndexToWord(word,index);
        }
        logger.info("Done: \"" + bookName + "\"");
    }

    public void indexQueue(){
        Receiver receiver = new Receiver(dataLake);
        receiver.start();
    }

}

