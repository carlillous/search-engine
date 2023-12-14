package util.inserter.list;

import java.util.List;

public class UniqueIntegerListInserter implements IntegerListInserter {
    @Override
    public void insert(List<Integer> list, Integer elem) {
        if (!list.contains(elem))
            list.add(elem);
    }
}
