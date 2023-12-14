package crawler.threads;

import crawler.impl.Downloader;
import datalake.cloud.CloudDataLake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Runner {
    private final Downloader downloader;

    public Runner(CloudDataLake dataLake){
        this.downloader = new Downloader(dataLake);
        this.run();
    }

    public void run(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        try {
            executor.scheduleAtFixedRate(downloader::run, 0, 1, TimeUnit.MINUTES);

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }
}
