package api;


import hazelcast.DataMart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service {
    private final DataMart dataMart = new DataMart();
    private final JSONSerializer serializer = new JSONSerializer();
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    public String search(String word) {
        List<Integer> indexes = dataMart.getInvertedIndexOf(word);
        List<String> names = indexes.stream()
            .map(dataMart::getBookName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        logger.info("REQ: \"" + word +"\" -> " + names);

        SearchRequest req = new SearchRequest(word, names);
        return serializer.toJson(req);
    }
}
