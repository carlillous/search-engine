package util.inserter;

import java.util.List;

public interface Inserter<S, E> {
    void insert(S container, E elem);
}
