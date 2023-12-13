package crawler;

import datalake.cloud.CloudDatalake;
import crawler.impl.Controller;

public class Main {
    public static void main(String[] args) {
        CloudDatalake dataLake = new CloudDatalake();
        Controller controller = new Controller(dataLake);
        controller.start();
    }
}
