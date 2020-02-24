package Spotify.Functions;

import Handlers.FormatHandler;
import Spotify.Beans.AllArray;
import Spotify.Beans.Artist;
import Spotify.Users.UserSessions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author Sara Karic
 */

public class SearchSpotify {

    private final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";
    private final String SEARCH_DOMAIN = "https://open.spotify.com/search/";
    private UserSessions userSessions;
    private HttpResponse<JsonNode> response;


    public SearchSpotify(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    public String requestSearch(String search, String session_id) {


        try {
            response = Unirest.get(SEARCH_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("q", search)
                    .queryString("type", "album,artist,playlist,track")
                    .asJson();


        } catch (UnirestException e) {
            e.printStackTrace();

        }
        /*JSONObject envelope = response.getBody().getObject();
        System.out.println(envelope);*/
        return getSearch(response.getBody().getObject());

    }

    public String getSearch(JSONObject envelope) {
        System.out.println(envelope);
        JSONObject albums = envelope.getJSONObject("albums");
        JSONArray artists = albums.getJSONArray("artists");
        JSONObject objectInsideAlbums;
        JSONObject objectInsideArtists;
      //  String albumname;
        String artistname;
        String uri;
        int amount = artists.length();
        AllArray allArray = new AllArray(amount);
        JSONObject[] objectsofArtists = new JSONObject[amount];
        //JSONObject[] object


        for (int i = 0; i < amount; i++) {
            AllArray allarray = new AllArray();
            objectsofArtists[i] = artists.getJSONObject(i);
            objectInsideArtists = artists.getJSONObject(i);
           // albumname = albums.getJSONObject(0).getString("name");
            artistname = artists.getJSONObject(0).getString("name");
            uri = artists.getJSONObject(0).getString("uri");


            allarray.setArtist_name(objectsofArtists[i].getString("name"));
            allarray.setArtist_uri(SEARCH_ENDPOINT + objectsofArtists[i].getString("uri"));

         //   FormatHandler formatHandler = new FormatHandler();
           // return formatHandler.getFormatAll(allArray);
            //allarray.setArtistItm..



        }

        FormatHandler format = new FormatHandler();
        return format.getFormat(allArray);
    }
}











//   JSONObject envelope = response.getBody().getObject();
//   System.out.println(envelope);




