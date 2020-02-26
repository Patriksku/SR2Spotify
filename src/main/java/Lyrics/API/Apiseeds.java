package Lyrics.API;
import Handlers.FormatHandler;
import Lyrics.Functions.Lyrics;
import Lyrics.Functions.StringSimplifier;
import SR.API.SrAPI;
import SR.Beans.Radio;
import SR.Functions.ChannelSongs;
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
 * class that gets the lyrics from APIseeds
 * @author RacquelC
 */

public class Apiseeds {
    private final String path = "/api/v2/lyrics";
    private final String domain = "https://orion.apiseeds.com/api/music/lyric/";
    private final String key = "apikey=ZyX167NCY5HPP0emPMV7pmZhVAl4XJtytkY7OLawdxJ7vJ2Sx20vUCvCGMsjIwY2";
    private FormatHandler formatHandler = new FormatHandler();
    private HttpResponse<JsonNode> responseNode;
    private StringSimplifier simply = new StringSimplifier();
    ;

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

                responseNode = Unirest.get(URI).queryString("format", "json").asJson();
                JsonNode json = responseNode.getBody();
                JSONObject envelope = json.getObject();
                String text = envelope.getJSONObject("result").getJSONObject("track").getString("text");
                lyrics.setText(text);

                System.out.println("200");
                response.status(200);

            }
            catch (Exception e){
                System.out.println("204"); //no artist or an error happens
                response.status(204);
                response.type("text/plain");
                e.printStackTrace();
                return "No lyrics available";

            }
            response.type("application/json");
            return formatHandler.getFormat(lyrics);
        });
        return null;
    }

    //http://localhost:4567/api/v2/lyrics/getLyricsRadio
    public Document getLyricsFromSR(){

        get(path + "/getLyricsRadio", (request, response) -> {

            Radio radio = ChannelSongs.getCurrentRadio();
            //System.out.println("comes here fist: " + radio == null);
            /*System.out.println("comes here");*/
            if (radio != null) {
                String artist = radio.getArtist();
                artist = simply.simplyString(artist);
                System.out.println(artist);
                String song = radio.getTitle();
                song = simply.simplyString(song);
                System.out.println(song);
                String URI = domain + artist + "/" + song + "?" + key;
                Lyrics lyrics = new Lyrics();
                System.out.println(URI); //test

                try {

                    responseNode = Unirest.get(URI).queryString("format", "json").asJson();
                    JsonNode json = responseNode.getBody();
                    JSONObject envelope = json.getObject();
                    String text = envelope.getJSONObject("result").getJSONObject("track").getString("text");
                    lyrics.setText(text);

                    System.out.println("200");
                    response.status(200);

                } catch (Exception e) {
                    System.out.println("404"); //no artist or an error happens
                    response.status(404);
                    response.type("text/plain");
                    e.printStackTrace();
                    return "No lyrics available";

                }
                response.type("application/json");
                return formatHandler.getFormat(lyrics);
            }
            else{
                response.status(400);
                response.type("text/plain");
                return "no channel selected";
            }
        });
        return null;
    }

    /**
     * initilizes functions in this class
     */
    public void init(){

        //getLyricsJson();
        getLyricsFromSR();
    }
}



