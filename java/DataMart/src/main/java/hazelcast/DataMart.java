package hazelcast;

import static hazelcast.config.HazelcastConfig.NAMES;
import static hazelcast.config.HazelcastConfig.WORDS;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import hazelcast.persistency.mapstore.NamesMapStore;
import hazelcast.persistency.mapstore.WordsMapStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataMart {
    private final IMap<String, List<Integer>> words;
    private final IMap<Integer, String> names;
    private final WordsMapStore wordsMapStore = new WordsMapStore();

    public DataMart() {
        Config config = new Config();

        config.getMapConfig(NAMES)
            .setMapStoreConfig(
                new MapStoreConfig()
                    .setEnabled(true)
                    .setImplementation(new NamesMapStore()));

        HazelcastInstance client = Hazelcast.newHazelcastInstance(config);
        names = client.getMap(NAMES);
        words = client.getMap(WORDS);
        if (words.isEmpty())
            load_words();
    }

    public void addWords(int id, String bookName, List<String> words) {
        names.put(id, bookName);

        for (String word : words) {
            this.words.putIfAbsent(word, new ArrayList<>());
            List<Integer> values = this.words.get(word);
            values.add(id);
            this.words.put(word, values);
        }

        save_words();
    }

    private void load_words() {
        this.words.putAll(wordsMapStore.load());
    }

    private void save_words() {
        wordsMapStore.store(words);
    }

    public List<Integer> getInvertedIndexOf(String word) {
        List<Integer> values = words.get(word);
        if (values != null)
            return values;
        return new ArrayList<>();
    }

    public Optional<String> getBookName(int id) {
        if (names.containsKey(id)) {
            return Optional.of(names.get(id));
        }
        return Optional.empty();
    }
}
