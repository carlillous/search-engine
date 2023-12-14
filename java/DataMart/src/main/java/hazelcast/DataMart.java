package hazelcast;

import static hazelcast.Config.NAMES;
import static hazelcast.Config.WORDS;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataMart {
    private final IMap<String, List<Integer>> words;
    private final IMap<Integer, String> names;

    public DataMart() {
        HazelcastInstance client = Hazelcast.newHazelcastInstance();
        words = client.getMap(WORDS);
        names = client.getMap(NAMES);
    }

    public void addWords(int id, String bookName, List<String> words) {
        names.put(id, bookName);

        for (String word : words) {
            this.words.putIfAbsent(word, new ArrayList<>());
            this.words.get(word).add(id);
        }
    }

    public List<Integer> getInvertedIndexOf(String word) {
        if (words.containsKey(word)) {
            return words.get(word);
        }
        return new ArrayList<>();
    }

    public Optional<String> getBookName(int id) {
        if (names.containsKey(id)) {
            return Optional.of(names.get(id));
        }
        return Optional.empty();
    }
}
