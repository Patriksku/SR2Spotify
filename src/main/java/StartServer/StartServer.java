package StartServer;

import SR.API.SrAPI;

/**
 * Starts the server with all provided functionalities.
 * @author Patriksku
 */
public class StartServer {

    public static void main(String[]args){
        SrAPI srAPI = new SrAPI();

        srAPI.init();

    }
}
