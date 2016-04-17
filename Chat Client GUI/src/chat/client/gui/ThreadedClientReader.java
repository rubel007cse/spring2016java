/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.client.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class ThreadedClientReader extends Thread {

    private Socket socket;
    private FXMLDocumentController controller;

    public ThreadedClientReader(Socket socket, FXMLDocumentController controller) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
            byte[] messageBytes = new byte[1000];
            while (true) {
                int length = in.read(messageBytes);
                if (length < 0)
                    break;
                String message = new String(messageBytes).substring(0, length);
                //System.out.printf("Server sent [%s]\n", message);
                controller.addMessage(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadedClientReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
