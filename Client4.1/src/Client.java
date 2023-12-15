import java.io.*;

import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1",2525);//установление //соединения между локальной машиной и указанным портом узла сети
            System.out.println("connection established....");
            BufferedReader stdin =
                    new BufferedReader(new InputStreamReader(System.in));//создание
//буферизированного символьного потока ввода
            ObjectOutputStream coos =
                    new ObjectOutputStream(clientSocket.getOutputStream());//создание
//потока вывода
            ObjectInputStream  cois =
                    new ObjectInputStream(clientSocket.getInputStream());//создание
//потока ввода
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter matrix to send to server \n\t('first and last number - 0' − programme terminate)");
            int[][] clientMessage = new int[2][2];
            do {
                System.out.println("matrix enter");
                for(int i = 0; i < clientMessage.length; i++){
                    for(int j = 0; j < clientMessage[i].length; j++){
                        clientMessage[i][j] = scanner.nextInt();
                    }
                }
                System.out.println("you've entered:");
                for(int i = 0; i < clientMessage.length; i++){
                    System.out.println(clientMessage[i][0] +" "+ clientMessage[i][1]);
                }
                coos.writeObject(clientMessage);//потоку вывода присваивается значение строковой переменной (передается серверу)
                System.out.println("~server~: минимальная строка - "+cois.readObject());
                System.out.println("---------------------------");
                System.out.println("~server~: максимальный столбец - "+cois.readObject());
                System.out.println("---------------------------");
                coos.writeObject(clientMessage);
            } while(clientMessage[0][0] != 0 && clientMessage[1][1] != 0);
            coos.close();//закрытие потока вывода
            cois.close();//закрытие потока ввода
            clientSocket.close();//закрытие сокета
        }catch(Exception e)	{
            e.printStackTrace();//выполнение метода исключения е
        }

    }
}