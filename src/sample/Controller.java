package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Controller implements Initializable{

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8180;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            socket = new Socket(IP_ADRESS, PORT);
//            in = new DataInputStream(socket.getInputStream());
//            out = new DataOutputStream(socket.getOutputStream());
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
//                            String str = in.readUTF();
//                            textArea.appendText(str + "\n");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendMsg() {

        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Scanner input = new Scanner(System.in);

            new Thread( () -> {
                    try {
                        while (true){
                            String msg = in.readUTF();
                            textArea.appendText(msg + "\n");
                        }
                    } catch (IOException e) {
//                        e.printStackTrace();
                        System.out.println("Exeption in client Thread");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}




//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
//public class Controller {
//    @FXML
//    TextArea textArea;
//
//    @FXML
//    TextField textField;
//
//    @FXML
//    Button btn1;
//
//    Socket socket;
//    DataInputStream in;
//    DataOutputStream out;
//
//    @FXML
//    HBox bottomPanel;
//
//    @FXML
//    HBox upperPanel;
//
//    @FXML
//    TextField loginfield;
//
//    @FXML
//    PasswordField passwordfiled;
//
//    private boolean isAuthorized;
//
//    final String IP_ADRESS = "localhost";
//    final int PORT = 8189;

//    public void setAuthorized(boolean isAuthorized) {
//        this.isAuthorized = isAuthorized;
//        if (!isAuthorized) {
//            upperPanel.setVisible(true);
//            upperPanel.setManaged(true);
//            bottomPanel.setVisible(false);
//            bottomPanel.setManaged(false);
//        } else {
//            upperPanel.setVisible(false);
//            upperPanel.setManaged(false);
//            bottomPanel.setVisible(true);
//            bottomPanel.setManaged(true);
//        }
//    }

//    public void connect() {
//        try {
//            socket = new Socket(IP_ADRESS, PORT);
//            in = new DataInputStream(socket.getInputStream());
//            out = new DataOutputStream(socket.getOutputStream());
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
//                            String str = in.readUTF();
//                            if (str.startsWith("/authok")) {
//                                setAuthorized(true);
//                                break;
//                            } else {
//                                textArea.appendText(str + "\n");
//                            }
//                        }
//
//                        while (true) {
//                            String str = in.readUTF();
//                            if(str.equals("/serverClosed")) {
//                                setAuthorized(false);
//                                break;
//                            }
//                            textArea.appendText(str + "\n");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void sendMsg() {
//        try {
//            out.writeUTF(textField.getText());
//            textField.clear();
//            textField.requestFocus();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void tryToAuth() {
//        if (socket == null || socket.isClosed()) {
//            connect();
//        }

//        try {
//            out.writeUTF("/auth " + loginfield.getText() + " " + passwordfiled.getText());
//            loginfield.clear();
//            passwordfiled.clear();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}