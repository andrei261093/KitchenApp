package tcpClient;

import controllers.MainController;
import org.json.JSONObject;
import staticUtils.StaticUtilsVariables;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by itsix on 6/26/2017.
 */
public class TcpClient{

    private boolean isConnected = false;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;
    private MainController mainController;

    public TcpClient(MainController mainController) {
        this.mainController = mainController;
    }

    public void connect() {
        if (isConnected == false) {
            try {
                sock = new Socket(StaticUtilsVariables.SERVER_IP, StaticUtilsVariables.SERVER_PORT);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                //ta_chat.append("connected");
                isConnected = true;
            } catch (Exception ex) {
              //  ta_chat.append("Cannot Connect! Try Again. \n");
              //  tf_username.setEditable(true);
            }

            ListenThread();

        } else if (isConnected == true) {
          //  ta_chat.append("You are already connected. \n");
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public class IncomingReader implements Runnable {

        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    System.out.println(stream);
                    try{
                        JSONObject orderJson = new JSONObject(stream);
                        mainController.addOrder(orderJson);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {

            }
        }
    }

    public void sentToServer(String message){
        try {
            writer.println(message);
            writer.flush(); // flushes the buffer
        } catch (Exception ex) {
            System.out.println("Message was not sent. \n");
        }
    }
}
