package crawler;

import datalake.DataLake;

public class Main {
    public static void main(String[] args) {
        DataLake dataLake = new DataLake();
        Controller controller = new Controller(dataLake);
        controller.start();
    }
}
