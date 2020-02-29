package Lyrics.API;

import Handlers.FormatHandler;
import Lyrics.Functions.Lyrics;
import Lyrics.Functions.StringSimplifier;
import SR.API.SrAPI;
import SR.Beans.Radio;
import SR.Functions.ChannelSongs;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Response;
import spark.Spark;

import javax.swing.text.Document;

import static spark.Spark.get;


/**
 * class that gets the lyrics from APIseeds
 *
 * @author RacquelC
 */

public class Apiseeds {
    private final String path = "/api/v2/lyrics";
    private final String domain = "https://orion.apiseeds.com/api/music/lyric/";
    private final String key = "apikey=ZyX167NCY5HPP0emPMV7pmZhVAl4XJtytkY7OLawdxJ7vJ2Sx20vUCvCGMsjIwY2";
    private FormatHandler formatHandler = new FormatHandler();
    private HttpResponse<JsonNode> responseNode;
    private StringSimplifier simply = new StringSimplifier();
    private String artist = "";
    private String track = "";


    // http://localhost:4567/api/v2/lyrics/getLyrics/artist/song

    public Document getLyricsJson() {
        /*System.out.println("comes here fist");*/
        get(path + "/getLyrics/:artist/:song", (request, response) -> {
            /*System.out.println("comes here");*/
            String artist = request.params(":artist");
            String song = request.params(":song");
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

    //http://localhost:4567/api/v2/lyrics/getLyricsRadio/channelid
    public Document getLyricsFromSR() {

        get(path + "/getLyricsRadio/:channelid", (request, response) -> {

            //Radio radio = ChannelSongs.getCurrentRadio();
            //System.out.println("comes here fist: " + radio == null);
            /*System.out.println("comes here");*/
            String channelid = request.params(":channelid");
            setSRSONG(channelid, response);
            artist = simply.simplyString(artist);
            System.out.println(artist);
            track = simply.simplyString(track);
            System.out.println(track);
            String URI = domain + artist + "/" + track + "?" + key;
            Lyrics lyrics = new Lyrics();
            System.out.println(URI); //test
            if (artist.equals("")) {
                System.out.println("404"); //no artist
                response.status(404);
                response.type("text/plain");
                return "No artist playing";
            }


            try {

                responseNode = Unirest.get(URI).queryString("format", "json").asJson();
                JsonNode json = responseNode.getBody();
                JSONObject envelope = json.getObject();
                String text = envelope.getJSONObject("result").getJSONObject("track").getString("text");
                lyrics.setText(text);

                System.out.println("200");
                response.status(200);

            } catch (Exception e) {
                System.out.println("404"); //no lyrics
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

    public void setSRSONG(String channelid, Response response) {
        SrAPI srAPI = new SrAPI();
        String domain = srAPI.getdomain();
        System.out.println("here1");
        String URI = domain + channelid;
        ChannelSongs channelSongs = new ChannelSongs();

        response.status(200);
        response.type("application/json");
        System.out.println("here2");
        String owo = channelSongs.getFormat(URI, response);


        try {
            JSONObject json = new JSONObject(owo);
            if (json.has("artist")) {
                artist = json.getString("artist");
            }
            if (json.has("title")) {
                track = json.getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(artist);
        System.out.println(track);
    }

    /**
     * initilizes functions in this class
     */
    public void init() {

        //getLyricsJson();
        getLyricsFromSR();
    }
}



