package persistence.list;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface ListPersistence {
    List<Integer> read(File file);

    void save(File file, List<Integer> list);
}
