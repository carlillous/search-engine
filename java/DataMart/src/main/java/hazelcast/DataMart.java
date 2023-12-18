package hazelcast;

import static hazelcast.Config.NAMES;
import static hazelcast.Config.WORDS;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.multimap.MultiMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataMart {
    private final MultiMap<String, Integer> words;
    private final IMap<Integer, String> names;

    public DataMart() {
        HazelcastInstance client = Hazelcast.newHazelcastInstance();
        words = client.getMultiMap(WORDS);
        names = client.getMap(NAMES);
    }

    public void addWords(int id, String bookName, List<String> words) {
        names.put(id, bookName);

        for (String word : words) {
            this.words.put(word, id);
        }
    }

    public List<Integer> getInvertedIndexOf(String word) {
        return new ArrayList<>(words.get(word));
    }

    public Optional<String> getBookName(int id) {
        if (names.containsKey(id)) {
            return Optional.of(names.get(id));
        }
        return Optional.empty();
    }
}
