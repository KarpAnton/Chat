package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Антон on 07.05.2017.
 * This class executes the exchange between different clients.
 */
public class ServerThread extends Thread {
    private PrintWriter printWriter;
    private BufferedReader reader;
    private Socket socket;
    private String userName;

    public ServerThread(Socket socket){
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void run() {
        setUserName();
        if(!Server.queueMessages.isEmpty()){
            showLastMessages();
        }
        for (ServerThread serverThread : Users.listUsers) {
            if(this!=serverThread)
                serverThread.getPrintWriter().println(userName + " was come to chat!");
        }
        String message;
        try {
            while (true) {
                message = getReader().readLine();
                addMessageToQueue(userName + " : " + message);
            for (ServerThread serverThread : Users.listUsers) {
                if(this!=serverThread)
                    serverThread.getPrintWriter().println(userName + " : " + message);
            }
        }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    /**
     * This method adds message sent from a client to another one inside of queue, that than to show the last 10 messages
     * a new client. The queue's size is 10.
     * @param message
     */
    private void addMessageToQueue(String message){
        if(Server.queueMessages.size()==10){
            Server.queueMessages.poll();
            Server.queueMessages.offer(message);
        }else if(Server.queueMessages.size()<10){
            Server.queueMessages.offer(message);
        }
    }

    /**
     * Polls all elements of queue and creates the StringBuilder object to transfer the new client the last 10 messages
     */
    private void showLastMessages(){
        StringBuilder builder = new StringBuilder();
        builder.append("The last messages:"+"\r\n");
        for(String s : Server.queueMessages){
            builder.append(s).append("\n\r");
        }
        getPrintWriter().println(builder);
    }

    /**
     * Sets the user's name
     */
    private void setUserName(){

        try {
            userName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closes all streams and socket
     */
    private void close(){
        printWriter.close();
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
