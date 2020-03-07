package Spotify.Functions;

import Handlers.FormatHandler;
import Lyrics.Functions.StringSimplifier;
import SR.Beans.Radio;
import SR.Functions.ChannelSongs;
import Spotify.API.SpotifyAPI;

import Spotify.Beans.*;
import Spotify.Users.UserSessions;
import com.google.gson.FieldAttributes;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.jetty.io.EndPoint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;


/**
 * This class is responsible for returning JSON, that contains track name, track URL,
 * track uri, artist name and artist uri.
 * @author Sara Karic
 */

public class SearchSpotify {

    private final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";
    private UserSessions userSessions;
    private HttpResponse<JsonNode> response;
    private StringSimplifier simply = new StringSimplifier();
    public JSONObject tracks;


    public SearchSpotify(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    /**
     * Requests search for track.
     * @param search for track.
     * @param session_id for the user.
     * @return JSON.
     */

    public String requestSearch(String search, String session_id) {


        try {
            response = Unirest.get(SEARCH_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("q", search)
                    .queryString("type", "track")
                    .asJson();


        } catch (UnirestException e) {
            e.printStackTrace();

        }
        return getSearchTrack(response.getBody().getObject());

    }

    /**
     * @param envelope contains the whole JSON
     * @return JSON search for track and artist.
     */

    public String getSearchTrack(JSONObject envelope) {
        Radio radio = ChannelSongs.getCurrentRadio();
        tracks = envelope.getJSONObject("tracks");
        JSONArray items = tracks.getJSONArray("items");
        String SRartist = simply.simplyString(radio.getArtist());
        System.out.println(SRartist);
        JSONObject artist = null;
        String trackname = null;
        JSONObject external_urls = null;
        String trackURL = null;
        String trackuri = null;
        String artistname = null;
        String artisturi = null;
        JSONObject artistdata;
        AllArray allArray = new AllArray();
        JSONObject itemsdata = null;
        JSONArray artists = null;




        for (int i = 0; i < items.length(); i++) {
          //  System.out.println(envelope);
            itemsdata = items.getJSONObject(i);
            artists = itemsdata.getJSONArray("artists");
            System.out.println(artists.getJSONObject(0));
            artist = artists.getJSONObject(0);
            System.out.println(artists.getJSONObject(0));
            artist = artists.getJSONObject(0);
            for (int y = 0; y < artists.length(); y++) {
                if (SRartist.equals(simply.simplyString(artists.getJSONObject(y).getString("name")))) {
                    artist = artists.getJSONObject(y);
                    artistname = artist.getString("name");
                    artisturi = artist.getString("uri");
                    trackname = itemsdata.getString("name");
                    external_urls = itemsdata.getJSONObject("external_urls");
                    trackURL = external_urls.getString("spotify");
                    trackuri = itemsdata.getString("uri");

                    y = artists.length();
                    i = items.length();

                    allArray.setArtist_name(artistname);
                    allArray.setArtist_uri(artisturi);
                    allArray.setTrack_name(trackname);
                    allArray.setTrack_url(trackURL);
                    allArray.setTrack_uri(trackuri);

                }

            }


        }

        if (artistname == null) {
            itemsdata = items.getJSONObject(0);
            artists = itemsdata.getJSONArray("artists");
            System.out.println(artists.getJSONObject(0));
            artist = artists.getJSONObject(0);
            artistname = artist.getString("name");
            artisturi = artist.getString("uri");
            trackname = itemsdata.getString("name");
            external_urls = itemsdata.getJSONObject("external_urls");
            trackURL = external_urls.getString("spotify");
            trackuri = itemsdata.getString("uri");

            allArray.setArtist_name(artistname);
            allArray.setArtist_uri(artisturi);
            allArray.setTrack_name(trackname);
            allArray.setTrack_url(trackURL);
            allArray.setTrack_uri(trackuri);
            }

        FormatHandler formatHandler = new FormatHandler();
        return formatHandler.getFormatAll(allArray);


    }

}
