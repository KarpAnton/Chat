package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Антон on 07.05.2017.
 */
public class Server {
    /**
     * queueMessage is field which stores the last 10 Messages
     */
    public static Queue<String> queueMessages = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) throws IOException {
        ServerSocket listener = null;
        Socket socketClient = null;
        try {
            listener = new ServerSocket(1234);
            while (true) {
                socketClient = listener.accept();
                ServerThread thread = new ServerThread(socketClient);
                Users.listUsers.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            listener.close();
            socketClient.close();
        }
    }
}
