package crawler;

import datalake.cloud.CloudDataLake;
import crawler.impl.Controller;

public class Main {
    public static void main(String[] args) {
        CloudDataLake dataLake = new CloudDataLake();
        Controller controller = new Controller(dataLake);
        controller.start();
    }
}
