package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public ClientHandler(Socket socket, Server server) {
//        try {
//            this.socket = socket;
//            this.server = server;
//            in = new DataInputStream(socket.getInputStream());
//            out = new DataOutputStream(socket.getOutputStream());
//
//            new Thread( () ->{
//                while (true){
//                    try (
//                            DataInputStream in = new DataInputStream(socket.getInputStream());
//                            DataOutputStream out = new DataOutputStream(socket.getOutputStream())
//                    ){
//                        System.out.println("Client connected");
//                        String msg = in.readUTF();
//                        System.out.println("Client said: " + msg);
//                        if (msg.equals("/end")) {
//                            out.writeUTF("/server closed.");
//                            break;
//                        }
//                        server.broadcastMsg(msg);
//                    }catch (IOException e){
//                        System.err.println("Exeption in client handeler thread");
//                    }finally {
//                        try{
//                            socket.close();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                        try{
//                            in.close();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                        try{
//                            out.close();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//
//        } catch (IOException e){
//            System.err.println("Exeption in ClientHandler Conctructor");
//        }

            this.socket = socket;
            this.server = server;

            try {
                this.in = new DataInputStream(socket.getInputStream());
                this.out = new DataOutputStream(socket.getOutputStream());

                new Thread(() -> {
                    try {
                        while (true) {
                            String msg = in.readUTF();
                            System.out.println("Client said: " + msg);
                            if (msg.equals("/end")) {
                                server.removeClient(this);
                                this.socket.close();
                                break;
                            }
                            server.broadcastMsg(msg);
                        }
                }catch (IOException e){
                    System.err.println("exeption in while loop in client handler");
                }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    public void sendClientMsg(String msg) {
        try {
            out.writeUTF(wrapMsg(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String wrapMsg(String msg){

        if (msg.length() > 14){
            char[] msgArr = new char[msg.length() + 1000];
            for (int i = 0; i < msg.length(); i++) {
                if (i % 14 == 0 && i != 0){
                    for (int j = i; j < msg.length(); j++) {
                        if (msg.charAt(j) == ' '){
                            msgArr[j] = '\n';
                            i = ++j;
                            break;
                        }
                        msgArr[j] = msg.charAt(i);
                    }
                }
                msgArr[i] = msg.charAt(i);
            }
            return valueOf(msgArr);
        }else
            return msg;
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
