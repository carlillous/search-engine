package filesystem.persistence;

import filesystem.persistence.facade.FileListPersistenceFacade;

public class BookIndexWordPersistence extends FileListPersistenceFacade {
    public BookIndexWordPersistence(String path, String extension) {
        super(path, extension);
    }
}
