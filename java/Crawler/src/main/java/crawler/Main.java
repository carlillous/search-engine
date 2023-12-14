package crawler;

import datalake.cloud.DataLake;
import crawler.run.Runner;

public class Main {
    public static void main(String[] args) {
        DataLake dataLake = new DataLake();
        Runner runner = new Runner(dataLake);
        runner.run();
    }
}
