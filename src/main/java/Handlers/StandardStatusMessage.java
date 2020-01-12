package Handlers;

import static spark.Spark.get;

/**
 * This class implements a standard error message for the API which is 404,
 * when a resource could not be found. This HTTP response status code will be visible
 * for the client when a request is made for a resource that the server can not find.
 * @author Patriksku
 */
public class StandardStatusMessage {

    public void standardErrorMessage() {
        get("*", (request, response) -> {
            response.type("text/plain");
            response.status(404);
            return "404 Not found";
        });
    }

    public void init() {
        standardErrorMessage();
    }
}
