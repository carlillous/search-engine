package api;

import datalake.DataLake;
import impl.DataMart;
import impl.file.FileSystemDataMart;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service {
    private final DataMart dataMart = new FileSystemDataMart();
    private final DataLake dataLake = new DataLake();
    private final JSONSerializer serializer = new JSONSerializer();
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    public String search(String word) {
        List<Integer> indexes = dataMart.getInvertedIndexOf(word);
        List<String> names = indexes.stream().map(dataLake::getTitle).collect(Collectors.toList());

        logger.info("REQ = \"" + word +"\" -> " + names);

        SearchRequest req = new SearchRequest(word, names);
        return serializer.toJson(req);
    }
}
