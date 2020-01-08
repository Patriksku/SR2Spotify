package Handlers;

import CORS.CORS;
import SR.Functions.ChannelSongs;
import org.w3c.dom.Document;

import static spark.Spark.get;

/**
 * This class implements a standard error message for the API, which is 404,
 * when a resource could not be found. This HTTP response status code will be visible
 * for the client when a request is made for a resource that the server can not find.
 * @author Patriksku
 */
public class StandardErrorMessage {

    private CORS cors = new CORS();

    public void standardErrorMessage() {
        get("*", (request, response) -> {
            cors.addSupport(request, response);
            response.type("text/html; charset=UTF-8");
            response.status(404);
            return "<html><body><h2>404 Not found</h2></body></html>";
        });
    }

    public void init() {
        standardErrorMessage();;
    }
}
