/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sudarshan
 */
public class AddClientRequest extends Thread{

    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    FileOutputStream fileOutputStream = null;
    
    static int index;
    String file_name;
    public AddClientRequest(Socket socket) {
        this.socket = socket;
        index ++;
    }

    @Override
    public void run() {
        //super.run(); //To change body of generated methods, choose Tools | Templates.
        int var;
        ServerFrame.updateMsgWindow("Info: " + socket.getInetAddress().getHostName() + "Connected");
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            
            file_name = dataInputStream.readUTF();
            
            file_name = index + file_name;
            
            fileOutputStream = new FileOutputStream(ServerFrame.dir_path + file_name);
            
            while((var=dataInputStream.read())!=-1)
            {
                fileOutputStream.write(var);
            }
            File file = new File(ServerFrame.dir_path + file_name );
            
            ServerFrame.linkedList.add(file_name);
            
            while(!ServerFrame.linkedList.isEmpty()){
                String printFile=ServerFrame.linkedList.remove();//removes the first element
                DoPrint print=new DoPrint(ServerFrame.dir_path + printFile);
                print.start();
            }  
            socket.close(); 
            
        } catch (IOException ex) {
            Logger.getLogger(AddClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
