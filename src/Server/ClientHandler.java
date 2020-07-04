package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String nick;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            new Thread( () ->{
                while (true){
                    try (DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())
                    ){
                        String msg = in.readUTF();
                        System.out.println("Client said: " + msg);
                        if (msg.equals("/end")) {
                            out.writeUTF("/server closed.");
                            break;
                        }
                        server.broadcastMsg(msg);
                    }catch (IOException e){
                        System.err.println("Exeption");
                    }
                }
            }).start();

        } catch (IOException e){
            System.err.println("Exeption in ClientHandler Conctructor");
        }
        finally {
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }



//            this.socket = socket;
//            this.server = server;
//
//            new Thread( () ->{
//                while (true){
//                    try (DataInputStream in = new DataInputStream(socket.getInputStream());
//                         DataOutputStream out = new DataOutputStream(socket.getOutputStream())
//                    ){
//                        String msg = in.readUTF();
//                        System.out.println("Client said: " + msg);
//                        if (msg.equals("/end")) break;
//                        out.writeUTF(msg);
//                    }catch (IOException e){
//                        System.err.println("Exeption");
//                    }
//                }
//            }).start();
    }


    public void sendClientMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
//    public ClientHandler(Server server, Socket socket) {
//        try {
//            this.socket = socket;
//            this.server = server;
//            this.in = new DataInputStream(socket.getInputStream());
//            this.out = new DataOutputStream(socket.getOutputStream());
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
////                            String str = in.readUTF();
////                            if (str.startsWith("/auth")) {
////                                String[] tokens = str.split(" ");
////                                String newNick = server.AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
////                                if (newNick != null) {
////                                    sendMsg("/authok");
////                                    nick = newNick;
////                                    server.subscribe(L.Server.ClientHandler.this);
////                                    break;
////                                } else {
////                                    sendMsg("Неверный логин/пароль!");
////                                }
////                            }
//                        }
//
//                        while (true) {
//                            String str = in.readUTF();
//                            System.out.println("Client " + str);
//                            if (str.equals("/end")) {
//                                out.writeUTF("/serverClosed");
//                                break;
//                            }
//                            server.broadcastMsg(nick + ": " + str);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            in.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            out.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        server.unsubscribe(Server.ClientHandler.this);
//                    }
//                }
//            }).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMsg(String msg) {
//        try {
//            out.writeUTF(msg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
