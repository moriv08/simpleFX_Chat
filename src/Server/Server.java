package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;


    public Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket;
        try {
            server = new ServerSocket(8180);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                clients.add(new ClientHandler(socket, this));
            }

        } catch (IOException e) {
            System.out.println("AAAAAAAAA");
            e.printStackTrace();
        }
//        finally {
//            try{
//                server.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void broadcastMsg(String msg){
        for (ClientHandler client: clients)
            client.sendClientMsg(msg);
    }



//    public Server() throws SQLException {
////        clients = new Vector<>();
//        ServerSocket server = null;
//        Socket socket = null;
//
//        try {
////            AuthService.connect();
////            String test = AuthService.getNickByLoginAndPass("login1", "pass1");
////            System.out.println(test);
//            server = new ServerSocket(8189);
//            System.out.println("Сервер запущен!");
//
//            while (true) {
//                socket = server.accept();
//                new ClientHandler(this, socket);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                server.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            AuthService.disconnect();
//        }
//    }

//    public void broadcastMsg(String msg) {
//        for (ClientHandler o: clients) {
//            o.sendMsg(msg);
//        }
//    }
//
//    public void subscribe(ClientHandler client) {
//        clients.add(client);
//    }
//
//    public void unsubscribe(ClientHandler client) {
//        clients.remove(client);
//    }

}
