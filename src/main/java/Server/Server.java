package Server;

import Handlers.StandardStatusMessage;
import Lyrics.API.Apiseeds;
import SR.API.SrAPI;
import Spotify.API.SpotifyAPI;
import com.mashape.unirest.http.Unirest;
import java.io.IOException;

import static spark.Spark.*;

/**
 * Class containing two methods - one for starting the server, API and all related functions,
 * and one for shutting these down safely.
 * @author Patriksku
 */
public class Server {

    /**
     * Creates a thread that makes sure that the server stops safely, in case a user of this
     * application shuts down the server by exiting the GUI with the X-button on the top
     * most right corner.
     */
    private void shutdownOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try {
                stop();
                Unirest.shutdown();
                System.out.println("Unirest has shutdown.");
                System.out.println("Server shutdown successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unirest shutdown error.");
            }
        }));
    }

    /**
     * Starts the Server with the API and website with all related functions.
     */
    public void startServer() {
        staticFileLocation("/public"); // makes http://localhost:4567/ the homepage of our website
        staticFiles.header("Access-Control-Allow-Origin", "*");

        //------------------CORS START------------------//
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        //-------------------CORS END-------------------//

        SrAPI srAPI = new SrAPI();
        SpotifyAPI spAPI = new SpotifyAPI();
        //ChartLyricsAPI clAPI = new ChartLyricsAPI();
        Apiseeds Aapi = new Apiseeds();

        srAPI.init();
        spAPI.init();
        //clAPI.init();
        Aapi.init();

        StandardStatusMessage standardStatusMessage = new StandardStatusMessage();
        standardStatusMessage.init();
        shutdownOnExit();
    }

    /**
     * Shuts down the Server safely.
     */
    public void shutdownServer() {
        try {
            stop();
            Unirest.shutdown();
            System.out.println("Unirest has shutdown.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unirest shutdown error.");
        }
    }
}