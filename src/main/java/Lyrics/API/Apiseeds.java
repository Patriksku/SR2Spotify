package Lyrics.API;
import Handlers.FormatHandler;
import Lyrics.Functions.Lyrics;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.swing.text.Document;

import static spark.Spark.get;


/**
 *
 * class that gets the lyrics from chartlyrics
 * @author RacquelC
 */

public class Apiseeds {
    private final String path = "/api/v2/lyrics";
    private final String domain = "https://orion.apiseeds.com/api/music/lyric/";
    private final String key = "apikey=ZyX167NCY5HPP0emPMV7pmZhVAl4XJtytkY7OLawdxJ7vJ2Sx20vUCvCGMsjIwY2";
    private FormatHandler formatHandler = new FormatHandler();
    private HttpResponse<JsonNode> response2;

   // http://localhost:4567/api/v2/lyrics/getLyrics/artist/song

    public Document getLyricsJson(){
        /*System.out.println("comes here fist");*/
        get(path + "/getLyrics/:artist/:song", (request, response) ->{
            /*System.out.println("comes here");*/
            String artist = request.params(":artist");
            String song = request.params(":song");
            String URI = domain + artist + "/" + song + "?" + key;
            Lyrics lyrics = new Lyrics();
            System.out.println(URI); //test

            try{

                response2 = Unirest.get(URI).queryString("format", "json").asJson();
                JsonNode json = response2.getBody();
                JSONObject envelope = json.getObject();
                String text = envelope.getJSONObject("result").getJSONObject("track").getString("text");
                lyrics.setText(text);
                
                System.out.println("200");
                response.status(200);

            }
            catch (Exception e){
                System.out.println("404"); //no artist or an error happens
                response.status(404);
                response.type("text/plain");
                e.printStackTrace();
                return "No lyrics available";

            }
            response.type("application/json");
            return formatHandler.getFormat(lyrics);
        });
        return null;
    }

    /**
     * initilizes functions in this class
     */
    public void init(){
        getLyricsJson();
    }
}



