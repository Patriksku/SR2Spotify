package Spotify.Functions;

import Handlers.FormatHandler;
import Spotify.API.SpotifyAPI;
import Spotify.API.Search;
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

public class SearchSpotify extends Search {

    private final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";
    private final String SEARCH_DOMAIN = "https://open.spotify.com/search/";
    private UserSessions userSessions;
    private HttpResponse<JsonNode> response;



    public SearchSpotify(UserSessions userSessions) {
        this.userSessions = userSessions;
    }

    public String requestSearch(String search, String session_id, String format) {


        try {
            response = Unirest.get(SEARCH_ENDPOINT)
                    .header("Authorization", (userSessions.get(session_id).getToken().getToken_type() + " " + userSessions.get(session_id).getToken().getAccess_token()))
                    .queryString("q", search)
                    .queryString("type", "album,artist,playlist,track")
                    .asJson();


        } catch (UnirestException e) {
            e.printStackTrace();

        }
        return getSearch(response.getBody().getObject(), format);

    }

    public String getSearch(JSONObject envelope, String format) {
        FormatHandler formatHandler = new FormatHandler();
        JSONArray searchItemsAlbum = envelope.getJSONArray("searchItemsAlbum");
        JSONArray searchItemsArtist = envelope.getJSONArray("searchItemsArtist");
        JSONArray searchItemsPlaylist = envelope.getJSONArray("searchItemsPlaylist");
        JSONArray searchItemsTrack = envelope.getJSONArray("searchItemsTrack");


        int amountOfAlbumItems = searchItemsAlbum.length();
        int amountOfArtistItems = searchItemsArtist.length();
        int amountOfPlaylists = searchItemsPlaylist.length();
        int amountOfTrackItems = searchItemsTrack.length();

        AllArray allArray = new AllArray(amountOfAlbumItems, amountOfArtistItems, amountOfPlaylists, amountOfTrackItems);


        JSONObject[] objectsofAlbumItems = new JSONObject[amountOfAlbumItems];
        JSONObject[] objectsofArtistItems = new JSONObject[amountOfArtistItems];
        JSONObject[] objectsofPlaylistItems = new JSONObject[amountOfPlaylists];
        JSONObject[] objectsofTrackItems = new JSONObject[amountOfTrackItems];


        for (int i = 0; i < amountOfAlbumItems; i++) {
            Album album = new Album();
            objectsofAlbumItems[i] = searchItemsAlbum.getJSONObject(i);

            album.setAlbum_name(objectsofAlbumItems[i].getString("name"));
            album.setAlbum_uri(SEARCH_ENDPOINT + objectsofAlbumItems[i].getString("id"));
            album.setAlbum_id(objectsofAlbumItems[i].getString("id"));

            return formatHandler.getFormatAll(format, allArray);

        }

        for (int i = 0; i < amountOfArtistItems; i++) {
            Artist artist = new Artist();
            objectsofArtistItems[i] = searchItemsArtist.getJSONObject(i);

            artist.setArtist_name(objectsofArtistItems[i].getString("name"));
            artist.setArtist_uri(SEARCH_ENDPOINT + objectsofArtistItems[i].getString("id"));
            artist.setArtist_id(objectsofArtistItems[i].getString("id"));

            return formatHandler.getFormatAll(format, allArray);


        }

        for (int i = 0; i < amountOfPlaylists; i++) {
            Playlist playlist = new Playlist();
            objectsofPlaylistItems[i] = searchItemsPlaylist.getJSONObject(i);

            playlist.setPlaylist_name(objectsofPlaylistItems[i].getString("name"));
            playlist.setSpotify_uri(SEARCH_ENDPOINT + objectsofPlaylistItems[i].getString("id"));
            playlist.setPlaylist_id(objectsofPlaylistItems[i].getString("id"));

            return formatHandler.getFormatAll(format, allArray);
        }

            for (int i = 0; i < amountOfTrackItems; i++) {
                Track track = new Track();
                objectsofTrackItems[i] = searchItemsTrack.getJSONObject(i);

                track.setTrack_name(objectsofTrackItems[i].getString("name"));
                track.setTrack_uri(SEARCH_ENDPOINT + objectsofTrackItems[i].getString("id"));
                track.setTrack_id(objectsofTrackItems[i].getString("id"));

                return formatHandler.getFormatAll(format, allArray);

            }




return null;
    }











     //   JSONObject envelope = response.getBody().getObject();
     //   System.out.println(envelope);


    }


