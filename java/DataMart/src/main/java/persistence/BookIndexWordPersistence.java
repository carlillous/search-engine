package persistence;

import persistence.facade.ListPersistenceFacade;

public class BookIndexWordPersistence extends ListPersistenceFacade {
    public BookIndexWordPersistence(String path, String extension) {
        super(path, extension);
    }
}
