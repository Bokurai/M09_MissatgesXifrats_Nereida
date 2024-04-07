import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientClass {
    Cypher cypher = new Cypher();
    private Scanner sc = new Scanner(System.in);

    public void connect(String address, int port) {
        String serverData;
        String request;
        boolean continueConnected=true;
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;


        try {
            KeyPair clientKeys = cypher.generateKeys();
            PublicKey publicKey = clientKeys.getPublic();
            PrivateKey privateKey = clientKeys.getPrivate();

            socket = new Socket(InetAddress.getByName(address), port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(publicKey);
            out.flush();

            PublicKey serverKey = (PublicKey) in.readObject();

            //el client atén el port fins que decideix finalitzar
            while(continueConnected){

                out.writeObject(e);

                out.flush(); //assegurem que s'envia
                continueConnected = mustFinish(request);
            }

            close(socket);
        } catch (UnknownHostException ex) {
            System.out.printf("Error de connexió. No existeix el host, %s", ex);
        } catch (IOException ex) {
            System.out.printf("Error de connexió indefinit, %s", ex);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean mustFinish(String request) {
        if(request.equals("bye")) return false;
        else return true;
    }


    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClientClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ClientClass clientClass = new ClientClass();
        clientClass.connect("localhost", 9090);
    }
}
