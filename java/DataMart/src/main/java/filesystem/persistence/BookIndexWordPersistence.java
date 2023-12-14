package persistence;

import persistence.facade.FileListPersistenceFacade;

public class BookIndexWordPersistence extends FileListPersistenceFacade {
    public BookIndexWordPersistence(String path, String extension) {
        super(path, extension);
    }
}
