package server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Антон on 07.05.2017.
 * This class has only one field which contains list of clients
 */
public class Users {
    public final static List<ServerThread> listUsers = Collections.synchronizedList(new LinkedList<ServerThread>());
}
