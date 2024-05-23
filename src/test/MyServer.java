package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public int port; // server port
    public ClientHandler clientHandler; // client handler
    public ServerSocket serverSocket; // server socket to send/receive data
    public boolean isServerRunning = false; // check if server running

    public MyServer(int port, ClientHandler new_clientHandler) { // constructor
        this.port = port;
        this.clientHandler = new_clientHandler;
    }

    public void start() { // starting server to accept connections coming from clients
        if (isServerRunning)
            return;

        isServerRunning = true;
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (isServerRunning) {
                    // try {
                    // Thread.sleep(1000);
                    // } catch (InterruptedException e) {
                    // e.printStackTrace();
                    // }

                    Socket clientSocket = serverSocket.accept();
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        clientHandler.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
                        clientSocket.close();
                    }
                }
            } catch (IOException e) {

            } finally {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void close() { // closing server, no longer accepting connections
        isServerRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
