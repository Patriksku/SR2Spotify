package Lyrics.API;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import CORS.CORS;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.Unirest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.io.IOException;

import org.json.XML;
import org.w3c.dom.Document;

/**
 *
 * class that gets the lyrics from chartlyrics
 * @author RacquelC
 */

public class ChartlyricsAPI {

    private final String path = "/api/chartlyrics";
    private final String domain = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist="; // dont forget &song= in the end
    private CORS cors = new CORS();


    //http://localhost:4567/api/chartlyrics/getLyrics

    /**
     * gets the lyrics of a song by seraching for the artist name and song
     * artist name is maximum 75 chars
     * Songs name is maximun 125 chars
     * @return string with lyrics
     */
    public String getLyrics(){

        get(path + "/getLyrics/:artist/:song", (request, response) ->{ //what happenes if there is a space in song/artist? %20 for space, doesnt work with space
            cors.addSupport(request, response);
            String lyrics = "no lyrics found";
            String body = "";
            String artist = request.params(":artist");
            String song = request.params(":song");
            String URI = domain + artist + "&song=" + song;
            System.out.println(URI); //test

            try{
                body = Unirest.get(URI).asString().getBody();
                try{
                    JSONObject xmlJsonObj = XML.toJSONObject(body);
                    String jsonPrettyPrintString = xmlJsonObj.toString(4);
                    System.out.println(jsonPrettyPrintString);
                    JSONObject json = new JSONObject(jsonPrettyPrintString);
                    lyrics = json.getJSONObject("GetLyricResult").getString("Lyric");
                    System.out.println(lyrics);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(lyrics.equals("")){
                return "no lyrics found";
            }
            return lyrics;
        });
        return "failed to get lyrics";
    }



    public void init(){
        getLyrics();

    }


}
