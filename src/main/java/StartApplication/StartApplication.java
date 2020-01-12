package StartApplication;

import GUI.ServerGUI;

import javax.swing.*;

/**
 * Starts the application with a GUI containing two buttons -
 * one for starting the server, and one for shutting it down safely.
 * The application is automatically terminated when shutting down the server.
 * @author Patriksku
 */
public class StartApplication {

    public static void main(String[]args) {
        ServerGUI gui = new ServerGUI();
        gui.setTitle("SR2Spotify - Server");
        gui.setContentPane(gui.mainPanel());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

    }
}
