package test;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;

public class BookScrabbleHandler implements ClientHandler {
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        BufferedReader readFromClient = null;
        PrintWriter writeToClient = null;
        String line = "";

        try {
            readFromClient = new BufferedReader(new InputStreamReader(inFromclient));
            line = readFromClient.readLine();
        } catch (IOException e) {
            System.out.printf("Error reading from client");
            e.printStackTrace();
        }

        String[] args = line.split(",");
        String action = args[0];
        String[] fixed_args = new String[args.length - 1];

        for (int i = 1; i < args.length; i++)
            fixed_args[i - 1] = args[i];

        writeToClient = new PrintWriter(outToClient, true);
        if (action.equals("C"))
            writeToClient.println(Boolean.toString(DictionaryManager.get().challenge(fixed_args)));
        else if (action.equals("Q"))
            writeToClient.println(Boolean.toString(DictionaryManager.get().query(fixed_args)));

        try {
            if (readFromClient != null)
                readFromClient.close();
        } catch (IOException e) {
            System.out.printf("Error reading from client");
            e.printStackTrace();
        }

        if (writeToClient != null)
            writeToClient.close();

    }

    @Override
    public void close() {
    }

    public BookScrabbleHandler() {

    }
}
