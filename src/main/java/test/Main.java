package test;

import EditDoc.EditDoc;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import static spark.Spark.*;

public class Main {

    public static String returnHello() {
        get("/test", (request, response) -> {

            return null;
        });
        return null;
    }

    public static String bomba() {
        get("/test/:name", (request, response) -> {

            return "You is: " + request.params(":name");
        });
        return null;
    }

    public static Document getSongs() {
        get("/nu", (request, response) -> {
            try {
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = docBuilder.parse("http://api.sr.se/api/v2/playlists/rightnow?channelid=132");
                /*EditDoc edit = new EditDoc();
                Document songsDoc = edit.songs(document);*/
                /*response.status(200);
                response.type("text/xml");*/
                System.out.println("Sosa");
                return document;
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (SAXException sax) {
                sax.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println("barba");
            response.status(401);
            return null;

        });
        System.out.println("rosa");
        return null;
    }

    public static void runTestAPI(){
        returnHello();
        bomba();
        getSongs();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.runTestAPI();
    }
}
