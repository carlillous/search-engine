package crawler;

import crawler.cloud.CloudController;
import datalake.cloud.CloudDatalake;
import datalake.filesystem.DataLake;
import crawler.filesys.Controller;

public class Main {
    public static void main(String[] args) {
        CloudDatalake dataLake = new CloudDatalake();
        Controller controller = new Controller(dataLake);
        controller.start();
    }
}
