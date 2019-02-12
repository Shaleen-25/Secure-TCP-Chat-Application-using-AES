
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author IDEAL
 */
public class client {
    private static final String key = "aesEncryptionKey";
    public static String encrypt(String value) {
    try {
        //IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
 
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
 
        byte[] encrypted = cipher.doFinal(value.getBytes());
        String str = new BASE64Encoder().encode(encrypted);
        //String str = new String(encrypted);
        return str;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
}
    public static void main(String[] args) throws Exception {
        
        //ServerSocket ss =new ServerSocket(2000);
        Socket s= new Socket("127.0.0.1", 5005);
        BufferedReader cin= new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream cout= new PrintStream(s.getOutputStream());
        BufferedReader stdin= new BufferedReader(new InputStreamReader(System.in));
        String str;
        Scanner sc =new Scanner(System.in);
        while(true){
            //str= cin.readLine();
            System.out.print("ClientC:  ");
            str= stdin.readLine();
            String t= encrypt(str);
            cout.println(t);
            //System.out.println("Server:  ");
            
            //str=sc.nextLine();
            
            if(str.equals("exit")){
                //cout.println("exit");
                System.out.println("Connection ended by client.");
                break;
            }
            str=cin.readLine();
            //cout.println(str);
           
            System.out.println("ServerC: "+ str);
        }
        //ss.close();
        s.close();
        cout.close();
        stdin.close();
}
}