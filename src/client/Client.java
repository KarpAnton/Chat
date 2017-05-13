package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Антон on 07.05.2017.
 */

public class Client {
    private PrintWriter printWriter;
    private BufferedReader reader;
    private Socket socket;
    private Scanner in = new Scanner(System.in);
    public Client(){
        try {
            this.socket = new Socket("localhost", 1234);
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new ClientThread().start();
        } catch (IOException e) {
            System.err.println("There are mistakes after during the generation of Client's object ");
            e.printStackTrace();
            close();
        }
    }

    /*public void read() throws IOException {
        System.out.println(reader.readLine());
    }*/
    public void writeName(){
        System.out.println("Enter your name: ");
        write();
    }

    public void write(){
        String s = in.nextLine();
        if (s!="")
        printWriter.println(s);

    }
    /**
     * This inner class listens to server to get answers from other clients
     */
    private class ClientThread extends Thread{
        private boolean isStopped = true;

        public void setStopped(boolean stopped) {
            isStopped = stopped;
        }

        public void run(){
            try {
                while (isStopped){
                    String str = reader.readLine();
                    if(str!=null)
                        System.out.println(str);
                }
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
    }
    /**
     * This method closes all streams such as PrintStream, BufferedReader and Socket
     * */
    private void close(){
        printWriter.flush();
        printWriter.close();
        in.close();
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
