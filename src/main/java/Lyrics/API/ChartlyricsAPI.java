package Lyrics.API;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import org.w3c.dom.Document;

/**
 *
 * class that gets the lyrics from chartlyrics
 * @author RacquelC
 */

public class ChartlyricsAPI {

    //private static final String songURL = "http://api.chartlyrics.com/apiv1.asmx/";
    //private static final String lyricsURL ="http://api.chartlyrics.com/apiv1.asmx/";
    private final String path = "/api/chartlyrics"; //i dont use this maybe domain is supposed to be path? ask later
    private final String domain = "http://api.chartlyrics.com/apiv1.asmx/";


//    /**
////     * nani the fuck
////     * gets a list of viable songs
////     * wrong query
////     * @return
////     */
////    public static Document getSongs(String artist, String songName) {
////        get(domain + "SearchLyric?artist=" + artist + "&song=" + songName, (request, response) -> {
////            System.out.println("songs get request function");
////            System.out.println("get request: " + artist + " song " + request + " response " + response);
////            return null;
////        });
////        System.out.println("getSongs");
////        return null;
////
////    }

    /**
     * gets the lyrics of a song by seraching for the artist name and song
     * artist name is maximum 75 chars
     * Songs name is maximun 125 chars
     * @return
     */
    public Document searchLyricDirect(){
        get(domain + "SearchLyricDirect?", (request, response) ->{
            //String artist = SR.Functions.getArtist(); //fix later
            //String song = SR.Functions.getSong(); //fix later
            String artist = "Michael"; //test
            String song = "bad"; //test

            if(response != null){
               return domain + "SearchLyricDirect?artist=" +  artist + "&song=" + song;
            }

            return null;
        });
        return null;
    }



    public void init(){
        searchLyricDirect();

    }


}
