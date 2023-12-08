package api;

import datalake.DataLake;
import impl.DataMart;
import impl.file.FileDataMart;

import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private final DataMart dataMart = new FileDataMart();
    private final DataLake dataLake = new DataLake();
    private final JSONSerializer serializer = new JSONSerializer();

    public String search(String word) {
        List<Integer> indexes = dataMart.getInvertedIndexOf(word);
        List<String> names = indexes.stream().map(dataLake::getTitle).collect(Collectors.toList());

        System.out.println("[API-SERVICE]: REQ = \"" + word +"\" -> " + names);

        SearchRequest req = new SearchRequest(word, names);
        return serializer.toJson(req);
    }
}
