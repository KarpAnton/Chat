package client;

/**
 * Created by Антон on 08.05.2017.
 */
public class MainClient3 {
    public static void main(String[] args) {
        Client client = new Client();
        client.writeName();
        System.out.println("Now you can write and send messages");
        while (true){
            client.write();
        }
    }
}
