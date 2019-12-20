package StartServer;

import SR.API.SrAPI;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

import static spark.Spark.stop;

/**
 * Starts the server with our mash-up REST API from Sveriges Radio API, Spotify API and ChartLyrics API.
 * @author Patriksku
 */
public class StartServer {

    /**
     * När vi stänger applikationen behöver vi få exit code 0 -> vilket innebär att allt gick korrekt.
     * Just nu har vi ingen UI (t.ex. en knapp för "Shutdown Server") som vi kan använda. Detta är ingenting
     * viktigt just nu, men det är ett krav att stänga ner servern korrekt och Unirest när projektet väl ska
     * lämnas in. Jag fixar detta när hela projektet är klart.
     */
    private static void shutdownOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try {
                stop();
                Unirest.shutdown();
                System.out.println("Unirest has been shutdown successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unirest shutdown error.");
            }
        }));
    }

    public static void main(String[]args){
        SrAPI srAPI = new SrAPI();
        srAPI.init();

        shutdownOnExit();
    }
}
