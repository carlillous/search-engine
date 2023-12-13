package crawler.impl;

import datalake.cloud.CloudDatalake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final CloudDatalake dataLake ;
    private final Downloader downloader;

    public Controller(CloudDatalake dataLake){
        this.dataLake = dataLake;
        this.downloader = new Downloader(dataLake);
        this.start();
    }

    public void start(){

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

    public CloudDatalake getDataLake(){
        return this.dataLake;
    }



}
