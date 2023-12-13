package datalake.utils;

import datalake.cloud.CloudDataLake;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static datalake.utils.Common.*;

public class CloudReader{

    private CloudDataLake dataLake;
    private String bucketName;
    private Storage storage;
    private Set<String> stopwords;

    public CloudReader(CloudDataLake dataLake) {
        this.dataLake = dataLake;
        this.bucketName = dataLake.getBucketName();
        this.storage = dataLake.getStorage();
        this.stopwords = loadStopwords();
    }

    public Book readBook(String fileName) {
        String bookName;
        List<String> words = new ArrayList<>();

        Blob blob = dataLake.getBlob(fileName);

        if (blob == null) {
            System.out.println("File not found in the bucket.");
            return null;
        }

        try (InputStream inputStream = new ByteArrayInputStream(blob.getContent());
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {

            StringBuilder bookContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bookContent.append(line).append('\n');
            }

            words = preprocessText(bookContent.toString(), stopwords);
        } catch ( IOException e) {
            e.printStackTrace();
        }

        bookName = extractBookName(fileName);
        Book book = new Book(bookName, words);
        dataLake.addBook(book.getIndex(), book.getName());

        return book;
    }
}

