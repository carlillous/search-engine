package crawler.run;

import crawler.impl.Crawler;
import datalake.cloud.DataLake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Runner {
    private final Crawler crawler;

    public Runner(DataLake dataLake){
        this.crawler = new Crawler(dataLake);
        this.run();
    }

    public void run() {
        try(ScheduledExecutorService executor = Executors.newScheduledThreadPool(1)) {
            executor.scheduleAtFixedRate(crawler::run, 0, 1, TimeUnit.MINUTES);
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
