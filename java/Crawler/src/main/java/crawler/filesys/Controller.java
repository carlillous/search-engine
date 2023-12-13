package crawler.filesys;

import crawler.cloud.CloudDownloader;
import datalake.cloud.CloudDatalake;
import datalake.filesystem.DataLake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final CloudDatalake dataLake ;
    private final CloudDownloader downloader;

    public Controller(CloudDatalake dataLake){
        this.dataLake = dataLake;
        this.downloader = new CloudDownloader(dataLake);
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
