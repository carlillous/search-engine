package filesystem.persistence.repository;

import java.io.File;

public class FileRepository {
    private final String path;
    private final String extension;

    public FileRepository(String path, String extension) {
        this.path = path;
        this.extension = extension;

        create();
    }

    private void create() {
        File repository = new File(path);
        if (!repository.exists())
            repository.mkdir();
    }

    public File get(String word) {
        return new File(path, word + extension);
    }
}
