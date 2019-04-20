package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DigitCountServer {

    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;

// MAIN
    public static void main(String[] args) {
        DigitCountServer server = new DigitCountServer();
        server.connect();
    }
// CREATING CONNECTION
    private void connect() {

        try {
            serverSocket = new ServerSocket(5555);

            while (true){
                clientSocket = serverSocket.accept();
                Thread watek = new Thread(new SendThread(clientSocket));
                System.out.println("nowy watek i polaczenie");
                watek.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// NEW THREAD IMPLEMENTATION AND SOCKET
    public class SendThread implements Runnable{
        Socket gniazdo;
        ObjectInputStream obiektWejscie;
        PrintWriter printWriterWatek;

        private SendThread(Socket socketClient){
            try{
                gniazdo = socketClient;
                obiektWejscie = new ObjectInputStream(gniazdo.getInputStream());
                printWriterWatek = new PrintWriter(gniazdo.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

// READING OBJECT SENDING SOLUTION
        @Override
        public void run() {
            ProblemObject obiektWatku;
            while (!gniazdo.isClosed()){
                try {
                    obiektWatku = (ProblemObject) obiektWejscie.readObject();
                    if (obiektWatku != null) {
                        int result = solver(obiektWatku.getLowerBound(), obiektWatku.getUpperBound(),
                                obiektWatku.getDigitToCheck(), obiektWatku.isAllDigits());
                        printWriterWatek.println("W podanym przedziale znaleziono: " + result);
                        printWriterWatek.flush();
                    }
                } catch (IOException e) {
                        try {
                            gniazdo.close();
                    }   catch (IOException e1) {
                            e1.printStackTrace();
                    }
                    System.out.println(e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
// USED TO SOLVE PROBLEM
    int solver(int start, int end, int digit, boolean allDigits){

        int count = 0;
        for (int i = start; i < end; i++){

            int x = i;
            while(x > 0){
                if (x == digit || x%10 == digit){
                    count++;
                    if (!allDigits) break;
                }
                x = x/10;
            }
        }
        return count;
    }
}