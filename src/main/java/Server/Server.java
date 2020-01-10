package Server;

import Handlers.StandardStatusMessage;
import Lyrics.API.ChartLyricsAPI;
import SR.API.SrAPI;
import Spotify.API.SpotifyAPI;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

import static spark.Spark.*;

/**
 * Starts the server with our mash-up REST API from Sveriges Radio API, Spotify API and ChartLyrics API.
 * @author Patriksku
 */
public class Server {
    private boolean running;
    private boolean threadExist;

    /**
     * När vi stänger applikationen behöver vi få exit code 0 -> vilket innebär att allt gick korrekt.
     * Just nu har vi ingen UI (t.ex. en knapp för "Shutdown Server") som vi kan använda. Detta är ingenting
     * viktigt just nu, men det är ett krav att stänga ner servern korrekt och Unirest när projektet väl ska
     * lämnas in. Jag fixar detta när hela projektet är klart.
     */
    private void shutdownOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try {
                stop();
                Unirest.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unirest shutdown error.");
            }
        }));
    }

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
        ChartLyricsAPI clAPI = new ChartLyricsAPI();

        srAPI.init();
        spAPI.init();
        clAPI.init();

        StandardStatusMessage standardStatusMessage = new StandardStatusMessage();
        standardStatusMessage.init();

        if (!threadExist) {
            System.out.println("Timing a thread for alternative shutdown.");
            shutdownOnExit();
            threadExist = true;
        }
        running = true;
    }

    public void shutdownServer() {
        try {
            stop();
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unirest shutdown error.");
        }
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}