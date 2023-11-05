import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataLake {

    public static boolean isBookInDataLake(String i) {
        File dir = new File("datalake/files");
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.contains("(" + i + ")")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void saveToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
