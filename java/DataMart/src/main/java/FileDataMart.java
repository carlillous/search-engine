import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataMart implements DataMart {
    private static final String path = "InvertedIndexRepository";
    private static final String extension = ".bin";

    public FileDataMart() {
        File newDirectory = new File(path);
        if (!newDirectory.exists())
            newDirectory.mkdir();
    }


    @Override
    public List<Integer> getInvertedIndexOfWord(String word) {
        File indexFile = new File(path, word + extension);
        return readListOfIntegersFromFile(indexFile);
    }

    private List<Integer> readListOfIntegersFromFile(File file) {
        ObjectInputStream objectInputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            return (List<Integer>) object;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBookIndexToWord(String word, int bookIndex) {
        List<Integer> indexListOfWord = getInvertedIndexOfWord(word);
        List<Integer> updatedIndexListOfWord = addIndexToListUnique(indexListOfWord, bookIndex);
        saveInvertedIndexListOfWordToFile(word, updatedIndexListOfWord);
    }

    private void saveInvertedIndexListOfWordToFile(String word, List<Integer> invertedIndexListOfWord) {
        File indexFile = new File(path, word + extension);
        saveListOfIntegersToFile(indexFile, invertedIndexListOfWord);
    }

    private void saveListOfIntegersToFile(File file, List<Integer> integers) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(integers);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Integer> addIndexToListUnique(List<Integer> list, int index) {
        if (!list.contains(index)) {
            list.add(index);
        }
        return list;
    }
}
