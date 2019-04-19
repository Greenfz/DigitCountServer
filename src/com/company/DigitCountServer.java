package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DigitCountServer {

    ProblemObject serverObject = new ProblemObject();
    ObjectInputStream objInStream;
    PrintWriter printWriter;

    Socket clientSocket = null;
    ServerSocket serverSocket = null;


    public static void main(String[] args) {
        DigitCountServer server = new DigitCountServer();
        server.connect();
    }

    public void connect() {

        try {
            serverSocket = new ServerSocket(5555);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objInStream = new ObjectInputStream(clientSocket.getInputStream());
            printWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                serverObject = (ProblemObject) objInStream.readObject();
                System.out.println(serverObject.toString());
                printWriter.println("Odebrano: " + serverObject.toString());
                printWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}