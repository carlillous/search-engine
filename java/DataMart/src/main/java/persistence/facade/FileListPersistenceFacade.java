package persistence.facade;

import persistence.list.BinaryListPersistence;
import persistence.repository.FileRepository;

import java.io.File;
import java.util.List;

public class ListPersistenceFacade {
    private final FileRepository fileRepository;
    private static BinaryListPersistence listPersistence;

    public ListPersistenceFacade(String path, String extension) {
        fileRepository = new FileRepository(path, extension);
    }

    public List<Integer> getBookIndexesOf(String word) {
        File wordFile = fileRepository.get(word);
        return listPersistence.read(wordFile);
    }

    public void saveBookIndexesOf(String word, List<Integer> bookIndexes) {
        File wordFile = fileRepository.get(word);
        listPersistence.save(wordFile, bookIndexes);
    }

}
