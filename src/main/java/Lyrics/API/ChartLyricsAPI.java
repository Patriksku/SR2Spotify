package Lyrics.API;

import static spark.Spark.defaultResponseTransformer;
import static spark.Spark.get;
import CORS.CORS;
import Handlers.FormatHandler;
import Lyrics.Functions.Lyrics;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.Body;
import org.json.JSONObject;
import org.json.XML;

import javax.swing.text.Document;


/**
 *
 * class that gets the lyrics from chartlyrics
 * @author RacquelC
 */
public class ChartLyricsAPI {

    private final String path = "/api/chartlyrics";
    private final String domain = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=";
    private CORS cors = new CORS();
    FormatHandler formatHandler = new FormatHandler();



    //http://localhost:4567/api/chartlyrics/getLyrics/artist/song

    /**
     * gets the lyrics of a song by seraching for the artist name and song
     * artist name is maximum 75 chars
     * Songs name is maximun 125 chars
     * artists and song with space in the name need to have a + where the space should be
     * @return string with lyrics
     */
    public String getLyrics(){

        get(path + "/getLyrics/:artist/:song", (request, response) ->{
            cors.addSupport(request, response);
            String lyrics = "no lyrics found";
            String body = "";
            String artist = request.params(":artist");
            String song = request.params(":song");
            String URI = domain + artist + "&song=" + song;
            // System.out.println(URI); //test

            try{
                body = Unirest.get(URI).asString().getBody();
                try{
                    JSONObject xmlJsonObj = XML.toJSONObject(body);
                    String jsonPrettyPrintString = xmlJsonObj.toString(4);
                    //System.out.println(jsonPrettyPrintString);
                    JSONObject json = new JSONObject(jsonPrettyPrintString);
                    System.out.println(json);
                    lyrics = json.getJSONObject("GetLyricResult").getString("Lyric");
                    //System.out.println(lyrics);
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

    /**
     * gets the lyrics of a song by seraching for the artist name and song
     *  artist name is maximum 75 chars
     * Songs name is maximun 125 chars
     * artists and song with space in the name need to have a + where the space should be
     * @return JSON with lyrics
     * @return
     */
    public Document getLyricsJson(){

        get(path + "/getLyrics/:artist/:song", (request, response) ->{

            String artist = request.params(":artist");
            String song = request.params(":song");
            String URI = domain + artist + "&song=" + song;
            Lyrics lyrics = new Lyrics();
            String body = "";
            // System.out.println(URI); //test

            try{
                body = Unirest.get(URI).asString().getBody();
                try{
                    JSONObject xmlJsonObj = XML.toJSONObject(body);
                    String jsonPrettyPrintString = xmlJsonObj.toString(4);
                    //System.out.println(jsonPrettyPrintString);
                    JSONObject json = new JSONObject(jsonPrettyPrintString);
                    System.out.println(json);
                    lyrics.setText(json.getJSONObject("GetLyricResult").getString("Lyric"));
                    //System.out.println(lyrics);
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
