package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {

    private Vector<ClientHandler> clients;

    public Server() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
//            String test = AuthService.getNickByLoginAndPass("login1", "pass1");
//            System.out.println(test);
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o: clients) {
            if (!o.isBlackList(from.getNick()))
                o.sendMsg(msg);
        }
    }

    public boolean isNickBusy(String nick){
        for(ClientHandler client: clients){
            if(client.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }

    public void sendPersonalMsg(ClientHandler from, String recipient, String msg){
        for (ClientHandler client: clients) {
            if (client.getNick().equals(recipient)){
                client.sendMsg("from: " + from.getNick() + "\n to you: " + msg);
                from.sendMsg("You to " + client.getNick() + " " + msg);
                return;
            }
        }
        from.sendMsg("There are no user " + recipient + " in the chat now (");
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

}
