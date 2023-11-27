package api;

public class SparkApp {
    private static final Service service = new Service();

    public static void main(String[] args) {
        spark.Spark.get("/search/:word", (request, response) -> {
            String word = request.params(":word");
            System.out.println("[API-APP]: Searched: " + word);

            response.type("application/json");
            response.status(200);

            return service.search(word);
        });
    }
}
