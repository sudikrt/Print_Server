/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static print_server.ServerFrame.port;

/**
 *
 * @author Sudarshan
 */
public class StartServer extends Thread{

    int port;
    ServerSocket serverSocket = null;
    Socket socket = null;
    public StartServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        //super.run(); //To change body of generated methods, choose Tools | Templates.
        try {
            serverSocket = new ServerSocket(port);
            ServerFrame.updateMsgWindow("Info.: Server started successfully");
            ServerFrame.updateMsgWindow("Info: Accepting incoming connections");
        } catch (IOException ex) {
            ServerFrame.updateMsgWindow("ERROR: Server Instantian Error ");
            System.exit(1);
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        do
        {                    
            try {
                //ACCEPT NEW CLIENT
                socket = serverSocket.accept();
                AddClientRequest  addClientRequest = new AddClientRequest(socket);
                addClientRequest.start();
            } catch (IOException ex) {
                ServerFrame.updateMsgWindow("ERROR: Accepting incoming connections error");
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(true);
    }
    
       
}
