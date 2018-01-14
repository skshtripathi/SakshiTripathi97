// A Java program for a Client
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataInputStream  in   = null;
    private DataOutputStream out     = null;
 
    // constructor to put ip address and port
    public client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
 
            // takes input from terminal
            input  = new DataInputStream(System.in);
 
            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
            // get response from socket
            in=new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
 
        // string to read message from input
        String line = "";
        Scanner sc=new Scanner(System.in);
        
        
            try
            {
                System.out.println("Enter an expression:");
                line = sc.nextLine();
                out.writeUTF(line);

                System.out.println(in.readUTF());
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        

 
        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        client client = new client("127.0.0.1", 5000);
    }
}