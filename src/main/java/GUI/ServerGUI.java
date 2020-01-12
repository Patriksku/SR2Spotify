package GUI;

import Server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for building and creating the functionalities of the GUI.
 * @author Patriksku
 */
public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLbl;
    private Server server;

    /**
     * Sets the functionalities for the GUI when this class is instantiated.
     */
    public ServerGUI() {
        stopServerButton.setEnabled(false);
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    server = new Server();
                }
                server.startServer();
                statusLbl.setText("Server is running.");
                startServerButton.setEnabled(false);
                stopServerButton.setEnabled(true);
            }
        });

        stopServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.shutdownServer();
                statusLbl.setText("Server has successfully stopped.");
                System.out.println("Server has successfully stopped.");
                System.exit(0);
            }
        });
    }

    public JPanel mainPanel() {
        return mainPanel;
    }
}
