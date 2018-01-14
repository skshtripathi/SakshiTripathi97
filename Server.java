import java.net.*;
import java.io.*;

import java.util.*;
class StackT
{
   private double[] a;
   private int top,m;
   public StackT(int max)
   {
     m=max;
     a=new double[m];
     top=-1;
   }
   public void push(double key)
   {
     a[++top]=key;
   }
   public double pop()
   {
     return(a[top--]);
   }
}


class Calculator
{
    public static double calculate(String infix)
    {
        String postfix=inf2postf(infix);
        double eval=evalf(postfix);
        return eval;
    }
    public static double evalf(String s)
    {
      int n;
      double r=0;
      n=s.length();
      StackT a=new StackT(n);
      for(int i=0;i<n;i++)
      {
        char ch=s.charAt(i);
        if(ch>='0'&&ch<='9')
          a.push((int)(ch-'0'));
        else
        {
          double x=a.pop();
          double y=a.pop();
          switch(ch)
          {
            case '+':r=x+y;
               break;
            case '-':r=y-x;

               break;
            case '*':r=x*y;

               break;
            case '/':r=y/x;

               break;
            default:r=0;
          }
          a.push(r);
        }
      }
      r=a.pop();
      return(r);
    }

    private static String inf2postf(String infix) {
        String postfix = "";
        Stack<Character> operator = new Stack<Character>();
        char popped;

        for (int i = 0; i < infix.length(); i++) {

            char get = infix.charAt(i);

            if (!isOperator(get))
                postfix += get;

            else if (get == ')')
                while ((popped = operator.pop()) != '(')
                    postfix += popped;

            else {
                while (!operator.isEmpty() && get != '(' && precedence(operator.peek()) >= precedence(get))
                    postfix += operator.pop();

                operator.push(get);
            }
        }
        // pop any remaining operator
        while (!operator.isEmpty())
            postfix += operator.pop();
       // System.out.println(postfix);
        return postfix;
    }

    private static boolean isOperator(char i) {
        return precedence(i) > 0;
    }

    private static int precedence(char i) {
        if (i == '(' || i == ')') return 1;
        else if (i == '-' || i == '+') return 2;
        else if (i == '*' || i == '/') return 3;
        else return 0;
    }
}//calculator end

//Server

public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       = null;
    private DataOutputStream output  = null;
    // constructor with port
    public Server(int port)
    {
        
        Calculator calc=new Calculator();
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);

            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
            output= new DataOutputStream(socket.getOutputStream());
            String line = "";
            
 
            // reads message from client until "Over" is sent
           
                try
                {
                    line = in.readUTF();
                   
                    output.writeUTF(""+calc.calculate(line));
 
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}