package Spotify.Functions;

import Handlers.FormatHandler;
import Spotify.API.SpotifyAPI;

import Spotify.Beans.*;
import Spotify.Users.UserSessions;
import com.google.gson.FieldAttributes;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
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
        return getSearch(response.getBody().getObject());

    }

    public String getSearch(JSONObject envelope) {
        JSONArray albums = envelope.getJSONArray("albums");
        JSONObject objectInsideAlbums;
        JSONArray artists;
        String artisturi;
        int amountOfArtistItems = albums.length();
        AllArray allArray = new AllArray(amountOfArtistItems);
        JSONObject[] objectsofArtistItems = new JSONObject[amountOfArtistItems];


        for (int i = 0; i < amountOfArtistItems; i++) {
            Artist artist = new Artist();
            objectsofArtistItems[i] = albums.getJSONObject(i);
            objectInsideAlbums = albums.getJSONObject(i);
            artists = objectInsideAlbums.getJSONArray("artists");
            artisturi = artists.getJSONObject(0).getString("uri");

            artist.setArtist_name(objectsofArtistItems[i].getString("name"));
            artist.setArtist_uri(SEARCH_ENDPOINT + objectsofArtistItems[i].getString("id"));
            artist.setArtist_id(objectsofArtistItems[i].getString("id"));
            artist.setArtist_uri(artisturi);
            System.out.println(artist.toString());
            allArray.getArrayOfArtistItems()[i] = artist;

        }

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormatAll(allArray);


    }


}
