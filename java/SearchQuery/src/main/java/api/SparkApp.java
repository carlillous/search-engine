package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkApp {
    private static final Service service = new Service();
    private static final Logger logger = LoggerFactory.getLogger(SparkApp.class);

    public static void main(String[] args) {
        spark.Spark.get("/search/:word", (request, response) -> {
            String word = request.params(":word");

            logger.info("Searched: " + word);

            response.type("application/json");
            response.status(200);

            return service.search(word);
        });
    }
}
