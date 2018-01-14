import java.util.*;
import java.io.*;  
import java.net.*;  

class StackS
{
   private double[] a;
   private int top,m;
   public StackS(int max)
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


public class calculator1
{

    public static void main(String args[]) 
    {
        Scanner sc=new Scanner(System.in);
        String infix = sc.nextLine();
        String postfix=inf2postf(infix);
        double eval=evalf(postfix);
        System.out.println(eval);
    }
    public static double evalf(String s)
    {
      int n;
      double r=0;
      n=s.length();
      StackS a=new StackS(n);
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
}