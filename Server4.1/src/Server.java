import java.io.*;

import java.net.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientAccepted = null; //объявление объекта класса Socket
        ObjectInputStream  sois = null; //объявление байтового потока ввода
        ObjectOutputStream soos = null; //объявление байтового потока вывода
        try {

            System.out.println("server starting....");
            serverSocket = new ServerSocket(2525); //создание сокета сервера для заданного порта
            clientAccepted = serverSocket.accept(); //выполнение метода, который обеспечивает реальное подключение сервера к клиенту
            System.out.println("connection established....");
            sois = new ObjectInputStream(clientAccepted.getInputStream()); //создание потока ввода
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());//создание потока вывода
            int[][] clientMessageRecieved = (int[][])sois.readObject();
            do {
                System.out.println("message recieved:");
                for(int i = 0; i < clientMessageRecieved.length; i++){
                    System.out.println(clientMessageRecieved[i][0] +" "+ clientMessageRecieved[i][1]);
                }
                int mine = clientMessageRecieved[0][0];
                int maxe = mine;
                int min = 0, max = 0;
                for(int i = 0; i < clientMessageRecieved.length; i++){
                    for(int j = 0; j < clientMessageRecieved[i].length; j++){
                        if(mine > clientMessageRecieved[i][j]){
                            min = i;
                        }
                        if(maxe < clientMessageRecieved[i][j]) {
                            max = j;
                        }
                    }
                }

                soos.writeObject(min + 1);
                soos.writeObject(max + 1);
                clientMessageRecieved = (int[][])sois.readObject();
                soos.writeObject(clientMessageRecieved);//потоку вывода присваивается значение строковой переменной (передается клиенту)
            } while(clientMessageRecieved[0][0] != 0 && clientMessageRecieved[1][1] != 0);
        }
        catch(Exception e) { }
        finally {
            try {
                sois.close();//закрытие потока ввода
                soos.close();//закрытие потока вывода
                clientAccepted.close();//закрытие сокета, выделенного для клиента
                serverSocket.close();//закрытие сокета сервера
            } catch(Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }
        }

    }
}