package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DigitCountServer {

    ProblemObject serverObject = null;
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
                if (serverObject != null) {
                    int result = solver(serverObject.getLowerBound(), serverObject.getUpperBound(),
                            serverObject.getDigitToCheck(), serverObject.isAllDigits());
                    printWriter.println("W podanym przedziale znaleziono: " + result);
                    printWriter.flush();
                    serverObject = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public class SendThread implements Runnable{
        Socket gniazdo;
        ObjectInputStream obiektWejscie;
        PrintWriter printWriterWatek;

        public SendThread(Socket socketClient){
            try{
                gniazdo = socketClient;
                obiektWejscie = new ObjectInputStream(gniazdo.getInputStream());
                printWriterWatek = new PrintWriter(gniazdo.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

        }
    }

    public int solver(int start, int end, int digit, boolean allDigits){
        int count = 0;
        for (int i = start; i < end; i++){

            int x = i;
            while(x > 0){
                if (x == digit || x%10 == digit){
                    System.out.println(i);
                    count++;
                    if (!allDigits) break;
                }
                x = x/10;
            }
        }
        return count;
    }
}