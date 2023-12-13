package crawler;

import crawler.cloud.CloudController;
import datalake.cloud.CloudDatalake;
import datalake.filesystem.DataLake;
import crawler.filesys.Controller;

public class Main {
    public static void main(String[] args) {
        startCrawlerToCloud();
    }


    private static void startCrawlerToFileSystem(){
        DataLake dataLake = new DataLake();
        Controller controller = new Controller(dataLake);
        controller.start();
    }

    private static void startCrawlerToCloud(){
        CloudDatalake dataLake = new CloudDatalake();
        CloudController crawler = new CloudController(dataLake);
        crawler.start();
    }
}
