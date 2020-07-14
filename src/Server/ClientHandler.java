package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String nick;
    private ArrayList<String> blackList = new ArrayList<>();

    public ClientHandler(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (!server.isNickBusy(newNick)) {
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    break;
                                }else
                                    sendMsg(newNick + " has already logged in");
                            } else {
                                sendMsg("Неверный логин/пароль!");
                            }
                        }
                    }

                    while (true) {
                        String str = in.readUTF();
                        System.out.println("Client " + str);
                        if (str.equals("/end")) {
                            out.writeUTF("/serverClosed");
                            break;
                        } else if (str.startsWith("/w")) {
                            String[] tokens = str.split(" ", 3);
                            if (tokens.length == 3)
                                server.sendPersonalMsg(this, tokens[1], tokens[2]);
                        } else if (str.startsWith("/bl")){
                            String[] tokens = str.split(" ", 2);
                            blackList.add(tokens[1]);
                        }else
                            server.broadcastMsg(this, nick + ": " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
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
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(ClientHandler.this);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isBlackList(String isBlackNick){
        return blackList.contains(isBlackNick);
    }

    public String getNick(){
        return nick;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
