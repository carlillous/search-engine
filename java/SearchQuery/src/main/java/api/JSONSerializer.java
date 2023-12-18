package api;

import com.google.gson.Gson;

public class JSONSerializer {
    private final Gson gson = new Gson();

    public String toJson(SearchRequest request) {
        return gson.toJson(request);
    }
}
