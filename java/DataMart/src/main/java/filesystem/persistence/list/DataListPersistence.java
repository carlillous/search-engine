package filesystem.persistence.list;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataListPersistence implements ListPersistence {
    @Override
    public List<Integer> read(File file) {
        List<Integer> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            while (dis.available() > 0) {
                list.add(dis.readInt());
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void save(File file, List<Integer> list) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (Integer integer : list) {
                dos.writeInt(integer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
